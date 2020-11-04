package Serverprogrammierung.ChatServer_Aufgabe_13;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ChatServer1 {

    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(5000);
            while(true){
                DatagramPacket dp = new DatagramPacket(new byte[65507],65507);
                serverSocket.receive(dp);
                int port = dp.getPort();
                InetAddress sender = dp.getAddress();
                byte[] data = dp.getData();
                String dataString = new String(data,0,data.length);
                System.out.println(sender.toString() + "("+ port + "): " + dataString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
