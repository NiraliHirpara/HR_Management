package ca.myseneca.servlet;

import ca.myseneca.model.DAManager;
import ca.myseneca.model.DBUtil;
import ca.myseneca.model.EmployeeShortInfo;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet({"/SearchEmployee"})
public class SearchEmployeeServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private final DataSource dataSource = DBUtil.getDataSource();
   private static final int rowsPerPage = 15;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String user = SessionHelper.getCurrentUser(request);
      String isFormSubmitted = request.getParameter("btnGo");
      if (user == null) {
         response.sendRedirect(request.getContextPath() + "/index.jsp");
      } else if (isFormSubmitted == null) {
         System.out.println(this.getClass().getSimpleName() + ", waiting for input...");
         this.getServletContext().getRequestDispatcher("/searchEmployee.jsp").forward(request, response);
      } else if (!this.isPassValidation(request)) {
         String statusMsg = "The search field cannot be empty";
         request.setAttribute("statusMsg", statusMsg);
         this.getServletContext().getRequestDispatcher("/searchEmployee.jsp").forward(request, response);
      } else {
         System.out.println(this.getClass().getSimpleName() + ", Current User is: " + user);
         System.out.println(this.getClass().getSimpleName() + ", Search text is: " + request.getParameter("searchText"));
         this.doBusinessLogic(request, response);
      }

   }

   private boolean isPassValidation(HttpServletRequest request) {
      String searchText = request.getParameter("searchText");
      return searchText != null && !searchText.isEmpty();
   }

   private void doBusinessLogic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String inPageNumber = request.getParameter("pageNumber");
      String searchText = request.getParameter("searchText");
      int page = 1;
      if (inPageNumber != null) {
         try {
            page = Integer.parseInt(inPageNumber);
            System.out.println(this.getClass().getSimpleName() + ", Page Number:" + page);
         } catch (NumberFormatException var9) {
            ;
         }
      }

      int offset = 15 * (page - 1);
      ArrayList<EmployeeShortInfo> myList = DAManager.getEmployeesByText(this.dataSource, searchText, offset, 15);
      int searchCount = DAManager.getEmployeeSearchCount(this.dataSource, searchText);
      request.setAttribute("searchText", searchText);
      request.setAttribute("searchCount", searchCount);
      request.setAttribute("thisPageNumber", page);
      request.setAttribute("empShortList", myList);
      request.setAttribute("pages", this.getPageNumbers(searchCount));
      this.getServletContext().getRequestDispatcher("/searchEmployeeResult.jsp").forward(request, response);
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