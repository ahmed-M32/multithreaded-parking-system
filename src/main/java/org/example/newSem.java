package org.example;

public class newSem {
    private int permits;

    public newSem(int initialPermits) {
        if (initialPermits < 0) {
            throw new IllegalArgumentException("Permits cannot be negative");
        }
        this.permits = initialPermits;
    }


    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
            wait();
        }
        permits--;
    }


    public synchronized void release() {
        permits++;
        notify();
    }



    public synchronized int availablePermits() {
        return permits;
    }
}
