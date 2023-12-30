package traffic;

import java.util.IdentityHashMap;
import java.util.Map;

public class Colors {

    private static final Map<Key, String> colors = new IdentityHashMap<>(Map.of(
            Key.ANSI_RED, "\u001B[31m",
            Key.ANSI_GREEN, "\u001B[32m",
            Key.ANSI_YELLOW, "\u001B[33m",
            Key.ANSI_RESET, "\u001B[0m"
    ));


    private enum Key {
        ANSI_RED, ANSI_GREEN, ANSI_YELLOW, ANSI_RESET;
    }

    private static String wrap(Key key, String text) {
        String ansiColor = colors.get(key);
        String ansiReset = colors.get(Key.ANSI_RESET);
        return ansiColor + text + ansiReset;
    }

    public static String wrapRed(String text) {
        return wrap(Key.ANSI_RED, text);
    }public static String wrapGreen(String text) {
        return wrap(Key.ANSI_GREEN, text);
    }public static String wrapYellow(String text) {
        return wrap(Key.ANSI_YELLOW, text);
    }


}
