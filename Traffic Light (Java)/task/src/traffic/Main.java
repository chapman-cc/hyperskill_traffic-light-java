package traffic;

public class Main {
    public static void main(String[] args) {
        Input input = new Input();
        Output output = new Output();
        String numRegex = "^[1-9]\\d*$"; // positiveNumber not 0
        String errorMsg = "Error! Incorrect Input, Try Again: ";

        output.println("Welcome to the traffic management system!");
        output.print("Input the number of roads: ");
        int roads = input.nextInt(numRegex, () -> output.print(errorMsg));

        output.print("Input the interval: ");
        int intervals = input.nextInt(numRegex, () -> output.print(errorMsg));

        try {
            Application trafficLight = new Application(roads, intervals);
            trafficLight.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            input.close();
        }
    }
}