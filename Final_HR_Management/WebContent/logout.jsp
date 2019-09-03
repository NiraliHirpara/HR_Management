<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html><html>
<head>
<meta charset="utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
   <title>Sign-out</title>
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
<div id="header" class="container-fluid navbar-fixed-top  backgrund-white">
   <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-margins navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <h4 class="text-glow" >Signed Out</h4>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
</div>   

<div class="container body-margins-logout" >
   <h4>
      You have logged off from the HR system
   </h4>
   <br>
   <p>
      You are safe to close this window now.  Click the button below if you want to sign-in the system again.
   </p>
   <form action="" method="get">
      <input type=button onClick="location.href='index.jsp'" value='Sign-in again'>
   </form>
</div>
<jsp:include page="footer.jsp" />
</body></html>