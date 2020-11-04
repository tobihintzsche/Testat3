package Testat_3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client2 {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

            //aufgabe definieren
            while(true){
                //Befehl einlesen
                String theLine = userIn.readLine();
                if(theLine.equals(".")) break;//wenn nur ein . eingegeben wird, dann wird die while verlassen

                //datagram paket erstellen und senden
                byte[] msg = theLine.getBytes();
                int lengthOfMsg = msg.length;
                InetAddress destination = InetAddress.getByName("localhost");
                int destinationPort = 5999;
                DatagramPacket toSend = new DatagramPacket(msg,lengthOfMsg,destination,destinationPort);
                clientSocket.send(toSend);

                //Antwort empfangen
                DatagramPacket dp = new DatagramPacket(new byte[65507],65507);
                clientSocket.receive(dp);
                byte[] data = dp.getData();
                String answer = new String(data,0,data.length);
                System.out.printf("------------\nDie Antwort des Servers ist: %s \n------------\n",answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
