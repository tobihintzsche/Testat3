package Aufgabe_9;

import Aufgabe_9.Aufgabe_9;

public class threads extends Thread{
    int[] numbers;
    int id;
    int partsize;
    public threads (int[] numbers, int id, int partsize){
        this.numbers=numbers;
        this.id=id;
        this.partsize=partsize;
    }

    public void run(){
        //System.out.println("Ich bin Thread "+id+" und berechne von Index "+(id*partsize)+" bis "+ (((id+1)*partsize)-1));
        long sum = 0;
        for(int i = (id*partsize); i<((id+1)*partsize);i++){
            sum += numbers[i];
        }
        Aufgabe_9.threadsums[id] = sum;
    }
}
