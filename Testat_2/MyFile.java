package Testat_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


//Leser Schreiber Problem
public class MyFile {

    private int readers = 0;
    private boolean writing = false;
    private int waitingW = 0;
    private boolean readersturn = false;

    public static MyFile myFileObject = new MyFile();


    public synchronized void startRead() {
        while(writing || (waitingW>0 && !readersturn)) try{ wait(); } catch (Exception e) {}//tryCatch
        ++readers;
    }

    public synchronized void endRead(){
        --readers;
        readersturn=false;
        notifyAll();
    }

    public synchronized void startWrite() {
        ++waitingW;
        while (readers>0 || writing) try{ wait(); } catch (Exception e) {}//tryCatch
        --waitingW;
        writing=true;
    }

    public synchronized void endWrite(){
        writing = false;
        readersturn = true;
        notifyAll();
    }

    public String read(String f, int line) throws IOException, NullPointerException {
        startRead();
        BufferedReader fileIn = new BufferedReader(new FileReader("C:\\Users\\Tobi-\\Desktop\\timKollochsrc\\Testat_3\\" +f));
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
        endRead();
        return zeile;

    }

    public synchronized boolean write(String f, int line, String data){
        startWrite();
        boolean replaced = false;
        try {
            List<String> lines = Files.readAllLines(Path.of("C:\\Users\\Tobi-\\Desktop\\timKollochsrc\\Testat_3\\" + f));
            lines.set(line-1,data);
            FileWriter writer = new FileWriter("C:\\Users\\Tobi-\\Desktop\\timKollochsrc\\Testat_3\\" + f);
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
        endWrite();
        return replaced;
    }
}
