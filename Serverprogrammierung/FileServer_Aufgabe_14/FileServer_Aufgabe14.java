package Serverprogrammierung.FileServer_Aufgabe_14;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class FileServer_Aufgabe14 {

    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        try {
            //Server öffnen
            serverSocket = new DatagramSocket(5999);
            while(true){
                //DatagramPacket empfangen
                DatagramPacket dp = new DatagramPacket(new byte[65507],65507);
                serverSocket.receive(dp);

                //Aufgabe an einen neu erstellten WorkerThread abgeben
                new WorkerThread(dp,serverSocket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
