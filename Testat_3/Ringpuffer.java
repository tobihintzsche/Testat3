package Testat_3;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

//Implementierung eines Ringpuffers (Semaphorenkonzept aus dem Skript durch Monitore ersetzt)

public class Ringpuffer {
    private static DatagramPacket[] ringpuffer;
    private static int counter = 0;
    private static int size;
    private static int nextfree=0;
    private static int nextfull=0;
    // private static Ringpuffer[] ringpuffers = {new Ringpuffer};

    public Ringpuffer(int size){
        this.size=size;
        ringpuffer = new DatagramPacket[size];
    }

    public synchronized void add(DatagramPacket dp){ //Die synchrosierende Methode wird verwendet um ein Objket für eine gemeinsam genutzte Ressource zu sperren
        while (getCounter()>=size) { //Mit try wird der Block markiert, der eine mögliche Exception auslösen kann
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
                System.out.println("Hier passiert was");
                ringpuffer[nextfree] = dp;
                System.out.println(dp);
                nextfree = (nextfree + 1) % size;
                counter++;
                notifyAll(); //notifyAll weckt alle Threads auf, die auf dem Monitor diesen Objekts warten

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized DatagramPacket getDp(){ // Methode zum auslesen des DatagramPacket
        while (getCounter()==0) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DatagramPacket dp = ringpuffer[nextfull];
        nextfull = (nextfull+1)%size;
        counter--;
        notifyAll();
        return dp;

    }

    public static int getCounter(){
        return counter;
    }


}
