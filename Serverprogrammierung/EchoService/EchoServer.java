package Serverprogrammierung.EchoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static final int serverPort = 7;

    public static void main(String[] args) {
        int port = serverPort;
        PrintWriter out = null;
        Socket connection = null;
        try{
            ServerSocket server = new ServerSocket(port);
            connection = server.accept();
            BufferedReader userIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            out = new PrintWriter(connection.getOutputStream());
            while(true){
                String line = userIn.readLine();
                out.println(line);
                out.flush();
            }
        }catch(IOException e){
            System.err.println(e);
        }finally{
            try {
                if(connection != null) connection.close();
            }catch(IOException e){}
        }
    }
}
