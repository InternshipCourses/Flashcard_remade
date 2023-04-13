package org.flashcards;

import org.flashcards.flashcard_util.FlashcardException;
import org.flashcards.flashcard_util.FlashcardInterface;
import org.flashcards.flashcard_util.FlashcardUtil;
import org.flashcards.input_output.ConsoleIO;
import org.flashcards.input_output.IO;
import org.flashcards.input_output.Input;
import org.flashcards.log.Log;

import java.io.FileWriter;
import java.io.IOException;

public class FlashCardGame {

    private final Input fileLocation;
    private final IO iOReader = new ConsoleIO();
    private final FlashcardInterface card;

    public FlashCardGame(ImportExportFileLocation config) {
        this.fileLocation = config.getInput();
        this.card = config.getFlashcardGame(iOReader);
    }

    public void statGame() {

        if (!this.fileLocation.read().equals("")){
            card.importCards(this.fileLocation.read());
        }

        String response;
        iOReader.write("""
                
                        add a card: add
                        remove a card: remove
                        load cards from file: import
                        save cards to file: export
                        ask for definitions of some random cards: ask
                        exit the program: exit
                        """);

        do {
            iOReader.write("Input the action (add, remove, import, export, ask, exit, " +
                    "log, hardest card, reset stats):");

            response = iOReader.read();

            switch (response){
                case "add" -> card.add();
                case "remove" -> card.remove();
                case "import" -> {
                    iOReader.write("File name");
                    card.importCards(iOReader.read());
                }

                case "export" -> {
                    iOReader.write("File name:");
                    card.exportCards(iOReader.read());
                }
                case "ask" -> card.ask();

                case "log" ->{
                    iOReader.write("File name:");
                    String file = iOReader.read();
                    try (FileWriter fileWriter = new FileWriter(file)){
                        fileWriter.append(Log.getLog());
                    } catch (IOException e) {
                        throw new FlashcardException("Log File saving Error",e);
                    }
                    iOReader.write("The log has been saved.");
                }
                case "hardest card" -> card.hardest();
                case "reset stats" -> card.reset();
                case "exit" -> iOReader.write("Bye bye!");

            }

        } while (!response.equals("exit"));
    }

}
