package ca.myseneca.model;

import java.io.Serializable;
import java.sql.Date;

public class Employee implements Serializable {
   private static final long serialVersionUID = 1L;
   int employee_id;
   String first_Name;
   String last_Name;
   String email;
   String phNO;
   Date hireDate;
   String job_Id;
   double salary;
   double commission_pct;
   int manager_id;
   int department_id;

   public Employee() {
   }

   public Employee(int employee_id, String first_Name, String last_Name, String email, String phNO, Date hireDate, String job_Id, double salary, double commission_pct, int manager_id, int department_id) {
      this.employee_id = employee_id;
      this.first_Name = first_Name;
      this.last_Name = last_Name;
      this.email = email;
      this.phNO = phNO;
      this.hireDate = hireDate;
      this.job_Id = job_Id;
      this.salary = salary;
      this.commission_pct = commission_pct;
      this.manager_id = manager_id;
      this.department_id = department_id;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      long temp = Double.doubleToLongBits(this.commission_pct);
      int result = 31 * result + (int)(temp ^ temp >>> 32);
      result = 31 * result + this.department_id;
      result = 31 * result + (this.email == null ? 0 : this.email.hashCode());
      result = 31 * result + this.employee_id;
      result = 31 * result + (this.first_Name == null ? 0 : this.first_Name.hashCode());
      result = 31 * result + (this.hireDate == null ? 0 : this.hireDate.hashCode());
      result = 31 * result + (this.job_Id == null ? 0 : this.job_Id.hashCode());
      result = 31 * result + (this.last_Name == null ? 0 : this.last_Name.hashCode());
      result = 31 * result + this.manager_id;
      result = 31 * result + (this.phNO == null ? 0 : this.phNO.hashCode());
      temp = Double.doubleToLongBits(this.salary);
      result = 31 * result + (int)(temp ^ temp >>> 32);
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         Employee other = (Employee)obj;
         if (Double.doubleToLongBits(this.commission_pct) != Double.doubleToLongBits(other.commission_pct)) {
            return false;
         } else if (this.department_id != other.department_id) {
            return false;
         } else {
            if (this.email == null) {
               if (other.email != null) {
                  return false;
               }
            } else if (!this.email.equals(other.email)) {
               return false;
            }

            if (this.employee_id != other.employee_id) {
               return false;
            } else {
               if (this.first_Name == null) {
                  if (other.first_Name != null) {
                     return false;
                  }
               } else if (!this.first_Name.equals(other.first_Name)) {
                  return false;
               }

               if (this.hireDate == null) {
                  if (other.hireDate != null) {
                     return false;
                  }
               } else if (!this.hireDate.equals(other.hireDate)) {
                  return false;
               }

               if (this.job_Id == null) {
                  if (other.job_Id != null) {
                     return false;
                  }
               } else if (!this.job_Id.equals(other.job_Id)) {
                  return false;
               }

               if (this.last_Name == null) {
                  if (other.last_Name != null) {
                     return false;
                  }
               } else if (!this.last_Name.equals(other.last_Name)) {
                  return false;
               }

               if (this.manager_id != other.manager_id) {
                  return false;
               } else {
                  if (this.phNO == null) {
                     if (other.phNO != null) {
                        return false;
                     }
                  } else if (!this.phNO.equals(other.phNO)) {
                     return false;
                  }

                  return Double.doubleToLongBits(this.salary) == Double.doubleToLongBits(other.salary);
               }
            }
         }
      }
   }

   public String toString() {
      return "Employee [employee_id=" + this.employee_id + ", first_Name=" + this.first_Name + ", last_Name=" + this.last_Name + ", email=" + this.email + ", phNO=" + this.phNO + ", hireDate=" + this.hireDate + ", job_Id=" + this.job_Id + ", salary=" + this.salary + ", commission_pct=" + this.commission_pct + ", manager_id=" + this.manager_id + ", department_id=" + this.department_id + "]";
   }

   public int getEmployee_id() {
      return this.employee_id;
   }

   public void setEmployee_id(int employee_id) {
      this.employee_id = employee_id;
   }

   public String getFirst_Name() {
      return this.first_Name;
   }

   public void setFirst_Name(String first_Name) {
      this.first_Name = first_Name;
   }

   public String getLast_Name() {
      return this.last_Name;
   }

   public void setLast_Name(String last_Name) {
      this.last_Name = last_Name;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhNO() {
      return this.phNO;
   }

   public void setPhNO(String phNO) {
      this.phNO = phNO;
   }

   public Date getHireDate() {
      return this.hireDate;
   }

   public void setHireDate(Date hireDate) {
      this.hireDate = hireDate;
   }

   public String getJob_Id() {
      return this.job_Id;
   }

   public void setJob_Id(String job_Id) {
      this.job_Id = job_Id;
   }

   public double getSalary() {
      return this.salary;
   }

   public void setSalary(double salary) {
      this.salary = salary;
   }

   public double getCommission_pct() {
      return this.commission_pct;
   }

   public void setCommission_pct(double commission_pct) {
      this.commission_pct = commission_pct;
   }

   public int getManager_id() {
      return this.manager_id;
   }

   public void setManager_id(int manager_id) {
      this.manager_id = manager_id;
   }

   public int getDepartment_id() {
      return this.department_id;
   }

   public void setDepartment_id(int department_id) {
      this.department_id = department_id;
   }
}
    