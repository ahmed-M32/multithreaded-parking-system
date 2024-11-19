package org.example;

public class Car extends Thread {
    public parking_lot spot;
    int gate_id;
    int car_id;
    int arrival_time;
    int stay_time;


    Car(int gate_id, int car_id, int arrival_time, int stay_time, parking_lot spot) {
        this.gate_id = gate_id;
        this.car_id = car_id;
        this.arrival_time = arrival_time;
        this.stay_time = stay_time;
        this.spot = spot;

    }

    @Override
    public void run() {
        try {
            spot.park_car(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void get_park_spot(parking_lot spot) {
        this.start();
    }
}
