package ca.myseneca.servlet;

import ca.myseneca.model.Employee;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class SessionHelper {
   protected static void setCurrentUser(HttpServletRequest req, String user) {
      HttpSession sess = req.getSession(true);
      sess.setAttribute("USER", user);
   }

   protected static String getCurrentUser(HttpServletRequest req) {
      HttpSession sess = req.getSession(false);
      return sess == null ? null : (String)sess.getAttribute("USER");
   }

   protected static void doLogout(HttpServletRequest req) {
      HttpSession sess = req.getSession(false);
      if (sess != null) {
         sess.invalidate();
      }

   }

   protected static Date convertSqlDate(String strDate) {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

      java.util.Date objDate;
      try {
         objDate = df.parse(strDate);
      } catch (ParseException var4) {
         return null;
      }

      return new Date(objDate.getTime());
   }

   protected static Employee createEmployeeFromJsp(HttpServletRequest request) {
      Employee emp = new Employee();
      int eid = Integer.parseInt(request.getParameter("txtEmpId"));
      Date hireDate = convertSqlDate(request.getParameter("txtHireDate"));

      double salary;
      try {
         salary = Double.parseDouble(request.getParameter("txtSalary"));
      } catch (NumberFormatException var14) {
         salary = -1.0D;
      }

      double commission_pct;
      try {
         commission_pct = Double.parseDouble(request.getParameter("txtCommPct"));
      } catch (NumberFormatException var13) {
         commission_pct = -1.0D;
      }

      int manager_id;
      try {
         manager_id = Integer.parseInt(request.getParameter("txtManagerId"));
      } catch (NumberFormatException var12) {
         manager_id = -1;
      }

      int department_id;
      try {
         department_id = Integer.parseInt(request.getParameter("cmbDepartId"));
      } catch (NumberFormatException var11) {
         department_id = -1;
      }

      emp.setEmployee_id(eid);
      emp.setFirst_Name(request.getParameter("txtFirstName"));
      emp.setLast_Name(request.getParameter("txtLastName"));
      emp.setEmail(request.getParameter("txtEmail"));
      emp.setPhNO(request.getParameter("txtPhone"));
      emp.setHireDate(hireDate);
      emp.setJob_Id(request.getParameter("txtJobId"));
      emp.setSalary(salary);
      emp.setCommission_pct(commission_pct);
      emp.setManager_id(manager_id);
      emp.setDepartment_id(department_id);
      return emp;
   }
}