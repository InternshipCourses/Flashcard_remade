package org.flashcards.flashcard_util;

public class FlashcardException extends RuntimeException{
    public FlashcardException(String message) {
        super(message);
    }

    public FlashcardException(String message, Throwable cause) {
        super(message, cause);
    }
}
