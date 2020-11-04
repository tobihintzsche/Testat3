package Testat_3;

import Sonstiges.MyThread1;

import java.util.concurrent.ThreadLocalRandom;

/* public class ThreadPool {
    private int size;
    //Initialisierung der Warteschlange als ThreadPool
    private ThreadPool[] warteschlange;
    private int ctr = 0;
    private int nextfree = 0;
    private int nextfull = 0;
    private static WorkerThread workerThread;

    //Konstruktor ThreadPool, indem die größe des ThreadPools, sowie der Parameter WorkerThread mitübergeben wird
    public ThreadPool(int size, WorkerThread myWorkerThread){
        this.size = size;
        //Initialisierung der größe der Warteschlange
        warteschlange = new ThreadPool[size];
        //Erstelle so viel Threads, so groß, wie der ThreadPool wird
        for(int i=0; i<size; i++) {
            //Erstelle bestimmte Anzahl an WorkerThreads
            this.workerThread = myWorkerThread;
            myWorkerThread = new WorkerThread(getTHREAD().dp, getTHREAD().socket);
        }

    }

    public WorkerThread getTHREAD(){
        return workerThread;
    }

    public synchronized void addTHREAD (ThreadPool workerThread) {
        try {
            System.out.println("Prod_arriving");

            System.out.println("Prod_active_with" + workerThread);
            warteschlange[nextfree] = workerThread;
            nextfree = (nextfree + 1) % size;
            ctr++;

            System.out.println("Prod_gone");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public synchronized ThreadPool removeTHREAD () {
        ThreadPool workerThread = null;
        try {
            System.out.println("Cons arriving");

            System.out.printf("Cons active");
            workerThread = warteschlange[nextfull];
            nextfull = (nextfull + 1) % size;
            ctr--;

            System.out.printf("Cons gone with" + workerThread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workerThread;
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(10, workerThread);
        new Thread( new )

    }

} */
