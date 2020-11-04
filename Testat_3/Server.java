package Testat_3;


import Aufgabe_12.Monitor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

    public static WorkerThread workerThread;
    // public static Ringpuffer ringpuffer;

    // Ringpuffer ringpuffer = new Ringpuffer(5);


    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        Ringpuffer newRingpuffer = new Ringpuffer(5);
        System.out.println(newRingpuffer);
        try {
            //Server öffnen
            serverSocket = new DatagramSocket(7777);
            while(true){
                //DatagramPacket empfangen
                byte[] msgToRecieve = new byte[65507];
                DatagramPacket dp = new DatagramPacket(msgToRecieve, msgToRecieve.length);
                for(int i=1; i<2; i++) {
                    try {
                        DatagramSocket threadSocket = new DatagramSocket(8000 + i);
                        WorkerThread workerThread = new WorkerThread(dp, threadSocket, newRingpuffer);
                        workerThread.start();
                        System.out.println("Thread" + i + "wurde gestartet");
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }

                }
                serverSocket.receive(dp);
                System.out.println("dp" + dp);
                //Warteschlange der DatagramPackete
                newRingpuffer.add(dp);
                System.out.println("dp" + dp);







            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


