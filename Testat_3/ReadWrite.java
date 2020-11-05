package Testat_3;

import Sonstiges.MyThread1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


//Leser Schreiber Problem
public interface ReadWrite {

     static String read_(String f, int line) throws IOException, NullPointerException {
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
        return zeile;
    }

     static  boolean write_(String f, int line, String data){
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
        return replaced;
    }
}

