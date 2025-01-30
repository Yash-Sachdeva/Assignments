import java.util.*;
import java.util.regex.*;
 
abstract class Employee{
        static int count = 0;
        static HashMap<Integer,Employee> emp = new HashMap<>();
        private int eid;
        private String name;
        private int age;
        private float salary;
        private String designation;
        public Employee(float salary,String designation){
            this.salary = salary;
            this.eid = ReadEmployeeDetails.readId();
            this.name = ReadEmployeeDetails.readName();
            this.age = ReadEmployeeDetails.readAge(21,60);
            this.designation = designation;
        }
        public void display(){
            System.out.println("Name - "+name);
            System.out.println("Employee ID - "+eid);
            System.out.println("Age - "+age);
            System.out.println("Salary - $"+salary);
            System.out.println("Designation - "+designation);
        }    
        public int getEid(){
            return eid;
        }
        public float getSalary(){
            return this.salary;
        }
        protected void setSalary(float salary){
            this.salary = salary;
        }
        public static void storeEmployee(Employee e){
            emp.put(e.getEid(),e);
        }
        public static void removeEmployee(int id){
            Scanner sc  = new Scanner(System.in);
            if(searchEmployee(id)){
                System.out.println("Are u sure you want to delete? ");
                String c = sc.next();
                if(c.equals("y"))
                    emp.remove(id);
            }
        }
        public static void showAll(){
            for(int id:emp.keySet()){
                emp.get(id).display();
                System.out.println();
            }
        }
        public static boolean searchEmployee(int id){
            if(!checkEid(id)){
                System.out.println("This Id is not present");
                return false;
            }
            Employee tmp = emp.get(id);
            tmp.display();
            return true;
        }
        public static void raiseAll(){
            for(int id:emp.keySet()){
                emp.get(id).raiseSalary();
            }
        }
        public static boolean checkEid(int id){
            return emp.containsKey(id);
        }
        abstract void raiseSalary();
};
final class Clerk extends Employee{
    private Clerk(){
        super(10000,"Clerk");
    }
    public static Clerk getObject(){
        if(CEO.checkCEO()==null){
            System.out.println("First Create a CEO object");
            return null;
        }
        return new Clerk();
    }
    void raiseSalary(){
        setSalary(getSalary()+2000);
    }
};
 
final class Programmer extends Employee{
    private Programmer(){
        super(30000,"Programmer");
    }
    public static Programmer getObject(){
        if(CEO.checkCEO()==null){
            System.out.println("First Create a CEO object");
            return null;
        }
        return new Programmer();
    }
    void raiseSalary(){
        setSalary(getSalary()+5000);
    }
};
 
final class Manager extends Employee{
    private Manager(){
        super(100000,"Manager");
    }
    public static Manager getObject(){
        if(CEO.checkCEO()==null){
            System.out.println("First Create a CEO object");
            return null;
        }
        return new Manager();
    }
    void raiseSalary(){
        setSalary(getSalary()+10000);
    }
};
final class CEO extends Employee{
    private static CEO c1 = null;
    private CEO(){
        super(200000,"CEO");
    }
    public static CEO getObject(){
        if(c1 == null)
            c1 = new CEO();
        return c1;
    }
    public static CEO checkCEO(){
        return c1;
    }
    void raiseSalary(){
        setSalary(getSalary()+50000);
    }
}
class AgeException extends RuntimeException{
    public AgeException(){
        super();
    }
    public AgeException(String msg){
        super(msg);
    }
};
class NameException extends RuntimeException{
    public NameException(){
        super();
    }
    public NameException(String msg){
        super(msg);
    }
}
class Menu{ 
    public static int readChoice(int maxChoice){
        Scanner sc = new Scanner(System.in);
        int choice;
        try{
            choice = sc.nextInt();
            if(choice > maxChoice){
                System.out.println("Please chose between 1 and "+maxChoice);
                return 0;
            }
        }
        catch(InputMismatchException e)
        {
            e.printStackTrace();
            return 0;
        }
        return choice;
       
    }
}
class ReadEmployeeDetails{
    public static String readName(){
        Scanner sc = new Scanner(System.in);
        String name = "";
        while(true){
            System.out.println("Enter the Name : ");
            try{
                name = sc.nextLine();
                if(!name.matches("[A-Z][A-Za-z]*[ ][A-Z][A-Za-z]*"))
                    throw new NameException("Name is not in correct format");
            }
            catch(NameException e){
                System.out.println(e.getMessage());
                continue;
            }
            return name;
        }
    }
    public static int readId(){
        Scanner sc = new Scanner(System.in);
        int tempId;
        while(true){
            System.out.print("Enter the ID : ");
            try{
                tempId = sc.nextInt();
            }
            catch(InputMismatchException e){
                e.printStackTrace();
                sc.nextLine();
                continue;
            }
            if(Employee.checkEid(tempId)){
                System.out.println("!!!!!Id is ALready Present!!!!!");
                continue;
            }
            break;
        }
        return tempId;
       
    }
    public static int readAge(int l,int r){
        Scanner sc = new Scanner(System.in);
        int age = 0;
        while(true){
            System.out.println("Enter the Age : ");
            try{
                age = sc.nextInt();
                if(age<l||age>r){
                    throw new AgeException("The age is not in the given range");
                }
            }
            catch(InputMismatchException e){
                e.printStackTrace();
                sc.nextLine();
                continue;
            }
           
            catch(AgeException e){
                System.out.println(e.getMessage());
                sc.nextLine();
                continue;
            }
             break;
        }
        return age;
    }
}
 
public class EmpManagementApp{
    public static void main (String args[]){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Enter Choice :-");
            System.out.println("-------------");
            System.out.println("1. Create\n2. Display\n3. Raise Salary\n4. Remove\n5. Exit");
            System.out.println("-------------");
            int choice1 = Menu.readChoice(6);
            if(choice1 == 0)
                continue;
            if(choice1==6)
                break;
            switch(choice1){
                case 1:
                {
                    int cnt = 0;
                    while(true){
                        int choice2;
                        System.out.println("Enter Choice :-");
                        System.out.println("-------------");
                        System.out.println("1. Clerk\n2. Programmer\n3. Manager\n4. CEO \n5. Search\n6. Exit");
                        System.out.println("-------------");
                        choice2 = Menu.readChoice(5);
                        if(choice2==5)
                            break;
                        switch(choice2){
                            case 1:
                            {
                                Clerk c = Clerk.getObject();
                                if(c!=null){
                                    Employee.storeEmployee(c);
                                }
                                break;
                            }
                            case 2:
                            {
                                Programmer p = Programmer.getObject();
                                if(p!=null){
                                    Employee.storeEmployee(p);
                                }
                                break;
                            }
                            case 3:
                            {
                                Manager m = Manager.getObject();
                                if(m!=null){
                                    Employee.storeEmployee(m);
                                }
                                break;
                            }
                            case 4:
                            {
                                if(CEO.checkCEO()!=null){
                                    System.out.println("Can't create 2 CEO");
                                    break;
                                }
                                CEO c1 = CEO.getObject();
                                Employee.storeEmployee(c1);
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
                    Employee.showAll();  
                    break;
                }
                case 3:
                {
                    Employee.raiseAll();
                    break;
                }
                case 4:
                {
                    System.out.println("Enter the EID to remove");
                    int id = sc.nextInt();
                    Employee.removeEmployee(id);
                   
                    break;
                }
                case 5:
                {
                    System.out.println("Enter the EID to search");
                    int id = sc.nextInt();
                    Employee.searchEmployee(id);
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
