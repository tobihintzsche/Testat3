package Testat_1;

import java.util.concurrent.Semaphore;

public class Erzeuger_Verbraucher {
    //Farben für die Konsolenausgabe der beiden Loks
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String reset = "\u001B[0m";

    //Variable zum Setzen der automatischen Geschwindigkeitsanpassungen
    public static boolean automaticSpeedChanges = false;

    //Geschwindigkeitsvariablen
    public static int speed_lok0 = 1;
    public static int speed_lok1 = 1;

    //Semaphore für Ausschluss und Erzeuger/Verbraucher
    public static Semaphore mutex = new Semaphore(1,true);
    public static Semaphore lok0_sem = new Semaphore(1,true);
    public static Semaphore lok1_sem = new Semaphore(0,true);

    static Runnable zug0 = () -> {
        while(true){
            try{
                enterLok0();
                System.out.println(red + "Lok 0 ist im k.A." + reset);
                Thread.sleep(2000);
                exitLok0();
                Thread.sleep((int)(5000/speed_lok0));
            }catch(Exception e){e.printStackTrace();}
        }
    };
    static Runnable zug1 = () -> {
        while(true){
            try{
                enterLok1();
                System.out.println(green + "Lok 1 ist im k.A." + reset);
                Thread.sleep(2000);
                exitLok1();
                Thread.sleep((int)(5000/speed_lok1));
            }catch(Exception e){e.printStackTrace();}
        }
    };

    //Eintrittsprotokolle
    static void enterLok0(){
        System.out.println(red + "Lok 0 möchte in den k.A" + reset);
        try{
            lok0_sem.acquire();
            mutex.acquire();
        }catch(Exception e){e.printStackTrace();}
    }

    static void enterLok1(){
        System.out.println(green + "Lok 1 möchte in den k.A" + reset);
        try{
            lok1_sem.acquire();
            mutex.acquire();
        }catch(Exception e){e.printStackTrace();}
    }

    //Austrittsprotokolle
    static void exitLok0(){
        System.out.println(red + "Lok 0 verlässt den k.A" + reset);
        try{
            mutex.release();
            lok1_sem.release();
        }catch(Exception e){e.printStackTrace();}
    }

    static void exitLok1(){
        System.out.println(green + "Lok 1 verlässt den k.A" + reset);
        try{
            mutex.release();
            lok0_sem.release();
        }catch(Exception e){e.printStackTrace();}
    }

    public static void main(String[] args) {
        System.out.printf("----------\nGeschwindigkeit gesetzt:\nLok0: %d\nLok1: %d\n----------\n",speed_lok0,speed_lok1);
        //Threads starten
        Thread lok0 = new Thread(zug0);
        Thread lok1 = new Thread(zug1);
        lok1.start();
        lok0.start();

        //Zufällige Geschwindigkeitsanpassungen
        while(automaticSpeedChanges){
            try{
                Thread.sleep((int)(5000+Math.random()*5000));
            }catch (Exception e){e.printStackTrace();}
            speed_lok0 = (int)(1+Math.random()*5);
            speed_lok1 = (int)(1+Math.random()*5);
            System.out.printf("----------\nGeschwindigkeit angepasst, neue Geschwindigkeiten:\nLok0: %d\nLok1: %d\n----------\n",speed_lok0,speed_lok1);
        }
    }
}
