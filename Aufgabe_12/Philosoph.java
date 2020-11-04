package Aufgabe_12;

public class Philosoph extends Thread{
    private int id;
    private Monitor table;

    public Philosoph(int id, Monitor table){
        this.id=id;
        this.table=table;
    }

    public void run(){
        for(int i=0; i<8; i++){
            try{
                Thread.sleep(1000+id*200);
            }catch (Exception e){
                e.printStackTrace();
            }
            table.startEating(id);
            try {
                Thread.sleep(1000+id*200);
            }catch (Exception e){
                e.printStackTrace();
            }
            table.stopEating(id);
        }
    }

    public static void main(String[] args) {
        int max=5;
        Monitor table = new Monitor(max);
        Philosoph p[] = new Philosoph[max];
        for (int id = 0;id<max;id++){
            p[id] = new Philosoph(id,table);
            p[id].start();
        }
        for(int id=0;id<max; id++){
            try {
                p[id].join();
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("Fertig, alle Philosophen sind satt");
        }
    }
}