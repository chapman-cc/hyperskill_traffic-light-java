package traffic;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Menu {
    private final String formattedMenu;
    private final Map<Integer, Runnable> options;

    public Menu(Map<Integer, Runnable> mapOfFunctions) {
        this.formattedMenu = setupFormattedMenu();
        this.options = mapOfFunctions;
    }

    private String setupFormattedMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("Menu:").append("\n");
        for (Command command : Command.values()) {
            int number = command.getNumber();
            String printName = command.getPrintName();
            String formattedLine = "%d. %s".formatted(number, printName);
            sb
                    .append(formattedLine)
                    .append("\n");
        }
        return sb.toString();
    }

    public String getOptionsRegex() {
        int[] keys = options.keySet().stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(keys);
        return "^[%d-%d]$".formatted(keys[0], keys[keys.length - 1]);
    }

    public void display(Consumer<String> consumer) {
        consumer.accept(formattedMenu);
    }

    public void selectOption(Supplier<Integer> supplier) {
        Integer select = supplier.get();
        selectOption(select);
    }

    public void selectOption(int select) {
        try {
            Runnable option = options.get(select);
            option.run();
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}
