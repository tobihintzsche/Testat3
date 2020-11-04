package Serverprogrammierung.EchoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DaytimeServer {
    public static final int serverPort = 7777;

    public static void main(String[] args) {
        int port = serverPort;
        PrintWriter out = null;
        Socket connection = null;
        try{
            ServerSocket server = new ServerSocket(port);
            while(true){
                try{
                    connection = server.accept();
                    out = new PrintWriter(connection.getOutputStream());
                    Date now = new Date();
                    out.println(now.toString());
                    out.flush();
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    try{
                        if(connection!=null){connection.close();}
                    }catch(IOException e){}
                }

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
