package Testat_3;

import Testat_2.MyFile;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
                    //Daten aus Ringpuffer auslesen
                    dp = ringpuffer.getDp();
                    int port = dp.getPort();
                    InetAddress sender = dp.getAddress();
                    byte[] data = dp.getData();
                    String msg = new String(data, 0, data.length);
                    System.out.println("\n-------------\nBEFEHL: " + msg);
                    //Beispielhafte Eingabe: READ file0,1
                    //Nachrichten aufteilen in 2 Teile: Nachricht wird nach dem Leerzeichen getrennt
                    String[] msgSplit1 = msg.split("[(\\s )]", 2);
                    //Zweiter Teil der Nachricht, wird auch nochmal geteilt: Nachricht wird nach dem Komma getrennt
                    String[] msgSplit = msgSplit1[1].split("[ ,]", 2);
                    String answer = "";
                    System.out.println("Working on command");

                    switch (msgSplit1[0]) {
                        case "READ":
                            System.out.println("READ AUSFÜHREN");
                            int lineNr = Integer.parseInt(msgSplit[1].trim());
                            if (lineNr < 0) {
                                answer = "Die Zeilennummer kann nicht negativ sein.";
                                break;
                            }

                            try {
                                //Hier wird gesteuert, welchen Monitor wir verwenden wollen; enthält die Nachricht "file0", dann nehmen wir MyFile1
                                //ist "file1" vorhanden, wird MyFile2 angesteuert
                                //dannach erfolgt die selbe Logik wie in Aufgabe 14
                                switch (msgSplit[0]) {
                                    case "file0":
                                        answer = MyFile1.myFileObject.read(msgSplit[0].trim(), lineNr);
                                        break;
                                    case "file1":
                                        answer = MyFile2.myFileObject.read(msgSplit[0].trim(), lineNr);
                                }
                                answer = MyFile.myFileObject.read(msgSplit[0].trim(), lineNr);
                            } catch (IOException e) {
                                answer = "Es ist ein Fehler aufgetreten" + e.toString();
                            } catch (NullPointerException n) {
                                answer = "Die Datei " + msgSplit[1] + " hat weniger als " + lineNr + " Zeilen";
                            }
                            break;
                        case "WRITE":
                            System.out.println("WRITE AUSFÜHREN");
                            try {
                                //Beim Schreiben in eine Datei wird die selbe Logik wie beim Lesen ausgeführt (bezogen auf die Verwaltung der Monitore)
                                String[] writeMsg = msgSplit[1].split(",", 2);
                                String fileName = msgSplit[0];
                                String lineNumber = writeMsg[0];
                                String dataToWrite = writeMsg[1];
                                dataToWrite = dataToWrite.trim();
                                switch (msgSplit[0]) {
                                    case "file0":
                                        if (MyFile1.myFileObject.write(fileName, Integer.parseInt(lineNumber), dataToWrite)) {
                                            answer = "Die Zeile wurde erfolgreich ausgetauscht.";
                                        } else {
                                            answer = "Es ist ein Fehler aufgetreten";
                                        }
                                        break;
                                    case "file1":
                                        if (MyFile2.myFileObject.write(fileName, Integer.parseInt(lineNumber), dataToWrite)) {
                                            answer = "Die Zeile wurde erfolgreich ausgetauscht.";
                                        } else {
                                            answer = "Es ist ein Fehler aufgetreten";
                                        }
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

}

