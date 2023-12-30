package traffic;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    private final Scanner sc = new Scanner(System.in);

    public String nextLine() throws InputMismatchException {
        return sc.nextLine();
    }

    public String next() throws InputMismatchException {
        String input = sc.next();
        sc.nextLine();
        return input;
    }

    public int nextInt(String regex, Runnable invalidAction) throws InputMismatchException {
        String input = sc.next();
        while (!input.matches(regex)) {
            invalidAction.run();
            input = sc.next();
        }
        sc.nextLine();
        return Integer.parseInt(input);
    }

    public boolean waitForBankLine(){
        while (true) {
            String input = sc.nextLine();
            if (input.isEmpty()) {
                return true;
            }
        }
    }

    public void close() {
        sc.close();
    }
}
