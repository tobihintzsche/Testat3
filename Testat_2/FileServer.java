package Testat_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class FileServer {
    //Konstanten
    private static final int SERVER_PORT = 7777;
    private static final int FILENAME_LENGTH = 5;
    private static final int LEFT_BORDER = 48;
    private static final int RIGHT_BORDER = 122;
    private static final String SAVE = "SAVE";
    private static final String GET = "GET";
    //Entsprechend anzupassen bzw bei Programmstart zu übergeben
    private static String FILEPATH;

    public static void main(String[] args) {
        FILEPATH = (args.length>0?args[0]:"C:\\Users\\a765209\\Desktop\\Messages");
        //Bei Start den Messages Ordner anlegen (falls er nicht existiert)
        File directory = new File(FILEPATH);
        if (!directory.exists()){
            directory.mkdir();
        }
        //Variablen zuerst mit null (bzw leer) deklarieren
        ServerSocket server = null;
        Socket connection = null;
        BufferedReader userIn = null;
        PrintWriter out = null;
        String line;
        String[] msg;
        String command;
        String message;
        try{
            server = new ServerSocket(SERVER_PORT);
            while(true){
                //Verbindung (wieder) eröffnen bzw. auf Verbindung warten
                System.out.println("Waiting for connection");
                connection = server.accept();
                System.out.printf("Got a connection from %s@%d\n",connection.getInetAddress(),connection.getPort());
                userIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                out = new PrintWriter(connection.getOutputStream());
                //Erhaltene Nachricht entschlüsseln und reagieren
                line = userIn.readLine();
                try{
                    msg = line.split(" ",2);
                    command = msg[0];
                    message = msg[1];
                    switch (command){
                        case SAVE:
                            System.out.println(SAVE + " text: " + message);
                            out.println(save(message));
                            break;
                        case GET:
                            System.out.println(GET + " key: " + message);
                            out.println(get(message));
                            break;
                        default:
                            System.out.println("Unknown command: " + command);
                            out.println("FAILED: Unknown command " + command);
                            break;
                    }
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Message from client was not well formatted. Closing connection.");
                    out.println("FAILED: The message was not well formatted");
                }
                //Antworten
                System.out.println("Send an answer");
                out.flush();
                //Verbindung schließen
                connection.close();
                System.out.print("Connection closed\n----------\n");
            }
        }catch(IOException e){
            System.err.println(e.toString());
        }finally{
            try{
                if(connection != null)connection.close();
                if(out != null) out.close();
                if(userIn != null) userIn.close();
                if(server != null) server.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private static String save(String msg){
        String fileName;
        File f;
        PrintWriter printWriter = null;
        //Zufälligen Dateinamen generieren und prüfen, ob dieser schon existiert
        do{
            fileName = generateRandomString();
            System.out.println("Der Dateiname ist: " + fileName);
        }while((f = new File(FILEPATH+"\\"+fileName+".txt")).exists());
        //Erhaltene Nachricht in die erzeugte Datei schreiben
        try{
            printWriter = new PrintWriter(new FileWriter(f));
            printWriter.write(msg);
            //Den Schlüssel zurückgeben
            return "KEY: " + fileName;
        }catch(IOException e){
            e.printStackTrace();
            return "FAILED: Error creating file. Please try again later.";
        }finally {
            if(printWriter != null) printWriter.close();
        }
    }

    private static String get(String msg){
        File f = new File(FILEPATH+"\\"+msg+".txt");
        BufferedReader bufferedReader = null;
        //Datei öffnen und auslesen
        try{
            bufferedReader = new BufferedReader(new FileReader(f));
            String currentLine;
            StringBuilder text = new StringBuilder();
            while ((currentLine = bufferedReader.readLine()) != null) {
                text.append(currentLine).append("\n");
            }
            bufferedReader.close();
            return "OK: " + text.toString();
        //Wenn die Datei nicht existiert den Fehler zurückgeben
        }catch(FileNotFoundException e){
            System.err.println("File with key " + msg + " does not exist:\n" + e.toString());
            return "FAILED: A file with the given key ("+msg+") does not exist";
        //Datei exisistiert, aber anderer Fehler
        }catch(IOException e){
            e.printStackTrace();
            return "FAILED: An error occurred while reading the file. Please try again later.";
        //Egal was passiert, den Leser versuchen zu schließen
        }finally {
            try{
                if(bufferedReader != null) bufferedReader.close();
            }catch(IOException e){
                System.err.println("FAILED: BufferedReader could not be closed:\n" + e.toString());
                e.printStackTrace();
            }
        }
    }

    private static String generateRandomString(){
        Random random = new Random();
        return random.ints(LEFT_BORDER, RIGHT_BORDER + 1)
                //Filtert randoms raus, die zwischen 58 und 65 bzw. 91 und 96 liegen, damit nur Zahlen und (Klein-)Buchstaben kommen
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(FILENAME_LENGTH)
                //Verbindet die verschiedenen Buchstaben zu einem String
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
