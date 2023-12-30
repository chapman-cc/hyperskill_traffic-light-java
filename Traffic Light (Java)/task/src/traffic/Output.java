package traffic;

import java.io.PrintStream;

public class Output extends PrintStream {
    public Output() {
        super(System.out);
    }

    public void printSystemInfo(Long seconds, int numOfRoads, int interval, Road... roads) {
        StringBuilder sb = new StringBuilder();

        sb.append("! %ds. have passed since system startup !".formatted(seconds)).append("\n");
        sb.append("! Number of roads: %d !".formatted(numOfRoads)).append("\n");
        sb.append("! Interval: %d !".formatted(interval)).append("\n");

        if (roads.length != 0) {
            sb.append("\n");
            for (Road road : roads) {
                if (road != null) {

                    String systemMessage = road.getSystemMessage(r -> switch (r.getState()) {
                        case OPEN -> {
                            if (r.getTime() == 1) yield Colors::wrapYellow;
                            yield Colors::wrapGreen;
                        }
                        case CLOSED -> Colors::wrapRed;
                    });
                    sb.append(systemMessage).append("\n");
                }
            }
            sb.append("\n");
        }
        sb.append("! Press \"Enter\" to open menu !");
        println(sb);
    }

    public void clear() {
        try {
            ProcessBuilder cmd = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            cmd.inheritIO().start().waitFor();
        } catch (Exception e) {
            println(e);
        }
    }
}
