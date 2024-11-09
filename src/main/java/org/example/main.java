package org.example;

class main {

    public static void main(String[] args) throws InterruptedException {
        parking_lot p = null;
        park_controller p1 = new park_controller(p);
        park_controller.readCarData("data.txt");

        p1.start();


    }

    public void print() {
    }

}