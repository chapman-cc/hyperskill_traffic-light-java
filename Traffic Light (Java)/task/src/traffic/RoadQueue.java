package traffic;

import java.util.Arrays;
import java.util.Iterator;

public class RoadQueue {

    private final Road[] roads;
    private final IndexPointer front;
    private final IndexPointer rear;
    private final int interval;
    private int roadsCount;

    public RoadQueue(int size, int interval) {
        this.roads = new Road[size];
        this.front = new IndexPointer(size);
        this.rear = new IndexPointer(size);
        this.interval = interval;
        this.roadsCount = 0;
    }

    public Road[] getRoads() {
        return roads;
    }

    public int getRoadsCount() {
        return roadsCount;
    }

    public Iterator<Road> getRoadsIterator() {
        return Arrays.stream(roads).iterator();
    }

    public boolean isFull() {
        return roadsCount == roads.length;
    }

    public boolean isEmpty() {
        return roadsCount == 0;
    }

    public void push(Road road) throws QueueFullException {
        if (isFull()) {
            throw new QueueFullException();
        }

        if (roadsCount != 0) {
            rear.next();
        }
        roads[rear.get()] = road;
        roadsCount++;

        if (roadsCount == 1) {
            road.setState(Road.RoadState.OPEN);
            road.setTime(interval + 1);
            return;
        }

        int prevIdx = rear.get() != 0 ? rear.get() - 1 : front.get();
        Road prevRoad = roads[prevIdx];
        if (prevRoad.isOpen()) {
            road.setTime(prevRoad.getTime());
        } else {
            road.setTime(prevRoad.getTime()+interval);
        }

    }

    public Road pop() throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }
        Road road = roads[front.get()];
        roads[front.get()] = null;
        roadsCount--;

        front.next();
        return road;
    }

    public static class QueueFullException extends Exception {
        public QueueFullException() {
            super("Queue is full");
        }
    }

    public static class QueueEmptyException extends Exception {
        public QueueEmptyException() {
            super("Queue is empty");
        }
    }
}
