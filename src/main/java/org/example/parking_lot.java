package org.example;

import java.util.concurrent.CountDownLatch;


public class parking_lot {
    private CountDownLatch latch;

    public final newSem park_control;
    int cars_served = 0;
    int currently_parked = 0;


    parking_lot(int n) {
        park_control = new newSem(n);

    }


    public void setLatch(int n) {
        latch = new CountDownLatch(n);

    }

    void park_car(Car car) throws InterruptedException {
        try {
            if (park_control.availablePermits() == 0) {
                System.out.println("car " + car.car_id + " is waiting at gate " + car.gate_id);
                car.waiting = true;
            }
            park_control.acquire();

            synchronized (this) {
                cars_served++;
                currently_parked++;
                System.out.println("Car " + car.car_id + " from Gate " + car.gate_id + " parked " + car.arrival_time + " (parking status : " + currently_parked + ")");

            }
            Thread.sleep(car.stay_time * 1000L);
            synchronized (this) {
                currently_parked--;
                park_control.release();
                System.out.println("Car " + car.car_id + " from Gate " + car.gate_id + " left after " + car.stay_time + " units of time. (parking status : " + currently_parked + " spots occupied)");
                latch.countDown();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void awaitCompletion() throws InterruptedException {
        latch.await();
    }
    public void report() {
        System.out.println("Total cars served: " + cars_served);
    }
}
