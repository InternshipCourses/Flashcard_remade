package org.flashcards.input_output;

public class ConsoleIOException extends RuntimeException{

    public ConsoleIOException(String message) {
        super(message);
    }

    public ConsoleIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
