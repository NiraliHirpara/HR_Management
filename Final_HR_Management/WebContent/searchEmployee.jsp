<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<div class="container-fluid body-margins">
   <div class="page-header">
      <h2>Search for Employee Page</h2>
   </div>
   <p>
   	  Search for an Employee by typing in any part of a name, email address, phone number or department
   </p>
   <form action="SearchEmployee" method="post"> 
      <input type="text" name="searchText" required >
      <input type="submit" name="btnGo" value="Go">
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