package Serverprogrammierung.ChatServer_Aufgabe_13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ChatClient1 {

    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

            //aufgabe definieren
            while(true){
                String theLine = userIn.readLine();
                if(theLine.equals(".")) break;//wenn nur ein . eingegeben wird, dann wird die while verlassen
                //datagram packet erstellen
                byte[] text = theLine.getBytes();
                int lengthOfText = text.length;
                InetAddress destination = InetAddress.getByName("localhost");
                int port = 4999;
                DatagramPacket toSend = new DatagramPacket(text,lengthOfText,destination,port);
                //dp senden
                clientSocket.send(toSend);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
