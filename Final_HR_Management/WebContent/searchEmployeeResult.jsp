<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ca.myseneca.model.EmployeeShortInfo" %>
<!DOCTYPE html><html>
<head>
<meta charset="utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
   <title>Employee Search</title>
   <!-- Latest compiled and minified CSS -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   <!-- jQuery library -->
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
   <!-- Latest compiled JavaScript -->
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <!-- My own file css -->
   <link rel="stylesheet" href="styles/main.css" />
</head>
<body>
<div class="container body-margins-no-header">
   <h2>
      Search for Employee
   </h2>
   <p>
     <% 
       String searchText = (String) request.getAttribute("searchText");
       int searchCount = (int) request.getAttribute("searchCount");
       int thisPageNumber = (int) request.getAttribute("thisPageNumber") - 1;
       out.println("Employee search results for " + searchText + ": " + searchCount);
     %>
   </p>
   
   <% @SuppressWarnings("unchecked")
     ArrayList<EmployeeShortInfo> list = (ArrayList<EmployeeShortInfo>) request.getAttribute("empShortList"); 
   %>
   <% @SuppressWarnings("unchecked")
     ArrayList<Integer> pageNumbers = (ArrayList<Integer>) request.getAttribute("pages"); 
   %>
   <%
      if (searchCount > 0) {
   %>   
   <table class="table table-striped">
     <thead class="thead-default">
       <tr>
         <th><strong>Name</strong></th>
         <th><strong>Department</strong></th>
         <th><strong>Job ID</strong></th>
         <th><strong>Salary</strong></th>
         <th><strong>Email</strong></th>
         <th><strong>Phone Number</strong></th>
       </tr>
     </thead>
     <tbody>
     <%
        for (int i = 0; i < list.size(); i++) {
     %>
     <tr>
        <%
           EmployeeShortInfo emp = (EmployeeShortInfo) list.get(i);
           out.println("<td>" + emp.getFull_name() + "</td>");
           out.println("<td>" + emp.getDepartment_name() + "</td>");
           out.println("<td>" + emp.getJob_Id() + "</td>");
           out.println("<td>" + (emp.getSalary() < 0? "" : emp.getSalary()) + "</td>");
           out.println("<td> <a href=\"mailto:" + emp.getEmail() + "\">" + emp.getEmail() + "</a> </td>");
           out.println("<td>" + emp.getPhNO() + "</td>");
        %>
     </tr>
     <%
        }
     %>
 
     </tbody>
   </table>
   <%
     }
   %>
   <%
      if (searchCount > 0) {
   %> 
   <nav aria-label="...">
   <ul class="pagination">
      <% for (int i = 0; i < pageNumbers.size(); i++) { %>
        <% if (i == thisPageNumber) { %>
              <li class="page-item active">
                <a class="page-link" href="#"><%=pageNumbers.get(i)%><span class="sr-only">(current)</span></a>
              </li>
        <% } else { %>
              <li class="page-item"><a class="page-link" href="SearchEmployee?pageNumber=<%=pageNumbers.get(i)%>&searchText=<%=searchText%>&btnGo=Go"><%=pageNumbers.get(i)%></a></li>
        <%
           }
        }
        %>
   </ul>
   </nav>
   <%
     }
   %>
   <p>To return the Search for Employee page, click on the Back button in your
		browser or the Return button shown below.</p>

   <form action="SearchEmployee" method="post">
     <input type="submit" value="Return">
   </form>
</div>   
</body>
</html>