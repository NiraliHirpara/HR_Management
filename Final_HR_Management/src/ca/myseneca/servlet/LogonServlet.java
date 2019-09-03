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

@WebServlet({"/Logon"})
public class LogonServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private final DataSource dataSource = DBUtil.getDataSource();

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SessionHelper.doLogout(request);
      String isFormSubmitted = request.getParameter("btnLogin");
      LinkedHashMap<Integer, String> dList = DAManager.getDeptHashMap(this.dataSource);
      request.setAttribute("deptlist", dList);
      if (isFormSubmitted == null) {
         System.out.println(this.getClass().getSimpleName() + ", waiting for input...");
         this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
      } else {
         String statusMsg;
         if ((statusMsg = this.isPassValidation(request)) != null) {
            request.setAttribute("statusMsg", statusMsg);
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
         } else {
            int userId = DAManager.getEmployeeID(this.dataSource, request.getParameter("txtUser"), request.getParameter("txtPwd"));
            if (userId == 0) {
               System.out.println(this.getClass().getSimpleName() + ", Logon User is unauthorized");
               statusMsg = "Invalid authentication";
               request.setAttribute("statusMsg", statusMsg);
               this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
               Employee usr = DAManager.getEmployeeByID(this.dataSource, userId);
               String userName = usr.getFirst_Name() + " " + usr.getLast_Name();
               SessionHelper.setCurrentUser(request, userName);
               System.out.println("LogonServlet, Logon User is: " + userName);
               response.sendRedirect(request.getContextPath() + "/EmployeeList");
            }
         }
      }

   }

   private String isPassValidation(HttpServletRequest request) {
      StringBuilder sb = new StringBuilder();
      if (request.getParameter("txtUser").isEmpty()) {
         sb.append("The user name cannot be empty!").append("<br>");
      }

      if (request.getParameter("txtPwd").isEmpty()) {
         sb.append("The password cannot be empty!").append("<br>");
      }

      return sb.toString().isEmpty() ? null : sb.toString();
   }
}