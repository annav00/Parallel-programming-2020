package main.java.ru.spbstu.telematics;

 import java.util.concurrent.Semaphore;
 import java.util.concurrent.Exchanger;
 import java.util.Random;
 import java.util.TreeSet;
 import java.util.SortedSet;
 import java.util.Iterator;
 import java.util.Set;
import  java.util.*;

 public class Scan implements Runnable {
     Semaphore switchSemaphor;
     Exchanger<String> exchanger;
     String message;
     SortedSet<Double> frequency = new TreeSet<>();
     double frequencyNow;

     Scan(Exchanger<String> ex, Semaphore sm){
             this.exchanger = ex;
             this.switchSemaphor = sm;
             frequencyNow = 108;
             double min = 108;
             double ptr = 108 - 88 + 1;
             Random random = new Random();
             for(int i = 0; i < 10; i++){
                     frequency.add(min + random.nextDouble() * ptr);
             }
             frequency.add(min);
     }

     public void scan(){
            try{
            System.out.println("S1: " + switchSemaphor.tryAcquire());
                switchSemaphor.acquire();
                System.out.println("S2: " + switchSemaphor.tryAcquire());
                            SortedSet<Double> subSet = frequency.headSet(frequencyNow);
                            System.out.println("subSet: " + subSet.first());
                            if(!subSet.isEmpty())
                                frequencyNow = subSet.last();
                            else
                                frequencyNow = frequency.first();
                            switchSemaphor.release();
                            System.out.println("Frequency: " + frequencyNow);
            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
     }

     public void reset(){
                try{
                   System.out.println("R1: " + switchSemaphor.tryAcquire());
                   switchSemaphor.acquire();
                   System.out.println("R2: " + switchSemaphor.tryAcquire());
                   SortedSet<Double> subSet = frequency.tailSet(frequencyNow);
                   if(!subSet.isEmpty())
                        frequencyNow = subSet.iterator().next();
                   else
                        frequencyNow = frequency.last();
                   System.out.println("Frequency: " + frequencyNow);
                   switchSemaphor.release();
                }
                catch(InterruptedException ex){
                    ex.printStackTrace();
                }
     }

     public void main(){
        try{
            message = exchanger.exchange(null);
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }

        if(message == "scan")
            scan();
        else
            reset();
     }

     @Override
     public void run(){
        while(true){
            main();
        }
     }
 }
