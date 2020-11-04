package Testat_3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class WorkerThread extends Thread{
    DatagramSocket socket;
    DatagramPacket dp;
    Ringpuffer ringpuffer;


    public WorkerThread(DatagramPacket dp, DatagramSocket socket, Ringpuffer ringpuffer){
        this.socket = socket;
        this.dp=dp;
        this.ringpuffer=ringpuffer;
    }



    public void run(){

        while (true) {
            //Bleibe solange in der While - Schleife, bis Werte da sind
            while (Ringpuffer.getCounter() == 0) {

            }
                try {
                    System.out.println("Got a message");
                    //Daten auslesen
                    dp = Ringpuffer.getDp();
                    int port = dp.getPort();
                    InetAddress sender = dp.getAddress();
                    byte[] data = dp.getData();
                    String msg = new String(data, 0, data.length);
                    System.out.println("\n-------------\nBEFEHL: " + msg);
                    String[] msgSplit = msg.split("[ ,]", 3);
                    String answer = "";
                    System.out.println("Working on command");
                    switch (msgSplit[0]) {
                        case "READ":
                            System.out.println("READ AUSFÜHREN");
                            int lineNr = Integer.parseInt(msgSplit[2].trim());
                            if (lineNr < 0) {
                                answer = "Die Zeilennummer kann nicht negativ sein.";
                                break;
                            }
                            try {
                                answer = MyFile.read(msgSplit[1].trim(), lineNr);
                            } catch (IOException e) {
                                answer = "Es ist ein Fehler aufgetreten" + e.toString();
                            } catch (NullPointerException n) {
                                answer = "Die Datei " + msgSplit[1] + " hat weniger als " + lineNr + " Zeilen";
                            }
                            break;
                        case "WRITE":
                            System.out.println("WRITE AUSFÜHREN");
                            try {
                                String[] writeMsg = msgSplit[2].split(",", 2);
                                String fileName = msgSplit[1];
                                String lineNumber = writeMsg[0];
                                String dataToWrite = writeMsg[1];
                                dataToWrite = dataToWrite.trim();
                                if (MyFile.write(fileName, Integer.parseInt(lineNumber), dataToWrite)) {
                                    answer = "Die Zeile wurde erfolgreich ausgetauscht.";
                                } else {
                                    answer = "Es ist ein Fehler aufgetreten";
                                }
                                ;
                            } catch (Exception E) {
                                answer = "Es ist ein Fehler aufgetreten";
                            }
                            break;
                        default:
                            System.out.println("FAIL");
                            answer = "FAIL";
                            break;
                    }
                    System.out.println("Sending answer");
                    byte[] msgToSend = answer.getBytes();
                    //Antwort zusammenbauen
                    DatagramPacket sendDP = new DatagramPacket(msgToSend, msgToSend.length, sender, port);
                    socket.send(sendDP);
                    System.out.println("Done");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }



    }

    /* public static void main(String[] args) {
        System.out.println(ringpuffer);
        //Erstellen von 10 neuen Threads
        for(int i=0; i<10; i++) {
            try {
                DatagramSocket threadSocket = new DatagramSocket(7000 + i);
                byte[] msgToRecieve = new byte[65507];
                DatagramPacket recieveDP = new DatagramPacket(msgToRecieve, msgToRecieve.length);
                WorkerThread workerThread = new WorkerThread(recieveDP, threadSocket, ringpuffer);
                workerThread.start();
                System.out.println("Thread" + i + "wurde gestartet");
            } catch (SocketException e) {
                e.printStackTrace();
            }

        }

    } */
}

