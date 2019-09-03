package ca.myseneca.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/Logout"})
public class LogoutServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String user = SessionHelper.getCurrentUser(request);
      if (user == null) {
         response.sendRedirect(request.getContextPath() + "/index.jsp");
      } else {
         System.out.println(this.getClass().getSimpleName() + ", Logout now... ");
         SessionHelper.doLogout(request);
         System.out.println(this.getClass().getSimpleName() + ", waiting for input...");
         this.getServletContext().getRequestDispatcher("/logout.jsp").forward(request, response);
      }

   }
}