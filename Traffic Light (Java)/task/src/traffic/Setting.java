package traffic;

public class Setting {
    private  int roads;
    private int intervals;

    public Setting(int roads, int intervals) {
        this.roads = roads;
        this.intervals = intervals;
    }

    public int getRoads() {
        return roads;
    }

    public void setRoads(int roads) {
        this.roads = roads;
    }

    public int getIntervals() {
        return intervals;
    }

    public void setIntervals(int intervals) {
        this.intervals = intervals;
    }
}
