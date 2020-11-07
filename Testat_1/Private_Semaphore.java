package Testat_1;

import java.util.concurrent.Semaphore;

public class Private_Semaphore {
    //Farben für die Konsolenausgabe der beiden Loks
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String reset = "\u001B[0m";

    //Variable zum Setzen der automatischen Geschwindigkeitsanpassungen
    public static boolean automaticSpeedChanges = false;

    //Geschwindigkeitsvariablen
    public static int speed_lok0 = 1;
    public static int speed_lok1 = 1;

    //Variable zum Abwechseln, sagt aus, welcher Zug einfahren darf
    public static int whoIsAllowed = 0;
    //Gibt an, ob der kritische Abschnitt frei ist
    public static boolean isFree = true;
    //Semaphore zum gegenseitigen Ausschluss und private Semaphore
    public static Semaphore mutex = new Semaphore(1,true);
    private static Semaphore[] privSem = new Semaphore[2];
    //Array um warten anzumelden
    private static boolean[] waiting = new boolean[2];

    //Beide run Methoden
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
                Thread.sleep((int)5000/speed_lok1);
            }catch(Exception e){e.printStackTrace();}
        }
    };

    //Eintrittsprotokolle
    static void enterLok0(){
        try{
            mutex.acquire(); //von 1 auf 0
            System.out.println(red + "Lok 0 möchte in den k.A." + reset);
            if(isFree && whoIsAllowed == 0){
                System.out.println(red + "Lok 0 bekommt den k.A." + reset);
                isFree = false;
                whoIsAllowed = 1;
                privSem[0].release();
            }else{
                System.out.println(red + "Lok 0 muss warten" + reset);
                waiting[0]=true;
            }
            mutex.release(); //von 0 auf 1
            privSem[0].acquire(); //von 1 auf 0
        }catch(Exception e){e.printStackTrace();}
    }

    static void enterLok1(){
        try{
            mutex.acquire();
            System.out.println(green + "Lok 1 möchte in den k.A." + reset);
            if(isFree && whoIsAllowed == 1){
                System.out.println(green + "Lok 1 bekommt den k.A." + reset);
                isFree = false;
                whoIsAllowed = 0;
                privSem[1].release();
            }else{
                System.out.println(green + "Lok 1 muss warten" + reset);
                waiting[1]=true;
            }
            mutex.release();
            privSem[1].acquire();
        }catch(Exception e){e.printStackTrace();}
    }

    //Austrittsprotokolle
    static void exitLok0(){
        try{
            mutex.acquire(); //von 1 auf 0
            System.out.println(red + "Lok 0 verlässt den k.A." + reset);
            isFree = true;
            if(waiting[1]){
                waiting[1] = false;
                isFree = false;
                whoIsAllowed = 0;
                privSem[1].release();
            }
            mutex.release();
        }catch(Exception e){e.printStackTrace();}
    }

    static void exitLok1(){
        try{
            mutex.acquire();
            System.out.println(green + "Lok 1 verlässt den k.A." + reset);
            isFree = true;
            if(waiting[0]){
                waiting[0] = false;
                isFree = false;
                whoIsAllowed = 1;
                privSem[0].release();
            }
            mutex.release();
        }catch(Exception e){e.printStackTrace();}
    }

    public static void main(String[] args) {
        System.out.printf("----------\nGeschwindigkeit gesetzt:\nLok0: %d\nLok1: %d\n----------\n",speed_lok0,speed_lok1);
        //Private Semaphore befüllen
        for(int i=0;i<2; i++){
            privSem[i] = new Semaphore(0,true);
        }

        //Threads starten
        Thread lok0 = new Thread(zug0);
        Thread lok1 = new Thread(zug1);
        lok1.start();
        lok0.start();

        //Zufällige Geschwindigkeitsanpssungen
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
