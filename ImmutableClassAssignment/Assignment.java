// For a class to be immutable 
// 1. The class should be final 
// 2. Data Members should be final and private
// 3. A constructor initializes all the data members
// 4. There are no setter and getter methods

final class Car{
    final private String modelName;
    final private long chassisNumber;
    final private int mileage;
    Car(String modelName,long chassisNumber,int mileage)
    {
        this.modelName=modelName;
        this.chassisNumber=chassisNumber;
        this.mileage=mileage;
    }
    String getModelName()
    {
        return modelName;
    }
    long getChassisNumber()
    {
        return chassisNumber;
    }
    int getMileage()
    {
        return mileage;
    }
    public String toString()
    {
        return "Model Name is "+modelName+" having Chassis Number as "+chassisNumber+" with a mileage of "+mileage+"km/L.";
    }

}
public class Assignment{
    public static void main(String args[])
    {
        Car c=new Car("Vitara Brezza",23675899,23);
        System.out.println(c);
    }
}