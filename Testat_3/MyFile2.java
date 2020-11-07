package Testat_3;

import Testat_2.MyFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


//Leser Schreiber Problem
public class MyFile2 {

    private int readers = 0;
    private boolean writing = false;
    private int waitingW = 0;
    private boolean readersturn = false;

    public static MyFile myFileObject = new MyFile();

    // Methode um Auslesen zu starten
    public synchronized void startRead() {
        while(writing || (waitingW>0 && !readersturn)) try{ wait(); } catch (Exception e) {}//tryCatch
        ++readers;
    }

    // Methode um Auslesen zu beenden
    public synchronized void endRead(){
        --readers;
        readersturn=false;
        notifyAll();
    }

    // Methode um den Schreibvorgang zu beginnen
    public synchronized void startWrite() {
        ++waitingW;
        while (readers>0 || writing) try{ wait(); } catch (Exception e) {}//tryCatch
        --waitingW;
        writing=true;
    }

    // Methode um den Schreibvorgang zu beenden
    public synchronized void endWrite(){
        writing = false;
        readersturn = true;
        notifyAll();
    }

    // Read Methode aus Aufgabe 14 mit nur einem expliziten File (hier "file1")
    public String read(int line) throws IOException, NullPointerException {
        startRead(); // Lesevorgang beginnen
        BufferedReader fileIn = new BufferedReader(new FileReader("C:\\Users\\Tobi-\\Desktop\\timKollochsrc\\Testat_3\\file1"));
        try {
            Thread.sleep(3500);
        }catch(Exception e){
            e.printStackTrace();
        }

        String zeile = "";
        for(int x = 0; x < line; x++){
            fileIn.readLine();
        }
        if((zeile = fileIn.readLine()) == null){
            throw new NullPointerException();
        }
        endRead(); //Lesevorgang beenden
        return zeile;

    }

    // Write Methode aus Aufgabe 14 mit nur einem expliziten File (hier "file1")
    public synchronized boolean write(int line, String data){
        startWrite(); //Schreibvorgang beginnen
        boolean replaced = false;
        try {
            List<String> lines = Files.readAllLines(Path.of("C:\\Users\\Tobi-\\Desktop\\timKollochsrc\\Testat_3\\file1"));
            lines.set(line-1,data);
            FileWriter writer = new FileWriter("C:\\Users\\Tobi-\\Desktop\\timKollochsrc\\Testat_3\\file1");
            try {
                Thread.sleep(3500);
            }catch(Exception e){
                e.printStackTrace();
            }
            for(String str: lines) {
                writer.write(str+"\n");
            }
            writer.close();
            replaced = true;
        } catch (Exception e) {
            System.out.println("Problem reading file.");
            e.printStackTrace();
        }
        endWrite(); //Schreibvorgang beenden
        return replaced;
    }
}