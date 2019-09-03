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

@WebServlet({"/EditEmployee"})
public class EditEmployeeServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private final DataSource dataSource = DBUtil.getDataSource();

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String user = SessionHelper.getCurrentUser(request);
      String editEmpId = request.getParameter("editEmpId");
      String isUpdateSubmitted = request.getParameter("btnUpdate");
      String isDeleteSubmitted = request.getParameter("btnDelete");
      LinkedHashMap<Integer, String> dList = DAManager.getDeptHashMap(this.dataSource);
      request.setAttribute("deptlist", dList);
      request.setAttribute("editEmpId", editEmpId);
      request.setAttribute("txtDepartId", request.getParameter("txtDepartId"));
      request.setAttribute("pageNumber", request.getParameter("pageNumber"));
      boolean var9 = true;

      int empKey;
      try {
         empKey = Integer.parseInt(editEmpId);
      } catch (NumberFormatException var12) {
         empKey = -1;
      }

      if (user == null) {
         response.sendRedirect(request.getContextPath() + "/index.jsp");
      } else if (DAManager.getEmployeeByID(this.dataSource, empKey) == null) {
         System.out.println(this.getClass().getSimpleName() + ", invalid input parm is not found, no action...");
         this.getServletContext().getRequestDispatcher("/employeeList.jsp").forward(request, response);
      } else {
         String statusMsg;
         Employee emp;
         if (isUpdateSubmitted == null && isDeleteSubmitted == null) {
            System.out.println(this.getClass().getSimpleName() + ", waiting for input...");
            emp = DAManager.getEmployeeByID(this.dataSource, empKey);
            request.setAttribute("rejectedEmp", emp);
            statusMsg = "";
            request.setAttribute("statusMsg", statusMsg);
            this.getServletContext().getRequestDispatcher("/editEmployee.jsp").forward(request, response);
         } else if (isDeleteSubmitted != null) {
            emp = DAManager.getEmployeeByID(this.dataSource, empKey);
            if (DAManager.deleteEmployeeByID(this.dataSource, empKey) > 0) {
               request.setAttribute("confirmEmp", emp);
               statusMsg = "The employee is deleted successfully!";
               request.setAttribute("statusMsg", statusMsg);
               this.getServletContext().getRequestDispatcher("/confirmUpdateDelete.jsp").forward(request, response);
            } else {
               Employee rejectedEmp = DAManager.getEmployeeByID(this.dataSource, empKey);
               request.setAttribute("rejectedEmp", rejectedEmp);
               statusMsg = "Cannot delete record.  This employee is a manager of other employees!";
               request.setAttribute("statusMsg", statusMsg);
               this.getServletContext().getRequestDispatcher("/editEmployee.jsp").forward(request, response);
            }
         } else if ((statusMsg = this.isPassValidation(request)) != null) {
            emp = SessionHelper.createEmployeeFromJsp(request);
            request.setAttribute("rejectedEmp", emp);
            request.setAttribute("statusMsg", statusMsg);
            this.getServletContext().getRequestDispatcher("/editEmployee.jsp").forward(request, response);
         } else {
            System.out.println(this.getClass().getSimpleName() + ", Current User is: " + user);
            System.out.println(this.getClass().getSimpleName() + ", txtEmpId is: " + request.getParameter("txtEmpId"));
            emp = SessionHelper.createEmployeeFromJsp(request);
            if (DAManager.updateEmployee(this.dataSource, emp) > 0) {
               request.setAttribute("confirmEmp", emp);
               statusMsg = "The employee is updated successfully!";
               request.setAttribute("statusMsg", statusMsg);
               this.getServletContext().getRequestDispatcher("/confirmUpdateDelete.jsp").forward(request, response);
            } else {
               request.setAttribute("rejectedEmp", emp);
               statusMsg = "Database error, fail to update this employee!";
               request.setAttribute("statusMsg", statusMsg);
               this.getServletContext().getRequestDispatcher("/editEmployee.jsp").forward(request, response);
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
         sb.append("System error.  The employee ID retrieved is not numeric!").append("<br>");
      }

      System.out.println(this.getClass().getSimpleName() + ", validation string: " + sb.toString());
      return sb.toString().isEmpty() ? null : sb.toString();
   }
}