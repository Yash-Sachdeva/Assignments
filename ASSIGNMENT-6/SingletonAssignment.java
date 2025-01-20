class A {
    private static A a1=new A();
    private boolean a=false;
    A()
    {
        if( this instanceof B && this instanceof A)
        {
            System.out.println("B instance created...");
        }
        else if (this instanceof A && a1==null)
        {
            a1=this;
            System.out.println("A instance created...");
        }
        else{
            throw new NullPointerException();
        }
    }
    public static A getObject()
    {
        if(a1==null)
            a1=new A();
        return a1;
    }
}
class B extends A{
    private static final B b1=new B();
    private B()
    {
        
    }
    public static B getObject()
    {
        return b1; 
    }
}
public class SingletonAssignment{
    public static void main(String args[])
    {
        A a4=new A();
        A a5=new A();
        A a1=A.getObject();
        B b1=B.getObject();
        System.out.println(a1);
        System.out.println(b1);
    }


}