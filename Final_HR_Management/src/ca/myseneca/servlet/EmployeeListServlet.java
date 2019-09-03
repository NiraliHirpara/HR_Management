package ca.myseneca.servlet;

import ca.myseneca.model.DAManager;
import ca.myseneca.model.DBUtil;
import ca.myseneca.model.Employee;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet({"/EmployeeList"})
public class EmployeeListServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private final DataSource dataSource = DBUtil.getDataSource();
   private static final int rowsPerPage = 15;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String user = SessionHelper.getCurrentUser(request);
      String isDeptSubmitted = request.getParameter("btnDept");
      String isAllSubmitted = request.getParameter("btnAllDept");
      if (user == null) {
         response.sendRedirect(request.getContextPath() + "/index.jsp");
      } else if (isDeptSubmitted == null && isAllSubmitted == null) {
         System.out.println(this.getClass().getSimpleName() + ", waiting for input...");
         this.getServletContext().getRequestDispatcher("/employeeList.jsp").forward(request, response);
      } else {
         String statusMsg;
         if (isDeptSubmitted != null && (statusMsg = this.isPassValidation(request)) != null) {
            request.setAttribute("statusMsg", statusMsg);
            this.getServletContext().getRequestDispatcher("/employeeList.jsp").forward(request, response);
         } else {
            System.out.println(this.getClass().getSimpleName() + ", Current User is: " + user);
            System.out.println(this.getClass().getSimpleName() + ", Search for dept ID: " + request.getParameter("txtDepartId"));
            this.doBusinessLogic(request, response);
         }
      }

   }

   private String isPassValidation(HttpServletRequest request) {
      StringBuilder sb = new StringBuilder();

      try {
         int deptId = Integer.parseInt(request.getParameter("txtDepartId"));
         if (deptId < 0) {
            sb.append("The department ID must be a positive numeric!").append("<br>");
         }
      } catch (NumberFormatException var4) {
         sb.append("The department ID must be numeric!").append("<br>");
      }

      System.out.println(this.getClass().getSimpleName() + ", validation string: " + sb.toString());
      return sb.toString().isEmpty() ? null : sb.toString();
   }

   private void doBusinessLogic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String inPageNumber = request.getParameter("pageNumber");
      int page = 1;

      int deptId;
      try {
         deptId = Integer.parseInt(request.getParameter("txtDepartId"));
      } catch (NumberFormatException var11) {
         deptId = -999;
      }

      if (inPageNumber != null) {
         try {
            page = Integer.parseInt(inPageNumber);
            System.out.println(this.getClass().getSimpleName() + ", Page Number:" + page);
         } catch (NumberFormatException var10) {
            ;
         }
      }

      int offset = 15 * (page - 1);
      int searchCount = DAManager.getEmployeesByDepartmentCount(this.dataSource, deptId);
      String searchDeptText;
      if (searchCount > 0) {
         searchDeptText = "";
         if (deptId > 0) {
            searchDeptText = DAManager.getDeptNameById(this.dataSource, deptId);
         }

         ArrayList<Employee> myList = DAManager.getEmployeesByDepartmentID(this.dataSource, deptId, offset, 15);
         request.setAttribute("txtDepartId", deptId);
         request.setAttribute("searchDeptText", searchDeptText);
         request.setAttribute("searchCount", searchCount);
         request.setAttribute("thisPageNumber", page);
         request.setAttribute("empList", myList);
         request.setAttribute("pages", this.getPageNumbers(searchCount));
         this.getServletContext().getRequestDispatcher("/employeeListResult.jsp").forward(request, response);
      } else {
         searchDeptText = "No employee records found for this department ID";
         request.setAttribute("statusMsg", searchDeptText);
         this.getServletContext().getRequestDispatcher("/employeeList.jsp").forward(request, response);
      }

   }

   private ArrayList<Integer> getPageNumbers(int totalRows) {
      ArrayList<Integer> pageNumbers = new ArrayList();
      int pages = totalRows / 15;
      if (totalRows % 15 != 0) {
         ++pages;
      }

      for(int i = 1; i <= pages; ++i) {
         pageNumbers.add(new Integer(i));
      }

      return pageNumbers;
   }
}