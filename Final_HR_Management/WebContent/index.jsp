<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html><html>
<head>
<meta charset="utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
   <title>Sign-in</title>
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
<div class="container body-margins" >
   <h1>
      <img class="logo" src="images/hr-logo.png" alt="HR logo" height="50" width="50" style="vertical-align:bottom"/>
      HR Management
   </h1>
   <p>
      Type your user name and password to login the system.
   </p>
   <form action="Logon" method="post"> 
      <label class="pad_top">User Name:</label> 
      <input type="text" name="txtUser" required><br> 
      <label class="pad_top">Password:</label> 
      <input type="password" name="txtPwd" required><br>
      <label>&nbsp;</label><input type="submit" name="btnLogin" value="Login" class="margin_left">
	</form>
   <div class="status-text-no-footer">
		<% 
		String statusMsg = (String) request.getAttribute("statusMsg");
		if (statusMsg != null ) out.println(statusMsg);
		%>
   </div>
</div></body></html>