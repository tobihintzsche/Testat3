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
        DatagramSocket serverSocket = null; // serverSocket vom Objekttyp DatagramSocket wird erstellt
        Ringpuffer newRingpuffer = new Ringpuffer(5); //Ringpuffer mit einer größe 5 wird erstellt
        System.out.println(newRingpuffer); //gibt den neuen Ringpuffer aus
        try {
            //Server öffnen
            serverSocket = new DatagramSocket(7777); //DatagramSocket auf dem Port 7777 wird erzeugt, ein DatagramSocket bietet eine Möglichkeit Pakete zu senden und zu empfangen
            while(true){ //solange die Bedingung wahr ist, wird die Schleife durchlaufen
                //DatagramPacket empfangen
                byte[] msgToRecieve = new byte[65507];
                DatagramPacket dp = new DatagramPacket(msgToRecieve, msgToRecieve.length); //Neues DatagramPacket wird erstellt, die zu erhaltende Nachricht und die länge der Nachricht wird üergeben
                for(int i=0; i<5; i++) {
                    try {
                        WorkerThread workerThread = new WorkerThread(dp, serverSocket, newRingpuffer);
                        workerThread.start(); //Thread starten
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                serverSocket.receive(dp); //serverSocket erhält das DatagramPackage
                //Warteschlange der DatagramPackete
                newRingpuffer.add(dp);  // Das DatagramPackage wird in den Ringpuffer gegeben
                System.out.println("dp" + dp);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


