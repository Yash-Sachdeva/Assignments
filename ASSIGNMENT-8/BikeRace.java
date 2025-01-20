import java.util.*;

class Biker 
{
    public static int totalDistance = 100;
    private int speed = 0;
    public int time = 0;
    private int dist = 0;
    public int rank = 0;
    Random rnd = new Random();

    void accelerate() 
    {
        int inc = rnd.nextInt(5) + 1;
        speed += inc;
        System.out.println(Thread.currentThread().getName() + " has accelerated by " + inc);
    }
    public void race() 
    {
        while (dist < totalDistance) 
        {
            try 
            {
                accelerate();
                time += 1;
                dist += speed;
                Thread.sleep(rnd.nextInt(1000));
            } 
            catch (Exception e) 
            {
                System.out.println(e);
            }
        }
    }
    
    public int getTime() 
    {
        return time;
    }
}

public class BikeRace 
{
    public static void main(String[] args) 
    {
        Biker bikers[] = new Biker[5];
        Thread threads[] = new Thread[5];
        for (int i = 0; i < 5; i++) 
        {
            bikers[i] = new Biker();
            final int idx = i;
            threads[i] = new Thread(new Runnable() 
            {
                public void run() 
                {
                    bikers[idx].race();
                    System.out.println("Biker " + idx + " finished the race in time: " + bikers[idx].getTime());
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < 5; i++) 
        {
            try{
                threads[i].join();
            }
            catch(InterruptedException e)
            {
                System.out.println(e);
            }
        }
    }
}
