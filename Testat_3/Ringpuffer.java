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
    // private static Ringpuffer[] ringpuffers = {new Ringpuffer};

    public Ringpuffer(int size){
        this.size=size;
        ringpuffer = new DatagramPacket[size];
    }

    public synchronized void add(DatagramPacket dp){
        while (getCounter()>=size) {
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
                notifyAll();

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized DatagramPacket getDp(){
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
