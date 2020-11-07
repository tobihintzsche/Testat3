package Testat_3;

import static java.net.InetAddress.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Client2 extends Thread {
    private static final int DEFAULT_PORT = 7777; //Server Port
    private int ID; //Client Thread id
    private String[] messages = {"READ file0,1","WRITE file1,4,zeile1234","READ file1,3","WRITE file0,0,zeile2443"};//Zum Abruf der zu sendenen Nachrichten
    private DatagramSocket client = null;

    //Konstruktor
    public Client2(int ID){
        this.ID=ID;
    }

    public void run() {
        try {
            System.out.println("Client-" + ID + " gestartet!");
            try {
                client = new DatagramSocket(); // Initialisierung eines eigenen Socktes für jeden Client Thread
                String message = messages[ID]; //Abrufen der zu sendenden Nachricht anhand der Client Thread ID
                byte[] clientBuffer = message.getBytes(); //Puffer für die jeweilige Nachricht
                DatagramPacket clientPacket = new DatagramPacket(clientBuffer, clientBuffer.length, getLocalHost(), DEFAULT_PORT);//Erstellen der Nachricht
                client.send(clientPacket);//Senden der Nachricht an den Server
                System.out.println("Client-" + ID + " hat sein Nachricht \""+ message +"\" versendet");
                byte[] serverBuffer = new byte[65507];//Puffer für die Nachricht des Servers
                DatagramPacket serverPacket = new DatagramPacket(serverBuffer, serverBuffer.length);//Vorbereitung zum Empfang der Servernachricht
                client.receive(serverPacket);//Empfangen der Servernachricht
                System.out.println("Serverantwort an Client-" + ID + ": " + new String(serverPacket.getData(), serverPacket.getOffset(), serverPacket.getLength()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
        // }


    }
    public static void main (String[]args){
        for(int i=0;i<4;i++){
            new Client2(i).start();
        }

    }
}