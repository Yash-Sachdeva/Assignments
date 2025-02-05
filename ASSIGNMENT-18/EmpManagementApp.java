// spring assignment copied here 
package mongoDemo;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.io.*;
import java.util.*;

class Employee {
    private int eid;
    private String name;
    private int age;
    private int salary;
    private String designation;
    private String department;
    private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private static final MongoDatabase database = mongoClient.getDatabase("demodb");
    private static final MongoCollection<Document> collection = database.getCollection("EmpManagement");

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
        this.salary = sc.nextInt();
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
            System.out.println("Enter Designation: ");
            this.designation = sc.nextLine();
            this.department = "Miscellaneous";
        }
    }

    public static void insertIntoDB(Employee e)
    {
        Document doc = new Document();
        doc.append("eid", e.eid);
        doc.append("name", e.name);
        doc.append("age", e.age);
        doc.append("salary", e.salary);
        doc.append("designation", e.designation);
        doc.append("department", e.department);
        collection.insertOne(doc);
        System.out.println("Employee inserted successfully!");
    }

    public static void displayEmployees() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------------------");
        System.out.println("Details of all the employees are:");
        System.out.println("-----------------------");
        Bson filter = null;
        FindIterable<Document> employees = collection.find();
        System.out.println("Employee Details:");
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-10s %-10s %-20s %-20s\n", "EID", "Name", "Age", "Salary", "Designation", "Department");
        System.out.println("------------------------------------------------------------------------------------------");
        for (Document doc : employees) 
        {
            System.out.printf("%-10d %-20s %-10d %-10d %-20s %-20s\n",
                    doc.getInteger("eid"),
                    doc.getString("name"),
                    doc.getInteger("age"),
                    doc.getInteger("salary"),
                    doc.getString("designation"),
                    doc.getString("department"));
        }
    }

    public static void appraisal() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the EID of the employee you want to appraise:");
        int eid = sc.nextInt();
        System.out.println("Enter the appraisal amount to be added to the salary:");
        int appraisalAmount = sc.nextInt();
        Bson filter = Filters.eq("eid", eid);
        Document employee = collection.find(filter).first();
        if (employee != null) 
        {
            int currentSalary = employee.getInteger("salary").intValue();
            int newSalary = currentSalary + appraisalAmount;
            Bson update = Updates.set("salary", newSalary);
            collection.updateOne(filter, update);
            System.out.println("Employee's salary updated successfully!");
            System.out.println("New Salary: " + newSalary);
        } 
        else 
        {
            System.out.println("No employee found with EID: " + eid);
        }
    }

    public static void deleteEmployee() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the EID of the employee you want to delete:");
        int eid = sc.nextInt();
        Bson filter = Filters.eq("eid", eid);
        Document employee = collection.find(filter).first();
        if (employee != null) 
        {
            collection.deleteOne(filter);
            System.out.println("Employee deleted successfully.");
        } 
        else 
        {
            System.out.println("No employee found with EID: " + eid);
        }
    }
    public static void search() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the EID of the employee you want to search:");
        int eid = sc.nextInt();
        Bson filter = Filters.eq("eid", eid);
        Document employee = collection.find(filter).first();
        if (employee != null) 
        {
            System.out.println("Employee found: " + employee.toJson());
        } 
        else 
        {
            System.out.println("No employee found with EID: " + eid);
        }
    }
    //mongoClient.close();
}

class Menu {
    public static int readChoice(int maxChoice) 
    {
        Scanner sc = new Scanner(System.in);
        int choice;
        try 
        {
            choice = sc.nextInt();
            if (choice > maxChoice || choice < 1) 
            {
                System.out.println("Please choose between 1 and " + maxChoice);
                return 0;
            }
        } 
        catch (Exception e) 
        {
            System.out.println(e);
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
            if (choice1 == 0) continue;
            if (choice1 == 6) break;
            switch (choice1)
            {
                case 1:
                    while (true) 
                    {
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
                                Employee e1 = new Employee(1);
                                Employee.insertIntoDB(e1);
                                break;
                            case 2:
                                Employee e2 = new Employee(2);
                                Employee.insertIntoDB(e2);
                                break;
                            case 3:
                                Employee e3 = new Employee(3);
                                Employee.insertIntoDB(e3);
                                break;
                            case 4:
                                Employee e4 = new Employee(4);
                                Employee.insertIntoDB(e4);
                                break;
                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                    }
                    break;
                case 2:
                    Employee.displayEmployees();
                    break;
                case 3:
                    Employee.appraisal();
                    break;
                case 4:
                    Employee.search();
                    break;
                case 5:
                    Employee.deleteEmployee();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
