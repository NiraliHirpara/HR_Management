package ca.myseneca.model;

import java.io.Serializable;

public class EmployeeShortInfo implements Serializable {
   private static final long serialVersionUID = 1L;
   int employee_id;
   String full_name;
   String department_name;
   String job_Id;
   double salary;
   String email;
   String phNO;

   public EmployeeShortInfo() {
   }

   public EmployeeShortInfo(int employee_id, String full_name, String department_name, String job_Id, double salary, String email, String phNO) {
      this.employee_id = employee_id;
      this.full_name = full_name;
      this.department_name = department_name;
      this.job_Id = job_Id;
      this.salary = salary;
      this.email = email;
      this.phNO = phNO;
   }

   public int getEmployee_id() {
      return this.employee_id;
   }

   public void setEmployee_id(int employee_id) {
      this.employee_id = employee_id;
   }

   public String getFull_name() {
      return this.full_name;
   }

   public void setFull_name(String full_name) {
      this.full_name = full_name;
   }

   public String getDepartment_name() {
      return this.department_name;
   }

   public void setDepartment_name(String department_name) {
      this.department_name = department_name;
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

   public int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + (this.department_name == null ? 0 : this.department_name.hashCode());
      result = 31 * result + (this.email == null ? 0 : this.email.hashCode());
      result = 31 * result + this.employee_id;
      result = 31 * result + (this.full_name == null ? 0 : this.full_name.hashCode());
      result = 31 * result + (this.job_Id == null ? 0 : this.job_Id.hashCode());
      result = 31 * result + (this.phNO == null ? 0 : this.phNO.hashCode());
      long temp = Double.doubleToLongBits(this.salary);
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
         EmployeeShortInfo other = (EmployeeShortInfo)obj;
         if (this.department_name == null) {
            if (other.department_name != null) {
               return false;
            }
         } else if (!this.department_name.equals(other.department_name)) {
            return false;
         }

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
            if (this.full_name == null) {
               if (other.full_name != null) {
                  return false;
               }
            } else if (!this.full_name.equals(other.full_name)) {
               return false;
            }

            if (this.job_Id == null) {
               if (other.job_Id != null) {
                  return false;
               }
            } else if (!this.job_Id.equals(other.job_Id)) {
               return false;
            }

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

   public String toString() {
      return "EmployeeShortInfo [employee_id=" + this.employee_id + ", full_name=" + this.full_name + ", department_name=" + this.department_name + ", job_Id=" + this.job_Id + ", salary=" + this.salary + ", email=" + this.email + ", phNO=" + this.phNO + "]";
   }
}