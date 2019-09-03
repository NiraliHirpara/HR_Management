<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="ca.myseneca.model.Employee" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   <title>Edit Employee</title>
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
<!-- Begin page content -->
<div class="container body-margins-no-header" >
   <div class="page-header">
      <h2>Edit Employee Page</h2>
   </div>
   <% 
   Employee reEmp = (Employee)request.getAttribute("rejectedEmp"); 
   String editEmpId = (String)request.getAttribute("editEmpId");
   String txtDepartId = (String)request.getAttribute("txtDepartId");
   String pageNumber = (String)request.getAttribute("pageNumber");
   %>
   <form action="EditEmployee" method="post" id="form1"> 
      <label class="pad_top">Employee ID:</label>
      <input type="number" name="txtEmpId" min="1" max="999999" readonly value="<%= reEmp==null? "": reEmp.getEmployee_id() %>"><br>

      <jsp:include page="employeeFields.jsp" />
      
      <input type="hidden" name="editEmpId" value="<%= editEmpId %>" >
      <input type="hidden" name="txtDepartId" value="<%= txtDepartId %>" >
      <input type="hidden" name="pageNumber" value="<%= pageNumber %>" >

      <label>&nbsp;</label><input type="submit" name="btnUpdate" value="Update the Employee" class="margin_left">
      <input type="submit" name="btnDelete" value="Delete the Employee" onClick='document.getElementById("form2").submit();' >
   </form>
   <br>
   <form action="EditEmployee" method="post" id="form2"> 
      <input type="hidden" name="editEmpId" value="<%= editEmpId %>" >
   </form>
   <div class="status-text-no-footer">
		<% 
		String statusMsg = (String) request.getAttribute("statusMsg");
		if (statusMsg != null ) out.println(statusMsg);
		%>
   </div>
   <br><br>
   <p>To return the Employee List page, click the Return button shown below.</p>

   <form action="EmployeeList" method="post">
     <input type="hidden" name="txtDepartId" value="<%= txtDepartId %>" >
     <input type="hidden" name="pageNumber" value="<%= pageNumber %>" >
     <% if (("-999").equals(txtDepartId)) { %>
          <input type="hidden" name="btnAllDept" value="Show All Employees" >
     <% } else { %>
          <input type="hidden" name="btnDept" value="Show Department Employees" >
     <% }        %>
     
     <input type="submit" name="btnReturn" value="Return">
   </form>
</div>
</body></html>