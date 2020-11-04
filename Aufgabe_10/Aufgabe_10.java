package Aufgabe_10;

public class Aufgabe_10 {
    static FifoQueue queue = new FifoQueue();
    static Runnable r = new Runnable() {
        @Override
        public void run() {
            for(int i = 0; i<10000;i++){
                try{
                    queue.put("Test1");
                    queue.put("Test2");
                    queue.put("Test3");
                    System.out.println(Thread.currentThread().getName() + ": " + queue.get());
                    System.out.println(Thread.currentThread().getName() + ": " + queue.get());
                    System.out.println(Thread.currentThread().getName() + ": " + queue.get());
                }catch(Exception e){
                    System.out.print(Thread.currentThread().getName() + ": ");
                    e.printStackTrace();
                    break;
                }
            }
        }
    };

    public static void main(String[] args) {
        Thread one = new Thread(r);
        Thread two = new Thread(r);
        one.start();
        two.start();
        try{
            one.join();
            two.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("IsEmpty: " + queue.isEmpty());
        if(!queue.isEmpty()){
            System.out.println(queue.get());
        }
    }
}
