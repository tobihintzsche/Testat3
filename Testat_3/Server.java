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
                for(int i=0; i<5; i++) {
                    try {
                        WorkerThread workerThread = new WorkerThread(dp, serverSocket, newRingpuffer);
                        workerThread.start();
                        System.out.println("Thread" + i + "wurde gestartet");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                serverSocket.receive(dp);
                //Warteschlange der DatagramPackete
                newRingpuffer.add(dp);
                System.out.println("dp" + dp);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


