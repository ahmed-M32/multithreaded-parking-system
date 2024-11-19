package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class park_controller {
    static List<Car> cars;
    static parking_lot p = new parking_lot(4);
    Gate gate1 = new Gate(1, p);
    Gate gate2 = new Gate(2, p);
    Gate gate3 = new Gate(3, p);


    park_controller(parking_lot p) {
        park_controller.p = new parking_lot(4);
    }

    public void start() throws InterruptedException {
        int size = cars.size();
        int time = 0;


        gate1.start();
        gate2.start();
        gate3.start();

        while (!cars.isEmpty()) {
            List<Car> carsToAdd = new ArrayList<>();

            for (Car car : cars) {
                if (car.arrival_time == time) {
                    carsToAdd.add(car);
                }
            }

            for (Car car : carsToAdd) {
                switch (car.gate_id) {
                    case 1 -> gate1.add_car(car);
                    case 2 -> gate2.add_car(car);
                    case 3 -> gate3.add_car(car);
                }
                cars.remove(car); // remove car after processing
            }

            Thread.sleep(1000);
            time++;
        }
        for (int i = 0; i < cars.size() ; i++) {
            System.out.println(cars.get(i).car_id);
        }
        try {
            gate1.join();
            gate2.join();
            gate3.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void readCarData(String fileName) {
        List<Car> carData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");

                int gate = Integer.parseInt(parts[0].split(" ")[1]);
                int carId = Integer.parseInt(parts[1].split(" ")[1]);
                int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                int duration = Integer.parseInt(parts[3].split(" ")[1]);

                carData.add(new Car(gate, carId, arrivalTime, duration, p));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        carData.sort(Comparator.comparingInt(car -> car.arrival_time));

        cars = new ArrayList<>(carData);
    }

    void print() {
        for (Car car : cars) {
            System.out.println(car.car_id);
        }
    }
}
