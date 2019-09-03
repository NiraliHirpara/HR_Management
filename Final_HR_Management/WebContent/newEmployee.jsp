<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="ca.myseneca.model.Employee" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   <title>New Employee</title>
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
      <h2>New Employee Page</h2>
   </div>
   <% 
   Employee reEmp = (Employee)request.getAttribute("rejectedEmp"); 
   %>
   <form action="NewEmployee" method="post" id="form1"> 
      <label class="pad_top">Employee ID:</label>
      <input type="number" name="txtEmpId" min="1" max="999999" required value="<%= reEmp==null? "": reEmp.getEmployee_id() %>"><br>

      <jsp:include page="employeeFields.jsp" />
      
      <label>&nbsp;</label><input type="submit" name="btnSave" value="Save the New Employee" class="margin_left">
      <input type="submit" name="btnClear" value="Clear" onClick='document.getElementById("form2").submit();' >
   </form>
   <br>
   <form action="NewEmployee" method="post" id="form2"></form>
   <div class="status-text">
		<% 
		String statusMsg = (String) request.getAttribute("statusMsg");
		if (statusMsg != null ) out.println(statusMsg);
		%>
   </div>
   <br><br>
</div>
<jsp:include page="footer.jsp" />
</body></html>