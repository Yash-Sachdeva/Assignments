import java.util.*;
 
class Bikers extends Thread
{
	String name;
	long startTime;
	long endTime;
	long timeTaken;
    static final Object lock=new Object();  
	Bikers()
	{
		System.out.print("Enter biker name : ");
		this.name = new Scanner(System.in).next();
		new Thread(this).start();
	}
	public void run()
	{
		System.out.println(name+" is ready to race.....");
		synchronized(lock)
		{
            try{
                lock.wait();
            }
			catch(InterruptedException e)
            {
                System.out.println(e);
            }
		}
		startTime = System.currentTimeMillis();	
		System.out.print("\nRacing\n");
		for(int i=1; i<RacingDetails.distance; i++)
		{
            try{
			Thread.sleep((int)Math.random()*100);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
			if(i%100==0)
				System.out.print(".");
		}
		endTime = System.currentTimeMillis();
		timeTaken = endTime - startTime;
	}	
	public String toString()
	{
		return "\nName="+name+":Start time=" +startTime+ ":end time="+endTime+ ":Total time="+timeTaken+"\n";
	}
}
class RacingDetails
{
	static int noOfBikers=3;
	static int distance=100;
	static String unit = "Meters";
}
 
public class Solution_Bike_Racer
{
	public static void main(String args[])
	{
		Bikers bikers[] = new Bikers[RacingDetails.noOfBikers]; 
		RacingDetails.distance = 1000;
		for(int i=0; i<bikers.length; i++)		
		{
			bikers[i] = new Bikers();   
		}
		try{
			Thread.sleep(2000);
		}
		catch(InterruptedException e)
		{
			System.out.println(e);
		}
		System.out.println("\nCount Down Started....\n");
		for(int i=10; i>0; i--)
		{
			System.out.println(i);
			try{
			Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				System.out.println(e);
			}
		}
		System.out.println("GO");
		synchronized(Bikers.lock)
		{
			Bikers.lock.notifyAll();
		}
		for(Bikers b : bikers)
		{
			try{
			b.join();
			}
			catch(InterruptedException e)
			{
				System.out.println(e);
			}
		}
		Arrays.sort(bikers, Comparator.comparingLong(b -> b.timeTaken));
		for(int i=0; i<bikers.length; i++)
		{
			System.out.print((i+1)+":"+bikers[i]);
		}
	}
}