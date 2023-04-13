package org.flashcards.flashcard_util;

public interface FlashcardInterface {
    void add();
    void remove();
    void importCards(String fileLocation);
    void exportCards(String fileLocation);
    void ask();
    void hardest();
    void reset();

}
