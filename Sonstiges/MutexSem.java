package Sonstiges;

import  java.util.concurrent.Semaphore;

public class MutexSem {
    static Semaphore mutex = new Semaphore(2, true);
    static Runnable r = new Runnable() {
        @Override
        public void run() {
            while(true){
                try{
                    mutex.acquire();
                    System.out.println(Thread.currentThread().getName() + " im k.A.");
                    Thread.sleep(3000);
                    mutex.release();
                }catch(InterruptedException e){
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    public static void main(String[] args) {
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
    }
}
