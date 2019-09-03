<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ca.myseneca.model.Employee" %>
<!DOCTYPE html><html>
<head>
<meta charset="utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
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
<div class="container body-margins-no-header">
   <h2>
      Employee List
   </h2>
   <p>
     <% 
       String searchDeptText = (String) request.getAttribute("searchDeptText");
       int searchDeptId = (int) request.getAttribute("txtDepartId");
       int searchCount = (int) request.getAttribute("searchCount");
       int thisPageNumber = (int) request.getAttribute("thisPageNumber");
       int thisPageIdx = thisPageNumber - 1;
       if (searchDeptId == 0)
          out.println("Here is the information of employees not in any department yet");
       else if (searchDeptId > 0)
          out.println("Here is the information of employees from department of " + searchDeptText);
       else
          out.println("Here is the information of all employees");
     %>
   </p>
   
   <% @SuppressWarnings("unchecked")
     ArrayList<Employee> list = (ArrayList<Employee>) request.getAttribute("empList"); 
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
         <th><strong>Employee ID</strong></th>
         <th><strong>First Name</strong></th>
         <th><strong>Last Name</strong></th>
         <th><strong>Email</strong></th>
         <th><strong>Phone Number</strong></th>
         <th><strong>Hire Date</strong></th>
         <th><strong>Job ID</strong></th>
         <th><strong>Salary</strong></th>
         <th><strong>Commission Pct</strong></th>
         <th><strong>Manager ID</strong></th>
         <th><strong>Department ID</strong></th>
       </tr>
     </thead>
     <tbody>
     <%
        for (int i = 0; i < list.size(); i++) {
     %>
     <tr>
        <%
           Employee emp = (Employee) list.get(i);
           out.println("<td> <a href=\"EditEmployee?editEmpId=" + emp.getEmployee_id() + "&txtDepartId=" + searchDeptId +"&pageNumber=" + thisPageNumber + "\">" + emp.getEmployee_id() + "</td>");
           out.println("<td>" + emp.getFirst_Name() + "</td>");
           out.println("<td>" + emp.getLast_Name() + "</td>");
           out.println("<td>" + emp.getEmail() + "</td>");
           out.println("<td>" + emp.getPhNO() + "</td>");
           out.println("<td>" + emp.getHireDate() + "</td>");
           out.println("<td>" + emp.getJob_Id() + "</td>");
           out.println("<td>" + (emp.getSalary() < 0? "" : emp.getSalary()) + "</td>");
           out.println("<td>" + (emp.getCommission_pct() < 0? "" : emp.getCommission_pct()) + "</td>");
           out.println("<td>" + (emp.getManager_id() <0? "" : emp.getManager_id()) + "</td>");
           out.println("<td>" + (emp.getDepartment_id()<0? "" : emp.getDepartment_id()) + "</td>");
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
        <% if (i == thisPageIdx) { %>
              <li class="page-item active">
                <a class="page-link" href="#"><%=pageNumbers.get(i)%><span class="sr-only">(current)</span></a>
              </li>
        <% } else { %>
        <%      if (searchDeptId > 0) { %>
                   <li class="page-item"><a class="page-link" href="EmployeeList?pageNumber=<%=pageNumbers.get(i)%>&txtDepartId=<%=searchDeptId%>&btnDept=Show%20Department%20Employees"><%=pageNumbers.get(i)%></a></li>
        <%      } else { %>
                   <li class="page-item"><a class="page-link" href="EmployeeList?pageNumber=<%=pageNumbers.get(i)%>&txtDepartId=-999&btnAllDept=Show%20All%20Employees"><%=pageNumbers.get(i)%></a></li>
        <%      }
           }
        }
        %>
   </ul>
   </nav>
   <%
     }
   %>
   <p>To return the Employee List page, click on the Back button in your
		browser or the Return button shown below.</p>

   <form action="EmployeeList" method="post">
     <input type="submit" name="btnReturn" value="Return">
   </form>
</div>   
</body>
</html>