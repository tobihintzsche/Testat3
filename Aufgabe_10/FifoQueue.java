package Aufgabe_10;

public class FifoQueue {
    element head = null;
    public void put(String s){
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
    }
    public String get(){
        String s = head.t;
        head = head.next;
        return s;
    }

    public boolean isEmpty(){
        return head == null;
    }
}


//drei adden, drei rausnehmen pro Thread;
//wenn die liste leer ist soll ein Fehler kommen
//am Ende des Programms muss die Liste leer sein