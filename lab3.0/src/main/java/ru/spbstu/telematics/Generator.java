package main.java.ru.spbstu.telematics;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.lang.Thread;

public class Generator implements Runnable {
    String [] commands = new String[] {"on", "off", "scan", "reset"};
    Random random = new Random();
    Exchanger<String> exchangerO;
    Exchanger<String> exchangerSR;
    String message;

    Generator(Exchanger<String> exO, Exchanger<String> exSR){
            this.exchangerO = exO;
            this.exchangerSR = exSR;
    }

     public void getMess(Exchanger<String> exch, String mess){
            System.out.println("Command: " + mess);
            try{
                exch.exchange(mess);
            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
     }

     @Override
     public void run(){
          while(true){
             for(int i = 0; i  >= 0; i++)
             {
                System.out.println("Number: " + i);
                int rand = random.nextInt(commands.length);
                if(rand == 0 || rand == 1)
                    getMess(exchangerO, commands[rand]);
                else
                    getMess(exchangerSR, commands[rand]);

                try{
                    Thread.sleep(2000);
                }
                catch(InterruptedException ex){
                    ex.printStackTrace();
                }
         }
         //break;
     }
    }
}
