package ca.myseneca.model;

import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.sql.DataSource;

public class DAManager {
   public static int getEmployeeID(DataSource ds, String user, String password) {
      int empId = 0;
      Connection connection = null;
      CallableStatement cs = null;

      try {
         connection = ds.getConnection();
         String sqlsec = "{ ? = call P_SECURITY.F_SECURITY(?,?) }";
         cs = connection.prepareCall(sqlsec);
         cs.setString(2, user);
         cs.setString(3, password);
         cs.registerOutParameter(1, 4);
         cs.execute();
         empId = cs.getInt(1);
      } catch (SQLException var15) {
         DBUtil.printSQLException(var15);
      } finally {
         try {
            if (cs != null) {
               cs.close();
            }
         } catch (SQLException var14) {
            DBUtil.printSQLException(var14);
         }

         DBUtil.closeConnection(connection);
      }

      return empId;
   }

   public static int addEmployee(DataSource ds, Employee emp) {
      Connection connection = null;
      PreparedStatement ps = null;
      byte rowCount = -1;

      try {
         connection = ds.getConnection();
         String query = "INSERT INTO EMPLOYEES (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
         ps = connection.prepareStatement(query);
         ps = setEmpFields(emp, ps);
         ps.executeUpdate();
         rowCount = 1;
      } catch (SQLException var14) {
         DBUtil.printSQLException(var14);
      } finally {
         try {
            if (ps != null) {
               ps.close();
            }
         } catch (SQLException var13) {
            DBUtil.printSQLException(var13);
         }

         DBUtil.closeConnection(connection);
      }

      return rowCount;
   }

   public static ArrayList<Employee> getAllEmployees(DataSource ds) {
      ArrayList<Employee> emps = new ArrayList();
      Connection connection = null;
      Statement statement = null;
      ResultSet rs = null;

      try {
         connection = ds.getConnection();
         statement = connection.createStatement();
         String query = "SELECT * FROM EMPLOYEES ORDER BY EMPLOYEE_ID";
         rs = statement.executeQuery(query);

         while(rs.next()) {
            Employee emp = createEmployee(rs);
            emps.add(emp);
         }
      } catch (SQLException var19) {
         DBUtil.printSQLException(var19);
         emps = null;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var18) {
            DBUtil.printSQLException(var18);
         }

         try {
            if (statement != null) {
               statement.close();
            }
         } catch (SQLException var17) {
            DBUtil.printSQLException(var17);
         }

         DBUtil.closeConnection(connection);
      }

      return emps;
   }

   public static ArrayList<Employee> getEmployeesByDepartmentID(DataSource ds, int deptId) {
      ArrayList<Employee> emps = new ArrayList();
      Connection connection = null;
      Statement statement = null;
      ResultSet rs = null;

      try {
         connection = ds.getConnection();
         statement = connection.createStatement();
         String query = "SELECT * FROM EMPLOYEES WHERE DEPARTMENT_ID = " + deptId + " ORDER BY EMPLOYEE_ID";
         rs = statement.executeQuery(query);

         while(rs.next()) {
            Employee emp = createEmployee(rs);
            emps.add(emp);
         }
      } catch (SQLException var20) {
         DBUtil.printSQLException(var20);
         emps = null;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var19) {
            DBUtil.printSQLException(var19);
         }

         try {
            if (statement != null) {
               statement.close();
            }
         } catch (SQLException var18) {
            DBUtil.printSQLException(var18);
         }

         DBUtil.closeConnection(connection);
      }

      return emps;
   }

   public static ArrayList<Employee> getEmployeesByDepartmentID(DataSource ds, int deptId, int offset, int recToRead) {
      ArrayList<Employee> emps = new ArrayList();
      Connection connection = null;
      Statement statement = null;
      ResultSet rs = null;
      String query;
      if (deptId == 0) {
         query = "SELECT * FROM (SELECT rownum rnum, A.* FROM ( SELECT * FROM EMPLOYEES WHERE DEPARTMENT_ID IS NULL ORDER BY EMPLOYEE_ID ) A WHERE rownum <= " + (offset + recToRead) + " ) WHERE rnum > " + offset;
      } else if (deptId > 0) {
         query = "SELECT * FROM (SELECT rownum rnum, A.* FROM ( SELECT * FROM EMPLOYEES WHERE DEPARTMENT_ID = " + deptId + " ORDER BY EMPLOYEE_ID" + " ) A WHERE rownum <= " + (offset + recToRead) + " ) WHERE rnum > " + offset;
      } else {
         query = "SELECT * FROM (SELECT rownum rnum, A.* FROM ( SELECT * FROM EMPLOYEES ORDER BY EMPLOYEE_ID ) A WHERE rownum <= " + (offset + recToRead) + " ) WHERE rnum > " + offset;
      }

      try {
         connection = ds.getConnection();
         statement = connection.createStatement();
         rs = statement.executeQuery(query);

         while(rs.next()) {
            Employee emp = createEmployee(rs);
            emps.add(emp);
         }
      } catch (SQLException var22) {
         DBUtil.printSQLException(var22);
         emps = null;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var21) {
            DBUtil.printSQLException(var21);
         }

         try {
            if (statement != null) {
               statement.close();
            }
         } catch (SQLException var20) {
            DBUtil.printSQLException(var20);
         }

         DBUtil.closeConnection(connection);
      }

      return emps;
   }

   public static int getEmployeesByDepartmentCount(DataSource ds, int deptId) {
      int count = 0;
      Connection connection = null;
      Statement statement = null;
      ResultSet rs = null;
      String query;
      if (deptId == 0) {
         query = "SELECT COUNT(*) AS NUM_OF_COUNT FROM EMPLOYEES WHERE DEPARTMENT_ID IS NULL";
      } else if (deptId > 0) {
         query = "SELECT COUNT(*) AS NUM_OF_COUNT FROM EMPLOYEES WHERE DEPARTMENT_ID = " + deptId;
      } else {
         query = " SELECT COUNT(*) AS NUM_OF_COUNT FROM EMPLOYEES";
      }

      try {
         connection = ds.getConnection();
         statement = connection.createStatement();
         rs = statement.executeQuery(query);
         if (rs.next()) {
            count = rs.getInt("NUM_OF_COUNT");
         }
      } catch (SQLException var20) {
         DBUtil.printSQLException(var20);
         count = 0;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var19) {
            DBUtil.printSQLException(var19);
         }

         try {
            if (statement != null) {
               statement.close();
            }
         } catch (SQLException var18) {
            DBUtil.printSQLException(var18);
         }

         DBUtil.closeConnection(connection);
      }

      return count;
   }

   public static int getEmployeeSearchCount(DataSource ds, String inStr) {
      int count = 0;
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         connection = ds.getConnection();
         String query = "SELECT COUNT(*) AS NUM_OF_COUNT FROM EMPLOYEES E LEFT OUTER JOIN DEPARTMENTS D ON D.DEPARTMENT_ID = E.DEPARTMENT_ID WHERE UPPER(E.FIRST_NAME) LIKE ? OR UPPER(E.LAST_NAME) LIKE ? OR UPPER(E.PHONE_NUMBER) LIKE ? OR UPPER(E.EMAIL) LIKE ? OR UPPER(D.DEPARTMENT_NAME) LIKE ?";
         ps = connection.prepareStatement(query);
         String searchText = "%" + inStr.toUpperCase() + "%";
         ps.setString(1, searchText);
         ps.setString(2, searchText);
         ps.setString(3, searchText);
         ps.setString(4, searchText);
         ps.setString(5, searchText);
         rs = ps.executeQuery();
         if (rs.next()) {
            count = rs.getInt("NUM_OF_COUNT");
         }
      } catch (SQLException var20) {
         DBUtil.printSQLException(var20);
         count = 0;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var19) {
            DBUtil.printSQLException(var19);
         }

         try {
            if (ps != null) {
               ps.close();
            }
         } catch (SQLException var18) {
            DBUtil.printSQLException(var18);
         }

         DBUtil.closeConnection(connection);
      }

      return count;
   }

   private static ArrayList<EmployeeShortInfo> getShortEmployeesByQuery(DataSource ds, String query, String inStr) {
      ArrayList<EmployeeShortInfo> emps = new ArrayList();
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         connection = ds.getConnection();
         ps = connection.prepareStatement(query);
         String searchText = "%" + inStr.toUpperCase() + "%";
         ps.setString(1, searchText);
         ps.setString(2, searchText);
         ps.setString(3, searchText);
         ps.setString(4, searchText);
         ps.setString(5, searchText);
         rs = ps.executeQuery();

         while(rs.next()) {
            EmployeeShortInfo emp = createEmployeeShortInfo(rs);
            emps.add(emp);
         }
      } catch (SQLException var21) {
         DBUtil.printSQLException(var21);
         emps = null;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var20) {
            DBUtil.printSQLException(var20);
         }

         try {
            if (ps != null) {
               ps.close();
            }
         } catch (SQLException var19) {
            DBUtil.printSQLException(var19);
         }

         DBUtil.closeConnection(connection);
      }

      return emps;
   }

   public static ArrayList<EmployeeShortInfo> getEmployeesByText(DataSource ds, String inStr) {
      String query = "SELECT E.EMPLOYEE_ID , E.FIRST_NAME || ' ' || E.LAST_NAME AS FULL_NAME , D.DEPARTMENT_NAME , E.JOB_ID , E.SALARY , E.EMAIL , E.PHONE_NUMBER FROM EMPLOYEES E LEFT OUTER JOIN DEPARTMENTS D ON D.DEPARTMENT_ID = E.DEPARTMENT_ID WHERE UPPER(E.FIRST_NAME) LIKE ? OR UPPER(E.LAST_NAME) LIKE ? OR UPPER(E.PHONE_NUMBER) LIKE ? OR UPPER(E.EMAIL) LIKE ? OR UPPER(D.DEPARTMENT_NAME) LIKE ? ORDER BY FULL_NAME";
      return getShortEmployeesByQuery(ds, query, inStr);
   }

   public static ArrayList<EmployeeShortInfo> getEmployeesByText(DataSource ds, String inStr, int offset, int recToRead) {
      String query = "SELECT * FROM (SELECT rownum rnum, A.* FROM ( SELECT   E.EMPLOYEE_ID , E.FIRST_NAME || ' ' || E.LAST_NAME AS FULL_NAME , D.DEPARTMENT_NAME , E.JOB_ID , E.SALARY , E.EMAIL , E.PHONE_NUMBER FROM EMPLOYEES E LEFT OUTER JOIN DEPARTMENTS D ON D.DEPARTMENT_ID = E.DEPARTMENT_ID WHERE UPPER(E.FIRST_NAME) LIKE ? OR UPPER(E.LAST_NAME) LIKE ? OR UPPER(E.PHONE_NUMBER) LIKE ? OR UPPER(E.EMAIL) LIKE ? OR UPPER(D.DEPARTMENT_NAME) LIKE ? ORDER BY FULL_NAME ) A WHERE rownum <= " + (offset + recToRead) + " ) WHERE rnum > " + offset;
      return getShortEmployeesByQuery(ds, query, inStr);
   }

   public static Employee getEmployeeByID(DataSource ds, int empid) {
      Employee emp = null;
      Connection connection = null;
      CallableStatement cs = null;
      ResultSet rs = null;

      try {
         connection = ds.getConnection();
         String sqlemp = "{ call P_SECURITY.P_EMP_INFO(?,?) }";
         cs = connection.prepareCall(sqlemp);
         cs.setInt(1, empid);
         cs.registerOutParameter(2, -10);
         cs.execute();
         rs = (ResultSet)cs.getObject(2);
         if (rs.next()) {
            emp = createEmployee(rs);
         }
      } catch (SQLException var19) {
         DBUtil.printSQLException(var19);
         emp = null;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var18) {
            DBUtil.printSQLException(var18);
         }

         try {
            if (cs != null) {
               cs.close();
            }
         } catch (SQLException var17) {
            DBUtil.printSQLException(var17);
         }

         DBUtil.closeConnection(connection);
      }

      return emp;
   }

   public static String getDeptNameById(DataSource ds, int deptId) {
      String deptName = null;
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         connection = ds.getConnection();
         String query = "SELECT DEPARTMENT_NAME FROM DEPARTMENTS WHERE DEPARTMENT_ID = ?";
         ps = connection.prepareStatement(query);
         ps.setInt(1, deptId);
         rs = ps.executeQuery();
         if (rs.next()) {
            deptName = rs.getString("DEPARTMENT_NAME");
         }
      } catch (SQLException var19) {
         DBUtil.printSQLException(var19);
         deptName = null;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var18) {
            DBUtil.printSQLException(var18);
         }

         try {
            if (ps != null) {
               ps.close();
            }
         } catch (SQLException var17) {
            DBUtil.printSQLException(var17);
         }

         DBUtil.closeConnection(connection);
      }

      return deptName;
   }

   public static int validateEmployeeByEmail(DataSource ds, int empId, String inEmail) {
      int count = -1;
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         connection = ds.getConnection();
         String query = "SELECT COUNT(*) AS NUM_OF_COUNT FROM EMPLOYEES WHERE EMPLOYEE_ID <> ? AND EMAIL = ?";
         ps = connection.prepareStatement(query);
         ps.setInt(1, empId);
         ps.setString(2, inEmail);
         rs = ps.executeQuery();
         if (rs.next()) {
            count = rs.getInt("NUM_OF_COUNT");
         }
      } catch (SQLException var20) {
         DBUtil.printSQLException(var20);
         count = -1;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var19) {
            DBUtil.printSQLException(var19);
         }

         try {
            if (ps != null) {
               ps.close();
            }
         } catch (SQLException var18) {
            DBUtil.printSQLException(var18);
         }

         DBUtil.closeConnection(connection);
      }

      return count;
   }

   public static boolean validateJobId(DataSource ds, String jobId) {
      int count = -1;
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         connection = ds.getConnection();
         String query = "SELECT COUNT(*) AS NUM_OF_COUNT FROM JOBS WHERE JOB_ID = ?";
         ps = connection.prepareStatement(query);
         ps.setString(1, jobId);
         rs = ps.executeQuery();
         if (rs.next()) {
            count = rs.getInt("NUM_OF_COUNT");
         }
      } catch (SQLException var19) {
         DBUtil.printSQLException(var19);
         count = -1;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var18) {
            DBUtil.printSQLException(var18);
         }

         try {
            if (ps != null) {
               ps.close();
            }
         } catch (SQLException var17) {
            DBUtil.printSQLException(var17);
         }

         DBUtil.closeConnection(connection);
      }

      return count > 0;
   }

   public static int updateEmployee(DataSource ds, Employee emp) {
      int rowCount = false;
      Connection connection = null;
      PreparedStatement ps = null;

      int rowCount;
      try {
         connection = ds.getConnection();
         String query = "UPDATE EMPLOYEES SET  EMPLOYEE_ID = ?, FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, PHONE_NUMBER = ?, HIRE_DATE = ?, JOB_ID = ?, SALARY = ?, COMMISSION_PCT = ?, MANAGER_ID = ?, DEPARTMENT_ID = ?  WHERE EMPLOYEE_ID = ?";
         ps = connection.prepareStatement(query);
         ps = setEmpFields(emp, ps);
         ps.setInt(12, emp.employee_id);
         rowCount = ps.executeUpdate();
      } catch (SQLException var14) {
         DBUtil.printSQLException(var14);
         rowCount = -1;
      } finally {
         try {
            if (ps != null) {
               ps.close();
            }
         } catch (SQLException var13) {
            DBUtil.printSQLException(var13);
         }

         DBUtil.closeConnection(connection);
      }

      return rowCount;
   }

   public static int deleteEmployeeByID(DataSource ds, int empid) {
      int rowCount = false;
      Connection connection = null;
      PreparedStatement ps = null;

      int rowCount;
      try {
         connection = ds.getConnection();
         String query = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
         ps = connection.prepareStatement(query);
         ps.setInt(1, empid);
         rowCount = ps.executeUpdate();
      } catch (SQLException var14) {
         DBUtil.printSQLException(var14);
         rowCount = -1;
      } finally {
         try {
            if (ps != null) {
               ps.close();
            }
         } catch (SQLException var13) {
            DBUtil.printSQLException(var13);
         }

         DBUtil.closeConnection(connection);
      }

      return rowCount;
   }

   public static boolean batchUpdate(DataSource ds, String[] SQLs) {
      boolean rc = false;
      Connection connection = null;
      Statement statement = null;

      try {
         connection = ds.getConnection();
         connection.setAutoCommit(false);
         statement = connection.createStatement();
         String[] var8 = SQLs;
         int var7 = SQLs.length;

         int i;
         for(i = 0; i < var7; ++i) {
            String inSQL = var8[i];
            statement.addBatch(inSQL);
         }

         try {
            int[] recordsAffected = statement.executeBatch();

            for(i = 0; i < recordsAffected.length; ++i) {
               System.out.println("SQL " + i + " updates " + recordsAffected[i] + " rows.");
            }

            connection.commit();
            rc = true;
         } catch (BatchUpdateException var20) {
            DBUtil.printBatchUpdateException(var20);

            try {
               connection.rollback();
            } catch (Exception var19) {
               var19.printStackTrace();
            }
         }
      } catch (SQLException var21) {
         DBUtil.printSQLException(var21);
      } finally {
         try {
            if (statement != null) {
               statement.close();
            }
         } catch (SQLException var18) {
            DBUtil.printSQLException(var18);
         }

         DBUtil.closeConnection(connection);
      }

      return rc;
   }

   private static Employee createEmployee(ResultSet rs) throws SQLException {
      Employee emp = new Employee();
      int eid = rs.getInt("EMPLOYEE_ID");
      String fname = rs.getString("FIRST_NAME");
      if (rs.wasNull()) {
         fname = "";
      }

      String lname = rs.getString("LAST_NAME");
      String email = rs.getString("EMAIL");
      String phNO = rs.getString("PHONE_NUMBER");
      if (rs.wasNull()) {
         phNO = "";
      }

      Date hireDate = rs.getDate("HIRE_DATE");
      String job_Id = rs.getString("JOB_ID");
      double salary = rs.getDouble("SALARY");
      if (rs.wasNull()) {
         salary = -1.0D;
      }

      double commission_pct = rs.getDouble("COMMISSION_PCT");
      if (rs.wasNull()) {
         commission_pct = -1.0D;
      }

      int manager_id = rs.getInt("MANAGER_ID");
      if (rs.wasNull()) {
         manager_id = -1;
      }

      int department_id = rs.getInt("DEPARTMENT_ID");
      if (rs.wasNull()) {
         department_id = -1;
      }

      emp.setEmployee_id(eid);
      emp.setFirst_Name(fname);
      emp.setLast_Name(lname);
      emp.setEmail(email);
      emp.setPhNO(phNO);
      emp.setHireDate(hireDate);
      emp.setJob_Id(job_Id);
      emp.setSalary(salary);
      emp.setCommission_pct(commission_pct);
      emp.setManager_id(manager_id);
      emp.setDepartment_id(department_id);
      return emp;
   }

   private static EmployeeShortInfo createEmployeeShortInfo(ResultSet rs) throws SQLException {
      EmployeeShortInfo emp = new EmployeeShortInfo();
      int eid = rs.getInt("EMPLOYEE_ID");
      String fname = rs.getString("FULL_NAME");
      String dname = rs.getString("DEPARTMENT_NAME");
      if (rs.wasNull()) {
         dname = "";
      }

      String job_Id = rs.getString("JOB_ID");
      double salary = rs.getDouble("SALARY");
      if (rs.wasNull()) {
         salary = -1.0D;
      }

      String email = rs.getString("EMAIL");
      String phNO = rs.getString("PHONE_NUMBER");
      if (rs.wasNull()) {
         phNO = "";
      }

      emp.setEmployee_id(eid);
      emp.setFull_name(fname);
      emp.setDepartment_name(dname);
      emp.setJob_Id(job_Id);
      emp.setSalary(salary);
      emp.setEmail(email);
      emp.setPhNO(phNO);
      return emp;
   }

   private static PreparedStatement setEmpFields(Employee emp, PreparedStatement ps) throws SQLException {
      ps.setInt(1, emp.getEmployee_id());
      if (emp.getFirst_Name().equalsIgnoreCase("")) {
         ps.setNull(2, 12);
      } else {
         ps.setString(2, emp.getFirst_Name());
      }

      ps.setString(3, emp.getLast_Name());
      ps.setString(4, emp.getEmail());
      if (emp.getPhNO().equalsIgnoreCase("")) {
         ps.setNull(5, 12);
      } else {
         ps.setString(5, emp.getPhNO());
      }

      ps.setDate(6, emp.getHireDate());
      ps.setString(7, emp.getJob_Id());
      if (emp.getSalary() < 0.0D) {
         ps.setNull(8, 8);
      } else {
         ps.setDouble(8, emp.getSalary());
      }

      if (emp.getCommission_pct() < 0.0D) {
         ps.setNull(9, 8);
      } else {
         ps.setDouble(9, emp.getCommission_pct());
      }

      if (emp.getManager_id() < 0) {
         ps.setNull(10, 4);
      } else {
         ps.setInt(10, emp.getManager_id());
      }

      if (emp.getDepartment_id() < 0) {
         ps.setNull(11, 4);
      } else {
         ps.setInt(11, emp.getDepartment_id());
      }

      return ps;
   }

   public static LinkedHashMap<Integer, String> getDeptHashMap(DataSource ds) {
      LinkedHashMap<Integer, String> deptHMap = new LinkedHashMap();
      Connection connection = null;
      Statement statement = null;
      ResultSet rs = null;

      try {
         connection = ds.getConnection();
         statement = connection.createStatement();
         String query = "SELECT DEPARTMENT_ID, DEPARTMENT_NAME FROM DEPARTMENTS ORDER BY DEPARTMENT_NAME";
         rs = statement.executeQuery(query);
         deptHMap.put(-1, " ");

         while(rs.next()) {
            deptHMap.put(rs.getInt("DEPARTMENT_ID"), rs.getString("DEPARTMENT_NAME"));
         }
      } catch (SQLException var18) {
         DBUtil.printSQLException(var18);
         deptHMap = null;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var17) {
            DBUtil.printSQLException(var17);
         }

         try {
            if (statement != null) {
               statement.close();
            }
         } catch (SQLException var16) {
            DBUtil.printSQLException(var16);
         }

         DBUtil.closeConnection(connection);
      }

      return deptHMap;
   }
}
    