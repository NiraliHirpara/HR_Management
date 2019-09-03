<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="ca.myseneca.model.Employee" %>
<!DOCTYPE html>
<html>
<body>
   <% 
   Employee reEmp = (Employee)request.getAttribute("rejectedEmp"); 
   %>
      <label class="pad_top">First Name:</label>
      <input type="text" name="txtFirstName" required value="<%= reEmp==null? "": reEmp.getFirst_Name()%>" ><br>
      <label class="pad_top">Last Name:</label>
      <input type="text" name="txtLastName" required value="<%= reEmp==null? "": reEmp.getLast_Name()%>" ><br>
      <label class="pad_top">Email:</label>
      <input type="email" name="txtEmail" required value="<%= reEmp==null? "": reEmp.getEmail()%>" ><br>
      <label class="pad_top">Phone Number:</label>
      <input type="tel" name="txtPhone" value="<%= reEmp==null? "": reEmp.getPhNO()%>" ><br>
      <label class="pad_top">Hire Date:</label>
      <input type="date" name="txtHireDate" required placeholder="yyyy-mm-dd" value="<%= reEmp==null || reEmp.getHireDate()== null? "": reEmp.getHireDate()%>" ><br>
      <label class="pad_top">Job ID:</label>
      <input type="text" name="txtJobId" required value="<%= reEmp==null? "": reEmp.getJob_Id()%>" ><br>
      <label class="pad_top">Salary:</label>
      <input type="number" name="txtSalary" step="0.01" min="0" max="999999.99" value="<%= reEmp==null || reEmp.getSalary() < 0? "": reEmp.getSalary()%>" ><br>
      <label class="pad_top">Comm Pct:</label>
      <input type="number" name="txtCommPct" step="0.01" min="0" max="0.99" value="<%= reEmp==null || reEmp.getCommission_pct() < 0? "": reEmp.getCommission_pct()%>" ><br>
      <label class="pad_top">Manager ID:</label>
      <input type="number" name="txtManagerId" min="0" max="999999" value="<%= reEmp==null || reEmp.getManager_id() < 0? "": reEmp.getManager_id()%>" ><br>
      <label class="pad_top">Department:</label>
      
      
      <% @SuppressWarnings("unchecked")
      LinkedHashMap<Integer, String> hmap = (LinkedHashMap<Integer, String>)request.getAttribute("deptlist"); 
      %>
      <select name="cmbDepartId">
         <% 
         if (hmap != null) {
         	Set<?> set = hmap.entrySet();
            Iterator<?> iterator = set.iterator();
            int oldValue = -1;
            if (reEmp != null ) oldValue = reEmp.getDepartment_id();
            while(iterator.hasNext()) {
               Map.Entry<?,?> mentry = (Map.Entry<?,?>)iterator.next();
               if (oldValue == (int)mentry.getKey()) {
         %>
                  <option  selected="selected" value="<%= mentry.getKey() %>"><%= mentry.getValue() %></option>
         <%    } else {%>
                  <option value="<%= mentry.getKey() %>"><%= mentry.getValue() %></option>
         <%
               }
            }
         }
         %>
      </select><br>
</body>
</html>