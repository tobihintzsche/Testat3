package Aufgabe_10;

import java.util.concurrent.Semaphore;

public class FifoQueue_Semaphore {
    static Semaphore mutex = new Semaphore(1, true);
    element head = null;
    public void put(String s){
        try{
            mutex.acquire();
            element add = new element(s);
            if(head == null){
                head = add;
            }else{
                element current = head;
                while(current.next != null){
                    current = current.next;
                }
                current.next = add;
            }

            mutex.release();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public String get(){
        try{
            mutex.acquire();
            String s = head.t;
            head = head.next;
            mutex.release();
            return s;
        }catch (InterruptedException e){
            return e.getMessage();
        }
    }

    public boolean isEmpty(){
        return head == null;
    }
}


//drei adden, drei rausnehmen pro Thread;
//wenn die liste leer ist soll ein Fehler kommen
//am Ende des Programms muss die Liste leer sein