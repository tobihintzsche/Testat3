package Aufgabe_9;

import java.util.ArrayList;
import java.util.Arrays;

public class Aufgabe_9 extends Thread{
    int[] numbers = new int[134217728];
    static long [] threadsums = new long[64];

    public static void main(String[] args){
        new Aufgabe_9();
    }

    public Aufgabe_9(){
        for(int i=0; i<numbers.length;i++){
            numbers[i] = (int)(Math.random()*1000000);
        }
        normalSum();
        createThreads(2);
        createThreads(4);
        createThreads(8);
        createThreads(16);
        createThreads(32);
        createThreads(64);
    }

    public void normalSum(){
        long sum = 0;
        long timestart = System.currentTimeMillis();
        for (int i:numbers){
            sum += i;
        }
        long timenow = System.currentTimeMillis();
        System.out.println("Die Berechnung hat " + (timenow-timestart) + "ms gedauert, die Summe ist: " + sum);
    }

    public void createThreads(int count){
        int part = numbers.length/count;
        long timestart = System.currentTimeMillis();
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(int i = 0;i<count; i++){
            Thread t =  new threads(numbers, i, part);
            t.start();
            threads.add(t);
        }
        for(Thread t:threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long sum = 0;
        for(long i:threadsums){
            sum += i;
        }
        long timenow = System.currentTimeMillis();
        System.out.println("Die Berechnung mit "+count+" Workerthreads hat " + (timenow-timestart) + "ms gedauert, die Summe ist: " + sum);
        //Array wieder leeren
        Arrays.fill(threadsums, 0);
    }
}
