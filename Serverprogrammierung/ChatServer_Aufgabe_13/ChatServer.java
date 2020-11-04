package Serverprogrammierung.ChatServer_Aufgabe_13;

import java.net.*;

public class ChatServer {

    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(4999);
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
