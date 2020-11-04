package Testat_3;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Ringpuffer {
    private static DatagramPacket[] ringpuffer;
    private static int counter = 0;
    private static int size;
    private static int nextfree=0;
    private static int nextfull=0;
    // private static Ringpuffer[] ringpuffers = new Ringpuffer[1];

    public Ringpuffer(int size){
        this.size=size;
        ringpuffer = new DatagramPacket[size];


    }

    public synchronized void add(DatagramPacket dp){
        try {
            System.out.println("Hier passiert was");
            ringpuffer[nextfree]=dp;
            System.out.println("");
            nextfree=(nextfree+1)%size;
            counter++;
            notifyAll();


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatagramPacket getDp(){
        DatagramPacket dp = ringpuffer[nextfree];
        nextfull = (nextfull+1)%size;
        counter--;
        return dp;
    }

    public static synchronized int getCounter(){
        return counter;
    }

    /*
    public static Ringpuffer getRingpuffer(){
        return Ringpuffer.ringpuffers[0];
    }

    public static void main(String[] args) {
        Ringpuffer ringpuffer = new Ringpuffer(20);
        // Ringpuffer.ringpuffers[0] = ringpuffer;
        System.out.println(ringpuffer);
    } */
}