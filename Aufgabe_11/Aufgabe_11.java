package Aufgabe_11;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Aufgabe_11 {
    static Semaphore mutex = new Semaphore(1, true);
    static Semaphore[] fork = new Semaphore[5];

    public Aufgabe_11(){
        for(int i = 0;i<fork.length; i++){
            fork[i] = new Semaphore(1,true);
            Thread t =  new thread(i);
            t.start();
        }
    }

    public static void eat(int id){
        try{
            //Gabeln blockieren
            if(id==4){
                fork[(id+1)%5].acquire();
                fork[id].acquire();
            }else{
                fork[id].acquire();
                fork[(id+1)%5].acquire();
            }
            //Essen
            int duration = (int)(Math.random()*10000);
            System.out.println("Philosoph " + id + " isst für " + duration + "ms");
            Thread.sleep(duration);
            //Gabeln freigeben
            System.out.println("Philosoph " + id + " ist fertig");
            fork[(id+1)%5].release();
            fork[id].release();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Aufgabe_11();
    }
}
