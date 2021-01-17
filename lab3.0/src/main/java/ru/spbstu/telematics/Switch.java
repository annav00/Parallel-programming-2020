package main.java.ru.spbstu.telematics;

import java.util.concurrent.Semaphore;
import java.util.concurrent.Exchanger;

public class Switch implements Runnable {
    Semaphore switchSemaphor;
    Exchanger<String> exchanger;
    String message;

    Switch(Exchanger<String> ex, Semaphore sm){
            this.exchanger = ex;
            this.switchSemaphor = sm;

            try{
            switchSemaphor.acquire();
            switchSemaphor.release();
            }
            catch(InterruptedException exc){
                        exc.printStackTrace();
                    }
    }

    public void onOff (){
        try{
            message = exchanger.exchange(null);
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }

        try{
        System.out.println("OnOff: " + switchSemaphor.tryAcquire());
            if(message == "on"){
            System.out.println("On0: " + switchSemaphor.tryAcquire());
                if(!switchSemaphor.tryAcquire()){
                System.out.println("On1: " + switchSemaphor.tryAcquire());
                    switchSemaphor.release();
                    System.out.println("On2: " + switchSemaphor.tryAcquire());
                }
            }
            else{
            System.out.println("Off2: " + switchSemaphor.tryAcquire());
                if(switchSemaphor.tryAcquire()){
                System.out.println("Off1: " + switchSemaphor.tryAcquire());
                    switchSemaphor.acquire();
                    System.out.println("Off2: " + switchSemaphor.tryAcquire());
                }
            }
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }

        System.out.println("Сondition: " + message);
        System.out.println("OnOffEnd: " + switchSemaphor.tryAcquire());
    }

     @Override
     public void run(){
          while(true){
               onOff();
          }
     }
}
