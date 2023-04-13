package org.flashcards.log;

public class Log {

    private static final StringBuilder log = new StringBuilder();

    public static void appendLog (String msg) {
        log.append(msg)
                .append("\n");
    }

    public static String getLog(){
        return log.toString();
    }
}
