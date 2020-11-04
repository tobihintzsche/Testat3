package Testat_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FileClient {
    //Farben für die Konsolenausgabe der Fehler
    public static final String red = "\u001B[31m";
    public static final String reset = "\u001B[0m";

    //Konstanten für Serververbindung
    private static final int SERVER_PORT = 7777;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        //Variablen mit null vordeklarieren
        PrintWriter networkOut = null;
        BufferedReader networkIn = null;
        BufferedReader userIn = null;
        Socket s = null;
        System.out.println("Enter one of the following commands follwed by the argument.");
        System.out.println("SAVE followed by the text you want to save e.g. \"SAVE how are you?\"");
        System.out.println("GET followed by the key you received after saving a file e.g. \"GET abcde\"");
        System.out.println("----------");
        try{
            userIn = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                //Eingabe einlesen und Programm bei exit beenden
                String theLine = userIn.readLine();
                if(theLine.equals("exit")){
                    break;
                }
                //Verbindung aufbauen
                s = new Socket(HOSTNAME, SERVER_PORT);
                System.out.println("Connected via Port " + s.getLocalPort());
                //Nachricht senden und Antwort empfangen
                networkOut = new PrintWriter(s.getOutputStream());
                networkOut.println(theLine);
                networkOut.flush();
                networkIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String answer = networkIn.readLine();
                if(answer.startsWith("FAILED")){
                    System.out.print(red);
                }
                System.out.print(answer + reset + "\n");

                //Verbindung schließen
                s.close();
                System.out.println("----------");
            }
        }catch (IOException e){
            System.err.println(e.toString());
        }finally {
            try{
                if(s != null) s.close();
                if(networkIn != null) networkIn.close();
                if(userIn != null) userIn.close();
                if(networkOut != null) networkOut.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
