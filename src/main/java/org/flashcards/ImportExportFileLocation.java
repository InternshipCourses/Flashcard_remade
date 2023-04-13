package org.flashcards;

import org.flashcards.flashcard_util.FlashcardInterface;
import org.flashcards.input_output.IO;
import org.flashcards.input_output.Input;
import org.flashcards.input_output.Output;

public interface ImportExportFileLocation {
    Input getInput();
    FlashcardInterface getFlashcardGame(IO ioConfig);

}
