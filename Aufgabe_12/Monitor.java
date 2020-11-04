package Aufgabe_12;

import java.util.Arrays;

public class Monitor{
    private boolean[] eating;
    private int max;

    public Monitor(int max){
        this.max=max;
        eating = new boolean[max];
        Arrays.fill(eating,false);
    }

    public synchronized void startEating(int i){
        while (eating[(i+max-1) % max] || eating[(i+1)%max]){
            System.out.println("Philosoph " + i + " wartet");
            try {
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        eating[i]=true;
        System.out.println("Philosoph " + i + " isst");
    }

    public synchronized void stopEating(int i){
        eating[i] = false;
        System.out.println("Philosph " + i + " denkt");
        notifyAll();
    }
}