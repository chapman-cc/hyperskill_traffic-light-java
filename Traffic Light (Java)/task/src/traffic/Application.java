package traffic;

import java.util.IdentityHashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class Application {
    private final Output output;
    private final Input input;
    private final Menu menu;
    private final TrafficLight trafficLight;
    private final Thread thread;
    private AppState state;


    public Application(int road, int interval) {

        this.input = new Input();
        this.output = new Output();
        this.menu = new Menu(new IdentityHashMap<>(Map.of(
                1, this::addRoad,
                2, this::deleteRoad,
                3, this::showSystem,
                0, this::quitApplication
        )));
        this.state = AppState.NOT_STARTED;
        RoadQueue roadQueue = new RoadQueue(road, interval);
        this.trafficLight = new TrafficLight(roadQueue, interval);
        this.trafficLight.setDisplay(this::printSystemInfo);
        this.thread = new Thread(trafficLight);
        this.thread.setName("QueueThread");
    }

    private void setState(AppState state) {
        this.state = state;
    }

    public void run() {
        try {
            thread.start();
            this.thread.join(1000L);
            trafficLight.setVisible(false);
            setState(AppState.MENU);
            while (true) {
                switch (state) {
                    case MENU -> showMenu();
                    case SYSTEM -> showSystem();
                    case TERMINATE -> {
                        return;
                    }
                }
            }
        } catch (RuntimeException | InterruptedException e) {
            output.println(e);
        } finally {
            input.close();
        }
    }


    private void addRoad() {
        try {
            output.print("Input road name: ");
            String name = input.next();
            Road road = new Road(name);
            trafficLight.addRoad(road);
            output.printf("%s Added%n", road.getName());

        } catch (InputMismatchException | RoadQueue.QueueFullException e) {
            output.println(e.getMessage());
        } finally {
            input.nextLine();
        }
    }

    private void deleteRoad() {
        try {
            Road road = trafficLight.popRoad();
            output.printf("%s deleted%n", road.getName());
        } catch (InputMismatchException | RoadQueue.QueueEmptyException e) {
            output.println(e.getMessage());
        } finally {
            input.nextLine();
        }
    }

    private void showMenu() {
        trafficLight.setVisible(false);
        setState(AppState.MENU);
        menu.display(output::println);
        String optionsRegex = menu.getOptionsRegex();
        menu.selectOption(() -> input.nextInt(optionsRegex, () -> {
            output.println("Incorrect option");
            input.nextLine();
            input.nextLine();
            menu.display(output::println);
        }));
    }


    private void showSystem() {
        setState(AppState.SYSTEM);
        trafficLight.setVisible(true);
        if (input.waitForBankLine()) {
            showMenu();
        }
    }

    private void printSystemInfo(Long seconds) {
        RoadQueue queue = trafficLight.getQueue();
        output.printSystemInfo(
                seconds,
                queue.getRoads().length,
                trafficLight.getInterval(),
                queue.getRoads()
        );
    }

    private void quitApplication() {
        setState(AppState.TERMINATE);
        trafficLight.setRunning(false);
        output.println("Bye!");
        input.close();
    }

    private enum AppState {
        NOT_STARTED, MENU, SYSTEM, TERMINATE
    }

//    public static void main(String[] args) {
//        try {
//            Application app = new Application(2, 2);
//            app.trafficLight.addRoad(new Road("RoadA"));
//            app.trafficLight.addRoad(new Road("RoadB"));
////            app.trafficLight.addRoad(new Road("RoadC"));
////            app.trafficLight.addRoad(new Road("RoadD"));
////            app.trafficLight.addRoad(new Road("RoadE"));
//
//            app.run();
//        } catch (RoadQueue.QueueFullException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
