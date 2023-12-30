package traffic;

import java.util.function.Function;

public final class Road {
    private final String name;
    private RoadState state;
    private int time;

    public Road(String name) {
        this.name = name;
        this.state = RoadState.CLOSED;
        this.time = 0;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public RoadState getState() {
        return state;
    }

    public void setState(RoadState state) {
        this.state = state;
    }

    public boolean isOpen() {
        return state == RoadState.OPEN;
    }

    public boolean isClosed() {
        return state == RoadState.CLOSED;
    }

    //
    public String getSystemMessage() {
        Function<String, String> returnTextDirectly = (text) -> text;
        return getSystemMessage(info -> returnTextDirectly);
    }

    public String getSystemMessage(Function<Road, Function<String, String>> wrapperGetter) {
        Function<String, String> colorWrapper = wrapperGetter.apply(this);
        String coloredString = colorWrapper.apply("%s for %ds.".formatted(state.name().toLowerCase(), time));
        return "Road \"%s\" will be %s".formatted(name, coloredString);
    }

    @Override
    public String toString() {
        return "Road{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", time=" + time +
                '}';
    }

    public enum RoadState {
        OPEN, CLOSED
    }

}
