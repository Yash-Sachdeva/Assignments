class Employee{
	private String name,designation;
	private int  age,salary;
	public Employee(String name,int age,int salary,String designation)
	{
		this.salary=salary;
		this.age=age;
		this.name=name;
		this.designation=designation;
	}
	public void display()
	{
		System.out.println("Name:"+name);
		System.out.println("Age:"+age);
		System.out.println("Designation:"+designation);
		System.out.println("Salary:"+salary);
	}
	public void raise_sal(int raise)
	{
		if(this.designation.equals("Manager"))
		{
			salary+=raise;
			System.out.println("Updated salary:"+salary);
		}
		else
		{
			System.out.println("No Access to update");
		}
	}
	
};
class Clerk extends Employee{
	public Clerk(String name,int age,int salary)
	{
		super(name,age,salary,"Clerk");
	}

};
class Programmer extends Employee{
	public Programmer(String name,int age,int salary)
	{
		super(name,age,salary,"Programmer");
	}

};

class Manager extends Employee{
	public Manager(String name,int age,int salary)
	{
		super(name,age,salary,"Manager");
	}
	
};
class Main
{
	public static void main(String[] args)
	{
		Clerk c=new Clerk("Yash",19,100);
		c.display();
		c.raise_sal(10);
		Manager m=new Manager("Akash",20,1200);
		m.display();
		m.raise_sal(200);
	}
};
