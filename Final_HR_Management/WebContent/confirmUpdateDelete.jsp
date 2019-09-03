<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ca.myseneca.model.Employee" %>
<!DOCTYPE html><html>
<head>
<meta charset="utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Confirm Database Changes</title>
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
<div class="container">
     <% 
       String statusMsg = (String) request.getAttribute("statusMsg");
       Employee confirmEmp = (Employee) request.getAttribute("confirmEmp");
       String txtDepartId = (String)request.getAttribute("txtDepartId");
       String pageNumber = (String)request.getAttribute("pageNumber");
     %>
   <div class="page-header">
      <h2>
      <%= statusMsg %>
      </h2>
   </div>
   <p>
     <% 
       out.println("Employee ID: " + confirmEmp.getEmployee_id());
       out.println("<br>Employee Name: " + confirmEmp.getFirst_Name() + " " + confirmEmp.getLast_Name());
     %>
   </p>

   <p>To return the Search for Employee List page, click the Return button shown below.</p>

   <form action="EmployeeList" method="post">
     <input type="hidden" name="txtDepartId" value="<%= txtDepartId %>" >
     <input type="hidden" name="pageNumber" value="<%= pageNumber %>" >
     <% if (("-999").equals(txtDepartId)) { %>
          <input type="hidden" name="btnAllDept" value="Show All Employees" >
     <% } else { %>
          <input type="hidden" name="btnDept" value="Show Department Employees" >
     <% }        %>

     <input type="submit" value="Return">
   </form>
</div>   
</body>
</html>