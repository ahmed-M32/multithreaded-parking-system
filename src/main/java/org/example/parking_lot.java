package org.example;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class parking_lot {
    public final Semaphore park_control;
    int cars_served = 0;
    int currently_parked = 0;
    private final Map<Integer, Integer> gateCounts = new HashMap<>();


    parking_lot(int n) {
        park_control = new Semaphore(n);
    }

    int available_spots() {
        return park_control.availablePermits();
    }

    void park_car(Car car) throws InterruptedException {
        try {

            if (park_control.availablePermits() == 0) {
                System.out.println("car " + car.car_id + " is waiting at gate " + car.gate_id);

            }
            park_control.acquire();

            synchronized (this) {
                cars_served++;
                currently_parked++;
                System.out.println("Car " + car.car_id + " from Gate " + car.gate_id + " parked " + car.arrival_time + " (parking status : " + currently_parked + ")");
                gateCounts.put(car.gate_id, gateCounts.getOrDefault(car.gate_id, 0) + 1);

            }
            Thread.sleep(car.stay_time * 1000L);
            synchronized (this) {
                currently_parked--;
                park_control.release();
                System.out.println("Car " + car.car_id + " from Gate " + car.gate_id + " left after " + car.stay_time + " units of time. (parking status : " + currently_parked + " spots occupied)");


            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}
