package Testat_2;

public class ReadWriteFile {
    private int readers = 0;
    private boolean writing = false;
    private int waitingW = 0;
    private boolean readersturn = false;

    public synchronized void aquireRead() throws InterruptedException {
        while(writing || (waitingW>0 && !readersturn)) wait();
        ++readers;
    }

    public synchronized void releaseRead() {
        --readers;
        readersturn=false;
        if(readers==0) notify();
    }

    public synchronized void aquireWrite() throws InterruptedException {
        ++waitingW;
        while (readers<0 || writing) wait();
        --waitingW;
        writing=true;
    }

    public synchronized void releaseWriter() {
        writing = false;
        readersturn = true;
        notifyAll();
    }

}
