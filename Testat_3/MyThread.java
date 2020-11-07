package Testat_3;

public class MyThread extends Thread{
    private int id;
    public MyThread(int id){
        this.id=id;
    }

    public void run(){
        try{ Thread.sleep( 1000);}
        catch (Exception e){}
        System.out.println("Hello World, ID" + id);
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++) {
            Thread t = new MyThread(i);
            t.start();
        }
    }
}
