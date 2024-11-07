package org.example;

public class Car extends Thread {
    @Override
    public void run(){

    }
    public void parking(int time) throws InterruptedException {
        sleep(time* 1000);
    }
}
