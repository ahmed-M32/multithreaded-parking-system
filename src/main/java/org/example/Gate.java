package org.example;

import java.util.ArrayList;
import java.util.List;


public class Gate extends Thread {
    int waiting_time;
    int gate_id;
    List<Car> cars;
    parking_lot parking_spot = null;
    private final Object lock = new Object();
    List<Car> waiting_cars = new ArrayList<>();


    Gate(int gate_id, parking_lot parking_lot) {
        this.gate_id = gate_id;
        parking_spot = parking_lot;
        this.cars = new ArrayList<>();
    }

    public int get_id() {
        return gate_id;
    }

    void add_car(Car car) throws InterruptedException {
        synchronized (lock) {
            cars.add(car);
            lock.notify();
        }
    }

    public void get_car(List<Car> cars) {
        this.cars = new ArrayList<Car>(cars);
    }

    @Override
    public void run() {


        Thread thread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (cars.isEmpty()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    } else {

                        Car current = cars.remove(0);
                        /*if (parking_spot.available_spots() == 0 && parking_spot != null) {
                            waiting_cars.add(current);
                            System.out.println("here");
                        }*/
                        waiting_cars.forEach(car -> {
                            car.get_park_spot(parking_spot);
                        });
                        current.get_park_spot(parking_spot);

                        System.out.println("car " + current.car_id + " arrived from gate " + current.gate_id + " at time " + current.arrival_time);

                    }
                }
            }
        });
        thread.start();
    }
}
