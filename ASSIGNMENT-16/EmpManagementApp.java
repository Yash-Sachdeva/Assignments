import java.sql.*;
import java.util.*;
import java.io.*;
class Employee 
{
    private int eid;
    private String name;
    private int age;
    private float salary;
    private String designation;
    private String department;
    public Employee(int choice) 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter EID: ");
        this.eid = sc.nextInt();
        sc.nextLine(); 
        System.out.println("Enter Name: ");
        this.name = sc.nextLine();
        System.out.println("Enter Age: ");
        this.age = sc.nextInt();
        System.out.println("Enter Salary: ");
        this.salary = sc.nextFloat();
        sc.nextLine(); 
        if (choice == 1)
        {
            this.designation = "Clerk";
            this.department = "Manager";
        }
        else if (choice == 2) 
        {
            this.designation = "Programmer";
            this.department = "Development";
        } 
        else if (choice == 3)
        {
            this.designation = "Manager";
            this.department = "Management";
        } 
        else if (choice == 4) 
        {
            this.designation = "Other";
            this.department = "Miscellaneous";
        }
    }
    public static void insertIntoDB(Employee e) 
    {   
        PreparedStatement stmt=null;
        Connection conn=null;
        try 
        {
            conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/empdb","postgres","tiger");
            stmt = conn.prepareStatement("INSERT INTO emp (eid, name, age, salary, designation, department) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, e.eid);
            stmt.setString(2, e.name);
            stmt.setInt(3, e.age);
            stmt.setFloat(4, e.salary);
            stmt.setString(5, e.designation);
            stmt.setString(6, e.department);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) 
            {
                System.out.println("Employee inserted successfully!");
            }
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        } 
    }

    public static void displayEmployees() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------------------");
        System.out.println("1. By ID");
        System.out.println("2. By Name");
        System.out.println("3. By Designation");
        System.out.println("4. By Age");
        System.out.println("5. By Salary");
        System.out.println("6. Show All");
        System.out.println("7. Exit");
        System.out.println("-----------------------");
        int choice = Menu.readChoice(7);  
        if (choice == 7) 
        {
            return; 
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try 
        {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/empdb", "postgres", "tiger");
            String query = "";
            switch (choice) 
            {
                case 1:  
                    System.out.println("Enter the EID of the employee:");
                    int eid = sc.nextInt();
                    query = "SELECT * FROM emp WHERE eid = ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setInt(1, eid);
                    break;
                case 2:  
                    System.out.println("Enter the name of the employee:");
                    sc.nextLine();  
                    String name = sc.nextLine();
                    query = "SELECT * FROM emp WHERE name LIKE ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, "%" + name + "%");  
                    break;
                case 3:  
                    System.out.println("Enter the designation of the employee:");
                    String designation = sc.nextLine();
                    query = "SELECT * FROM emp WHERE designation LIKE ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, "%" + designation + "%");  
                    break;
                case 4:  
                    System.out.println("Enter the age of the employee:");
                    int age = sc.nextInt();
                    query = "SELECT * FROM emp WHERE age = ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setInt(1, age);
                    break;
                case 5:  
                    System.out.println("Enter the salary of the employee:");
                    float salary = sc.nextFloat();
                    query = "SELECT * FROM emp WHERE salary = ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setFloat(1, salary);
                    break;
                case 6:  
                    query = "SELECT * FROM emp";
                    stmt = conn.prepareStatement(query);
                    break;
                default:
                    System.out.println("Not a valid choice.");
                    return;
            }
            rs = stmt.executeQuery();
            
            System.out.println("Employee Details:");
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-10s %-10s %-20s %-20s\n", "EID", "Name", "Age", "Salary", "Designation", "Department");
            System.out.println("------------------------------------------------------------------------------------------ ");
            
            while (rs.next()) {
                int empId = rs.getInt("eid");
                String empName = rs.getString("name");
                int empAge = rs.getInt("age");
                float empSalary = rs.getFloat("salary");
                String empDesignation = rs.getString("designation");
                String empDepartment = rs.getString("department");

                System.out.printf("%-10d %-20s %-10d %-10.2f %-20s %-20s\n", empId, empName, empAge, empSalary, empDesignation, empDepartment);
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        }
    }

    public static void appraisal() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the EID of the employee you want to appraise:");
        int eid = sc.nextInt();
        System.out.println("Enter the appraisal amount to be added to the salary:");
        float appraisalAmount = sc.nextFloat();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/empdb", "postgres", "tiger");
            stmt = conn.prepareStatement("SELECT salary FROM emp WHERE eid = ?");
            stmt.setInt(1, eid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                float currentSalary = rs.getFloat("salary");
                float newSalary = currentSalary + appraisalAmount;
                stmt = conn.prepareStatement("UPDATE emp SET salary = ? WHERE eid = ?");
                stmt.setFloat(1, newSalary);
                stmt.setInt(2, eid);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) 
                {
                    System.out.println("Employee's salary updated successfully!");
                    System.out.println("New Salary: " + newSalary);
                } 
                else 
                {
                    System.out.println("No employee found with EID: " + eid);
                }
            } 
            else 
            {
                System.out.println("No employee found with EID: " + eid);
            }

        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        }
    }

    public static void deleteEmployee()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the EID of the employee you want to delete:");
        int eid = sc.nextInt();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/empdb", "postgres", "tiger");
            stmt = conn.prepareStatement("SELECT name FROM emp WHERE eid = ?");
            stmt.setInt(1, eid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                stmt = conn.prepareStatement("DELETE from emp WHERE eid = ?");
                stmt.setInt(1, eid);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) 
                {
                    System.out.println("Deleted Successfully");
                } 
                else 
                {
                    System.out.println("No employee found with EID: " + eid);
                }
            } 
            else 
            {
                System.out.println("No employee found with EID: " + eid);
            }

        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        }
    }

    public static void search()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the EID of the employee you want to delete:");
        int eid = sc.nextInt();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/empdb", "postgres", "tiger");
            stmt = conn.prepareStatement("SELECT name FROM emp WHERE eid = ?");
            stmt.setInt(1, eid);
            rs = stmt.executeQuery();
            if (rs.next()) 
            {
                stmt = conn.prepareStatement("DELETE from emp WHERE eid = ?");
                stmt.setInt(1, eid);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) 
                {
                    System.out.println("Deleted Successfully");
                } 
                else 
                {
                    System.out.println("No employee found with EID: " + eid);
                }
            } 
            else 
            {
                System.out.println("No employee found with EID: " + eid);
            }

        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        }
    }
}

class Menu {
    public static int readChoice(int maxChoice) {
        Scanner sc = new Scanner(System.in);
        int choice;
        try {
            choice = sc.nextInt();
            if (choice > maxChoice) {
                System.out.println("Please choose between 1 and " + maxChoice);
                return 0;
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
            return 0;
        }
        return choice;
    }
}

public class EmpManagementApp 
{
    public static void main(String args[]) 
    {
        Scanner sc = new Scanner(System.in);
        while (true)
        {
            System.out.println("Enter Choice :-");
            System.out.println("-------------");
            System.out.println("1. Create\n2. Display\n3. Appraisal\n4. Search\n5. Remove\n6. Exit");
            System.out.println("-------------");
            int choice1 = Menu.readChoice(6);
            if (choice1 == 0)
                continue;
            if (choice1 == 6)
                break;
            switch (choice1) 
            {
                case 1:
                    {
                        while (true) {
                            int choice2;
                            System.out.println("Enter Choice :-");
                            System.out.println("-------------");
                            System.out.println("1. Clerk\n2. Programmer\n3. Manager\n4. Others\n5. Exit");
                            System.out.println("-------------");
                            choice2 = Menu.readChoice(5);
                            if (choice2 == 5)
                                break;
                            switch (choice2) 
                            {
                                case 1:
                                    {
                                        Employee e = new Employee(1); // Clerk
                                        Employee.insertIntoDB(e);
                                        break;
                                    }
                                case 2:
                                    {
                                        Employee e = new Employee(2); // Programmer
                                        Employee.insertIntoDB(e);
                                        break;
                                    }
                                case 3:
                                    {
                                        Employee e = new Employee(3); // Manager
                                        Employee.insertIntoDB(e);
                                        break;
                                    }
                                case 4:
                                    {
                                        Employee e = new Employee(4); // Other
                                        Employee.insertIntoDB(e);
                                        break;
                                    }
                                default:
                                    {
                                        System.out.println("Not a Valid Command");
                                        break;
                                    }
                            }
                        }
                        break;
                    }
                case 2:
                    {
                        Employee.displayEmployees();
                        break;
                    }
                case 3:
                    {
                        Employee.appraisal();
                        break;
                    }
                case 4:
                    {
                        Employee.search();
                        break;
                    }
                case 5:
                    {
                        Employee.deleteEmployee();
                        break;
                    }
                default:
                    {
                        System.out.println("Not a Valid Command");
                        break;
                    }
            }
        }
    }
}
