package traffic;

import java.util.Iterator;
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
        queue.push(road);
    }

    public Road popRoad() throws RoadQueue.QueueEmptyException {
        return queue.pop();
    }

    public void updateRoadsTime() {
        if (queue.isEmpty()) {
            return;
        }
        Iterator<Road> it = queue.getRoadsIterator();
        int count = queue.getRoadsCount();
        int i = 0;
        while (it.hasNext()) {
            i++;
            Road road = it.next();

            if (road == null) {
                continue;
            }
            int time = road.getTime();
            if (time > 1) {
                road.setTime(time - 1);
                continue;
            }

            if (queue.getRoadsCount() == 1 || road.isClosed()) {
                // Open road
                road.setState((Road.RoadState.OPEN));
                road.setTime(interval);
            } else {
                // Close road
                road.setState((Road.RoadState.CLOSED));
                road.setTime((count - 1) * interval);
                count--;
            }
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(1000L);
                milliseconds += 1000;
                updateRoadsTime();
                if (visible) {
                    display.accept(milliseconds / 1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
