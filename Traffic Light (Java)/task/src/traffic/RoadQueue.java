package traffic;

public class RoadQueue {

    private final Road[] roads;
    private final IndexPointer front;
    private final IndexPointer rear;
    private final int interval;
    private int openRoadIdx;
    private int roadsCount;

    public RoadQueue(int size, int interval) {
        this.roads = new Road[size];
        this.front = new IndexPointer(size);
        this.rear = new IndexPointer(size);
        this.interval = interval;
        this.openRoadIdx = 0;
        this.roadsCount = 0;
    }

    public Road[] getRoads() {
        return roads;
    }

    public void resetRoadsState() {
        roadsCount = 0;
        boolean initialSetting = false;
        for (int i = 0; i < roads.length; i++) {
            Road road = roads[i];
            if (road == null) continue;

            roadsCount++;

            if (!initialSetting) {
                initialSetting = true;
                road.setState(Road.RoadState.OPEN);
                road.setTime(interval);
                openRoadIdx = i;
            } else {
                road.setState(Road.RoadState.CLOSED);
                road.setTime(interval * i);
            }
        }
    }

    public void enqueue(Road road) throws QueueFullException {
        if (isFull()) {
            throw new QueueFullException();
        }
        int pointer = rear.get();
        if (roads[pointer] != null) {
            rear.next();
            roads[rear.get()] = road;
        } else {
            roads[pointer] = road;
        }
        resetRoadsState();
    }

    public Road dequeue() throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }
        Road road = roads[front.get()];
        roads[front.get()] = null;

        front.next();

        resetRoadsState();
        return road;
    }

    public boolean isFull() {
        for (Road road : roads) {
            if (road == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        for (Road road : roads) {
            if (road != null) {
                return false;
            }
        }
        return true;
    }

    public void updateRoadsTime() {
        if (isEmpty()) {
            return;
        }

        IndexPointer pointer = new IndexPointer(roads.length, openRoadIdx);
        int count = roadsCount;
        for (int i = 0; i < roads.length; i++) {

            Road road = roads[pointer.get()];
            if (road == null) {
                pointer.next();
                continue;

            }
            int time = road.getTime();
            if (time > 1) {
                road.setTime(time - 1);
                pointer.next();
                continue;
            }
            if (road.isClosed()) {
                openRoadIdx = pointer.get();
                road.setState((Road.RoadState.OPEN));
                road.setTime(interval);
            } else {
                road.setState((Road.RoadState.CLOSED));
                road.setTime((count - 1) * interval);
                count--;
            }
            pointer.next();
        }
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
