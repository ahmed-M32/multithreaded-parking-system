package org.example;

import java.util.concurrent.Semaphore;

public class parking_lot {
    public park<Thread>[];
    public final Semaphore park_control;

    parking_lot(){
        park_control = new Semaphore(4);
    }
}
