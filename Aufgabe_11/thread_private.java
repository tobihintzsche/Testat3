package Aufgabe_11;

public class thread_private extends Thread{
    int id;

    public thread_private(int id){
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
            Aufgabe_11_private.eat(id);
        }
    }
}
