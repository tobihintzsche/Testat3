package Testat_2;

import java.util.concurrent.locks.ReadWriteLock;

/* abstract class ReaderWriter implements ReadWrite {
    protected int activeReaders = 0;
    protected int activeWriters = 0;
    protected int waitingReaders = 0;
    protected int waitingWriters = 0;

    //protected abstract void read_();
    //protected abstract void write_();

    public void read(){
        beforeRead();
        read_();
        afterRead();
    }

    public void write(){
        beforeWrite();
        write_();
        afterWrite();
    }

    public boolean allowReader(){
        return waitingWriters == 0 && activeWriters == 0;
    }

    protected boolean allowWriter(){
        return activeReaders == 0 && activeWriters == 0;
    }

    protected synchronized void beforeRead(){
        ++waitingReaders;
        while(!allowReader());
        try{ wait(); } catch (Exception e) {}
        --waitingReaders;
        ++activeReaders;
    }

    protected synchronized void afterRead(){
        --activeReaders;
        notifyAll();
    }

    protected synchronized void beforeWrite(){
        ++waitingWriters;
        while(!allowWriter());
        try{ wait(); } catch (Exception e) {}
        --waitingWriters;
        ++activeWriters;
    }

    protected synchronized void afterWrite(){
        --activeWriters;
        notifyAll();
    }

} */
