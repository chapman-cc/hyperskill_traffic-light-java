package traffic;

public enum Command {
    ADD(1, "Add road"),
    DELETE(2, "Delete road"),
    SYSTEM(3, "Open system"),

    QUIT(0, "Quit");
    private final int number;
    private final String printName;

    Command(int number, String printName) {
        this.number = number;
        this.printName = printName;
    }

    public int getNumber() {
        return number;
    }

    public String getPrintName() {
        return printName;
    }

}
