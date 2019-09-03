<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<body>
<% String thisUser = (String)session.getAttribute("USER"); %>

<div id="header" class="container-fluid navbar-fixed-top  backgrund-white">
   <div class="row pull-right">
       <%= thisUser %>&nbsp;
       <a href="Logout" class="btn btn-info btn-xs mybtnred" >
           <span class="glyphicon glyphicon-off mybtnred"></span></a>
   </div>
   <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-margins navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="sr-only">Toggle navigation</span>
          </button>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="EmployeeList">Employee List</a></li>
            <li><a href="NewEmployee">New Employee</a></li>
            <li><a href="SearchEmployee">Search Employee</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
</div>   
</body>
</html>