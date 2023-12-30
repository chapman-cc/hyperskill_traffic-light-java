package traffic;

import java.util.function.Consumer;

public class TrafficLight implements Runnable {
    private final RoadQueue queue;
    private Consumer<Long> display;
    private long milliseconds;
    private final int interval;
    private boolean visible;
    private boolean isRunning;

    public TrafficLight(RoadQueue roadQueue, int interval) {
        this(roadQueue, interval, (s) -> {
        });
    }

    public TrafficLight(RoadQueue roadQueue, int interval, Consumer<Long> display) {
        this.milliseconds = 0;
        this.interval = interval;
        this.display = display;
        this.visible = false;
        this.isRunning = true;
        this.queue = roadQueue;
    }

    public RoadQueue getQueue() {
        return queue;
    }

    public int getInterval() {
        return interval;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public void setDisplay(Consumer<Long> display) {
        this.display = display;
    }

    public void addRoad(Road road) throws RoadQueue.QueueFullException {
        queue.enqueue(road);
    }

    public Road popRoad() throws RoadQueue.QueueEmptyException {
        return queue.dequeue();
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(1000L);
                milliseconds += 1000;
                queue.updateRoadsTime();
                if (visible) {
                    display.accept(milliseconds / 1000);
                }
            } catch (InterruptedException e) {  
                throw new RuntimeException(e);
            }
        }
    }
}
