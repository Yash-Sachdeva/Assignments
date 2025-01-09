import java.util.*;
abstract class Employee{
        private String name;
        private int age;
        private float salary;
        private String designation;
 
        public Employee( String name,int age,float salary,String designation){
            this.salary = salary;
            if(age<21  || age>60){
                System.out.println("Invalid Age Entered");
            }
            else{
                this.age = age;
            }
            this.name = name;
            this.designation = designation;
        }
        public void display(){
            System.out.println("Name : "+name);
            System.out.println("Age : "+age);
            System.out.println("Salary : "+salary);
            System.out.println("Designation : "+designation);
        }    
        public void raiseSalary(){
            if(designation.equals("Clerk"))
                this.salary += 2000;
            else if(designation.equals("Programmer"))
                this.salary += 5000;
            else if(designation.equals("Manager"))
                this.salary += 15000;
        }
};
class Clerk extends Employee{
    public Clerk(String name,int age){
        super(name,age,20000,"Clerk");
    }
};
 
class Programmer extends Employee{
    public Programmer(String name,int age){
        super(name,age,30000,"Programmer");
    }
};
 
class Manager extends Employee{
    public Manager(String name,int age){
        super(name,age,100000,"Manager");
    }
};
 
 
 
 
public class EmployeeManagementApp{
    public static void main (String args[]){
        Scanner sc = new Scanner(System.in);
        ArrayList<Clerk> allClerk = new ArrayList<Clerk>();
        ArrayList<Programmer> allProgrammer = new ArrayList<Programmer>();
        ArrayList<Manager> allManager = new ArrayList<Manager>();
        while(true){
            System.out.println("Enter Your Choice :-");
            System.out.println("-------------");
            System.out.println("1. Create\n2. Display\n3. Raise Salary\n4. Exit");
            System.out.println("-------------");
            int choice1 = sc.nextInt();
            if(choice1==4)
                break;
            switch(choice1){
                case 1:
                {
                    int cnt = 0;
                    while(true){
                        System.out.println("Enter Designation :-");
                        System.out.println("-------------");
                        System.out.println("1. Clerk\n2. Programmer\n3. Manager\n4. Exit");
                        System.out.println("-------------");
                        int choice2 = sc.nextInt();
                        if(choice2==4)
                            break;
                        switch(choice2){
                            case 1:
                            {
                                System.out.println("Enter the Name :- ");
                                String name;
                                name = sc.next();      
                                System.out.println("Enter the Age :- ");
                                int age = sc.nextInt();
                                Clerk c = new Clerk(name,age);
                                allClerk.add(c);
                                cnt++;
                                break;
                            }
                            case 2:
                            {
                                System.out.println("Enter the Name :- ");
                                String name;
                                name = sc.next();      
                                System.out.println("Enter the Age :- ");
                                int age = sc.nextInt();
                                Programmer c = new Programmer(name,age);
                                allProgrammer.add(c);
                                cnt++;
                                break;
                            }
                            case 3:
                            {
                                System.out.println("Enter the Name :- ");
                                String name;
                                name = sc.next();      
                                System.out.println("Enter the Age :- ");
                                int age = sc.nextInt();
                                Manager c = new Manager(name,age);
                                allManager.add(c);
                                cnt++;
                                break;
                            }
                            default:
                            {
                                System.out.println("Not a Valid Command");
                                break;
                            }
                        }
                    }
                    System.out.println("Employee Created - "+cnt);
                    break;
                }
                case 2:
                {
                    System.out.println("All Clerks");
                    System.out.println("*******************************************************");
                    for(int i = 0;i<allClerk.size();i++){
                        allClerk.get(i).display();
                        System.out.println("*******************************************************");
                    }
               
                    System.out.println("All Programmer");
                    System.out.println("*******************************************************");
                    for(int i = 0;i<allProgrammer.size();i++){
                        allProgrammer.get(i).display();
                        System.out.println("*******************************************************");
                    }
 
                    System.out.println("All Manager");
                    System.out.println("*******************************************************");
                    for(int i = 0;i<allManager.size();i++){
                        allManager.get(i).display();
                        System.out.println("*******************************************************");
                    }
                    break;
                }
                case 3:
                {
                    for(int i = 0;i<allClerk.size();i++){
                        allClerk.get(i).raiseSalary();
                    }
                    for(int i = 0;i<allProgrammer.size();i++){
                        allProgrammer.get(i).raiseSalary();
                    }
                    for(int i = 0;i<allManager.size();i++){
                        allManager.get(i).raiseSalary();
                    }
                    System.out.println("Salaries are updated");
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