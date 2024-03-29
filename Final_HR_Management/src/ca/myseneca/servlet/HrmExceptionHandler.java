package ca.myseneca.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/HrmException"})
public class HrmExceptionHandler extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Throwable throwable = (Throwable)request.getAttribute("javax.servlet.error.exception");
      Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
      String servletName = (String)request.getAttribute("javax.servlet.error.servlet_name");
      if (servletName == null) {
         servletName = "Unknown";
      }

      String requestUri = (String)request.getAttribute("javax.servlet.error.request_uri");
      if (requestUri == null) {
         requestUri = "Unknown";
      }

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.write("<html><head><title>Exception/Error Details</title></head><body>");
      if (throwable == null && statusCode == null) {
         out.println("<h2>Error information is missing</h2>");
      } else if (statusCode != null && statusCode != 500) {
         out.write("<h3>Error Details</h3>");
         out.write("<strong>Status Code</strong>:" + statusCode + "<br>");
         out.write("<strong>Requested URI</strong>:" + requestUri);
      } else {
         out.write("<h3>Exception Details</h3>");
         out.write("<ul><li>Servlet Name:" + servletName + "</li>");
         out.write("<li>Exception Name:" + throwable.getClass().getName() + "</li>");
         out.write("<li>Requested URI:" + requestUri + "</li>");
         out.write("<li>Exception Message:" + throwable.getMessage() + "</li>");
         out.write("</ul>");
      }

      out.write("<br><br>");
      out.write("<a href=\"index.jsp\">Login again</a>");
      out.write("</body></html>");
   }
}