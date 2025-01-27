import java.lang.reflect.*;
import java.util.*;
class Calculator
{
	public int add(int a,int b)
	{
		return a+b;
	}
	public int sub(int a,int b)
	{
		return a-b;
	}
	public int mul(int a,int b)
	{
		return a*b;
	}
	public int div(int a,int b)
	{
		return a/b;
	}
}
public class CalculatorApp
{
    public static void main(String args[])
    {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator=new Calculator();
        System.out.print("Enter operation:");
        String operation = scanner.next();
        System.out.print("Enter a:");
        int a = scanner.nextInt();
        System.out.print("Enter b:");
        int b = scanner.nextInt();
        try 
        {
            Class c=Calculator.class;
            //System.out.println(c);
            Method method = c.getMethod(operation,int.class,int.class);
            int res = (int)method.invoke(calculator,a,b);
            System.out.println("Result="+res);
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
}