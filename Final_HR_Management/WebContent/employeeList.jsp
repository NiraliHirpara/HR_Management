<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   <title>Employee List</title>
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
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<div class="container-fluid body-margins">
   <div class="page-header">
      <h1>Employee List Page</h1>
   </div>
   <p>
   	  Show employees in a department by typing in the department id and click on the button next (Enter 0 for those employees not yet in any department), or click on the Show All Employee for all employees in the company.
   </p>
   <form action="EmployeeList" method="post"> 
      <label class="pad_top">Department ID:</label>
      <input type="number" name="txtDepartId" required min="0" max="9999" ><br>
      <label>&nbsp;</label><input type="submit" name="btnDept" value="Show Department Employees" class="margin_left">
   </form>
   <br>
   <form action="EmployeeList" method="post"> 
      <input type="hidden" name="txtDepartId" value="-999">
      <label>&nbsp;</label><input type="submit" name="btnAllDept" value="Show All Employees" class="margin_left">
   </form>
   <br><br>
       <div class="status-text">
		<% 
		String statusMsg = (String) request.getAttribute("statusMsg");
		if (statusMsg != null ) out.println(statusMsg);
		%>
       </div>
</div>
<jsp:include page="footer.jsp" />
</body></html>