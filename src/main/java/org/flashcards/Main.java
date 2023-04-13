package org.flashcards;

import org.flashcards.flashcard_util.FlashcardInterface;
import org.flashcards.flashcard_util.FlashcardUtil;
import org.flashcards.input_output.IO;
import org.flashcards.input_output.Input;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new FlashCardGame(new Config(args)).statGame();
       /* new FlashCardGame(new ImportExportFileLocation() {
            @Override
            public Input getInput() {
                return () -> "/Users/kylebailey/prj/pet/FlashCard_remade/Flashcard/test_export";
            }

            @Override
            public FlashcardInterface getFlashcardGame(IO ioConfig) {
                return new FlashcardUtil(new IO() {
                    @Override
                    public String read() {
                        Scanner scanner = new Scanner(System.in);
                        return scanner.nextLine();
                    }

                    @Override
                    public void write(String inputData) {
                        System.out.println(inputData);
                    }
                });
            }
        }).statGame();*/


    }
}
