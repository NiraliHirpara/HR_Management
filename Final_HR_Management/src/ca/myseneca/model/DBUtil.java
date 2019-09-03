package ca.myseneca.model;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {
   public static DataSource getDataSource() {
      try {
         Context initContext = new InitialContext();
         Context envContext = (Context)initContext.lookup("java:/comp/env");
         return (DataSource)envContext.lookup("jdbc/hrdb");
      } catch (NamingException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static void printWarnings(SQLWarning warning) throws SQLException {
      if (warning != null) {
         System.out.println("\n---Warning---\n");

         while(warning != null) {
            System.out.println("Message: " + warning.getMessage());
            System.out.println("SQLState: " + warning.getSQLState());
            System.out.print("Vendor error code: ");
            System.out.println(warning.getErrorCode());
            System.out.println("");
            warning = warning.getNextWarning();
         }
      }

   }

   public static void printBatchUpdateException(BatchUpdateException b) {
      System.err.println("----BatchUpdateException----");
      System.err.println("SQLState:  " + b.getSQLState());
      System.err.println("Message:  " + b.getMessage());
      System.err.println("Vendor:  " + b.getErrorCode());
      System.err.print("Update counts:  ");
      int[] updateCounts = b.getUpdateCounts();

      for(int i = 0; i < updateCounts.length; ++i) {
         System.err.print(updateCounts[i] + "   ");
      }

   }

   public static void printSQLException(SQLException ex) {
      Iterator var2 = ex.iterator();

      while(true) {
         Throwable e;
         do {
            if (!var2.hasNext()) {
               return;
            }

            e = (Throwable)var2.next();
         } while(!(e instanceof SQLException));

         e.printStackTrace(System.err);
         System.err.println("SQLState: " + ((SQLException)e).getSQLState());
         System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
         System.err.println("Message: " + e.getMessage());

         for(Throwable t = ex.getCause(); t != null; t = t.getCause()) {
            System.out.println("Cause: " + t);
         }
      }
   }

   public static void closeConnection(Connection connArg) {
      System.out.println("Releasing all open resources ...");

      try {
         if (connArg != null) {
            connArg.close();
            connArg = null;
         }
      } catch (SQLException var2) {
         printSQLException(var2);
      }

   }
}