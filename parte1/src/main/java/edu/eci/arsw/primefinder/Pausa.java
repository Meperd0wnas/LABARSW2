package edu.eci.arsw.primefinder;

public class Pausa {
    private boolean paused = false;

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resumeAll() {
        paused = false;
        notifyAll(); // despierta a todos los hilos esperando en este monitor
    }

    public synchronized void esperaSiPausado() throws InterruptedException {
        while (paused) {
            wait(); // libera el lock y espera a notifyAll
        }
    }

    public synchronized boolean isPaused() {
        return paused;
    }
}
