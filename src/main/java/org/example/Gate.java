package org.example;

import java.util.ArrayList;
import java.util.List;


public class Gate extends Thread {
    int gate_id;
    List<Car> cars;
    parking_lot parking_spot = null;
    private final Object lock = new Object();
    List<Car> waiting_cars = new ArrayList<>();
    int cars_served = 0;


    Gate(int gate_id, parking_lot parking_lot) {
        this.gate_id = gate_id;
        parking_spot = parking_lot;
        this.cars = new ArrayList<>();
    }


    void add_car(Car car) throws InterruptedException {
        synchronized (lock) {
            cars.add(car);
            cars_served++;
            lock.notify();
        }
    }

    public int getServedCars(){
        return cars_served;
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
