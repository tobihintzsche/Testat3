package Aufgabe_11;

public class thread extends Thread{
    int id;

    public thread (int id){
        this.id=id;
    }

    public void run(){
        while(true){
            int wait = (int) (Math.random()*5000);
            try {
                Thread.sleep(wait);
            }catch(Exception e){
                System.out.println("Verhungert: " + e.getMessage());
            }
            Aufgabe_11.eat(id);
        }
    }
}
