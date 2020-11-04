package Serverprogrammierung.EchoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static final int echoPort = 7;//Default port, der festgelegt ist
    public static final int dayTimePort = 7777;//Default port, der festgelegt ist

    public static void main(String[] args) {
        //Variablen deklarieren, mit null vobelegen ist besser, da man sie dann überall verwenden kann
        String hostname = "localhost";
        boolean daytimeServer = false;
        PrintWriter networkOut = null;
        BufferedReader networkIn = null;
        Socket s = null;
        try{
            //Variablen anlegen
            if(daytimeServer){
                s = new Socket(hostname, dayTimePort);
            }else{
                s = new Socket(hostname, echoPort);
            }

            System.out.println("Connected to echo server");
            networkIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //Diese beiden sind beim DaytimeServer zur Ausgabe gedacht
            if(daytimeServer){
                System.out.println(networkIn.readLine());
                s.close();
                return;
            }
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            networkOut = new PrintWriter(s.getOutputStream());

            //aufgabe definieren
            while(true){
                String theLine = userIn.readLine();
                if(theLine.equals(".")) break;//wenn nur ein . eingegeben wird, dann wird die while verlassen
                networkOut.println(theLine);
                networkOut.flush();
                System.out.println(networkIn.readLine());
            }

        }catch (IOException e){
            System.err.println(e);
        }finally {
            try{
                if(s != null)s.close();
            }catch(IOException e){}
        }
    }
}
