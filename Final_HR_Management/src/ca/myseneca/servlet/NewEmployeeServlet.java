package ca.myseneca.servlet;

import ca.myseneca.model.DAManager;
import ca.myseneca.model.DBUtil;
import ca.myseneca.model.Employee;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet({"/NewEmployee"})
public class NewEmployeeServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private final DataSource dataSource = DBUtil.getDataSource();

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String user = SessionHelper.getCurrentUser(request);
      String isFormSubmitted = request.getParameter("btnSave");
      String isFormCleared = request.getParameter("btnClear");
      LinkedHashMap<Integer, String> dList = DAManager.getDeptHashMap(this.dataSource);
      request.setAttribute("deptlist", dList);
      if (user == null) {
         response.sendRedirect(request.getContextPath() + "/index.jsp");
      } else {
         String statusMsg;
         if (isFormCleared != null) {
            request.setAttribute("rejectedEmp", (Object)null);
            statusMsg = "";
            request.setAttribute("statusMsg", statusMsg);
            this.getServletContext().getRequestDispatcher("/newEmployee.jsp").forward(request, response);
         } else if (isFormSubmitted == null) {
            System.out.println(this.getClass().getSimpleName() + ", waiting for input...");
            this.getServletContext().getRequestDispatcher("/newEmployee.jsp").forward(request, response);
         } else {
            Employee rejectedEmp;
            if ((statusMsg = this.isPassValidation(request)) != null) {
               rejectedEmp = SessionHelper.createEmployeeFromJsp(request);
               request.setAttribute("rejectedEmp", rejectedEmp);
               request.setAttribute("statusMsg", statusMsg);
               this.getServletContext().getRequestDispatcher("/newEmployee.jsp").forward(request, response);
            } else {
               System.out.println(this.getClass().getSimpleName() + ", Current User is: " + user);
               if (this.doBusinessLogic(request, response) > 0) {
                  request.setAttribute("rejectedEmp", (Object)null);
                  statusMsg = "New employee added successfully!";
               } else {
                  rejectedEmp = SessionHelper.createEmployeeFromJsp(request);
                  request.setAttribute("rejectedEmp", rejectedEmp);
                  statusMsg = "Database error, fail to add the new employee!";
               }

               request.setAttribute("statusMsg", statusMsg);
               this.getServletContext().getRequestDispatcher("/newEmployee.jsp").forward(request, response);
            }
         }
      }

   }

   private String isPassValidation(HttpServletRequest request) {
      StringBuilder sb = new StringBuilder();
      System.out.println(this.getClass().getSimpleName() + ", txtEmpId: " + request.getParameter("txtEmpId"));
      boolean var3 = false;

      try {
         int id = Integer.parseInt(request.getParameter("txtEmpId"));
         if (DAManager.getEmployeeByID(this.dataSource, id) != null) {
            sb.append("The employee ID already exists!").append("<br>");
         }

         if (DAManager.validateEmployeeByEmail(this.dataSource, id, request.getParameter("txtEmail")) > 0) {
            sb.append("The employee email already exists!").append("<br>");
         }

         if (!DAManager.validateJobId(this.dataSource, request.getParameter("txtJobId"))) {
            sb.append("The job ID is invalid!").append("<br>");
         }

         if (SessionHelper.convertSqlDate(request.getParameter("txtHireDate")) == null) {
            sb.append("The hire date Is invalid!").append("<br>");
         }

         if (request.getParameter("txtManagerId") != null && !request.getParameter("txtManagerId").isEmpty()) {
            try {
               int managerId = Integer.parseInt(request.getParameter("txtManagerId"));
               if (DAManager.getEmployeeByID(this.dataSource, managerId) == null) {
                  sb.append("The Manager ID is invalid!").append("<br>");
               }
            } catch (Exception var5) {
               sb.append("The Manager ID must be numeric").append("<br>");
            }
         }
      } catch (NumberFormatException var6) {
         sb.append("The employee ID must be numeric").append("<br>");
      }

      System.out.println(this.getClass().getSimpleName() + ", validation string: " + sb.toString());
      return sb.toString().isEmpty() ? null : sb.toString();
   }

   private int doBusinessLogic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Employee emp = SessionHelper.createEmployeeFromJsp(request);
      return DAManager.addEmployee(this.dataSource, emp);
   }
}