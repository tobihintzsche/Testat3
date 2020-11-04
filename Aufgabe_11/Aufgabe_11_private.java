package Aufgabe_11;

import java.util.concurrent.Semaphore;

public class Aufgabe_11_private {
    static int[] state = new int[5];//0=denkend, 1=hungrig, 2=essend
    static Semaphore[] privSem = new Semaphore[5];
    static Semaphore mutex = new Semaphore(1,true);

    public Aufgabe_11_private(){
        for(int i=0;i<5;i++){
            privSem[i] = new Semaphore(0,true);
            state[i] = 0;
            Thread t =  new thread_private(i);
            t.start();
        }
    }

    public static void eat(int id){
        try{
            //Berechnung der Nachbarn
            int leftNeighbor = (id-1)%5;
            if(leftNeighbor<0) leftNeighbor = 4;

            int leftOfLeft = (leftNeighbor-1)%5;
            if(leftOfLeft <0 ) leftOfLeft = 4;

            int rightNeighbor = (id+1)%5;

            int rightOfRight = (rightNeighbor+1)%5;

            //Eintrittsprotokoll
            mutex.acquire();
            if(state[leftNeighbor]<2 && state[rightNeighbor]<2){
                state[id]=2;
                privSem[id].release();
            }else{
                state[id]=1;
            }
            mutex.release();
            privSem[id].acquire();

            //Essen
            int duration = (int) (Math.random()*10000);
            System.out.println("Philosoph " + id + " isst für " + duration + "ms");
            Thread.sleep(duration);

            //Austrittsprotokoll
            mutex.acquire();
            System.out.println("Philosoph " + id + " ist fertig.");
            state[id] = 0;
            if(state[leftNeighbor] == 1 && state[leftOfLeft] != 2){
                state[leftNeighbor]=2;
                privSem[leftNeighbor].release();
            }
            if(state[rightNeighbor] == 1 && state[rightOfRight] != 2){
                state[rightNeighbor]=2;
                privSem[rightNeighbor].release();
            }
            mutex.release();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Aufgabe_11_private();
    }
}
