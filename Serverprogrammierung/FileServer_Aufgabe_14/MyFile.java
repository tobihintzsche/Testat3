package Serverprogrammierung.FileServer_Aufgabe_14;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class MyFile {

    public static String read(String f, int line) throws IOException, NullPointerException {
        BufferedReader fileIn = new BufferedReader(new FileReader("C:/Users/Tobi-/Desktop/timKollochsrc/Serverprogrammierung/FileServer_Aufgabe_14/" +f));
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

    public static boolean write(String f, int line, String data){
        boolean replaced = false;
        try {
            List<String> lines = Files.readAllLines(Path.of("C:/Users/Tobi-/Desktop/timKollochsrc/Serverprogrammierung/FileServer_Aufgabe_14/" + f));
            lines.set(line-1,data);
            FileWriter writer = new FileWriter("C:/Users/Tobi-/Desktop/timKollochsrc/Serverprogrammierung/FileServer_Aufgabe_14/" + f);
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
