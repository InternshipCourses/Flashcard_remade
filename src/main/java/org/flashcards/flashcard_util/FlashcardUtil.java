package org.flashcards.flashcard_util;

import org.flashcards.object.Card;
import org.flashcards.object.FlashCard;
import org.flashcards.input_output.IO;
import java.io.*;
import java.util.*;

import static org.flashcards.object.FlashCard.cards;
public class FlashcardUtil implements FlashcardInterface {

    private final IO iOReader;

    public FlashcardUtil(IO inputOutput) {
        this.iOReader = inputOutput;
    }

    public void addCard(String term, Card card){
        cards.put(term,card);
        iOReader.write(term + " has been successfully added");
    }

    public void updateCardDefinition(String term, String newDefinition,int errorCount){
        cards.replace(term,cards.get(term),new Card(newDefinition,errorCount));
        iOReader.write(term + " Updated");
    }
    public void updateCardError(String term){
        cards.replace(term,cards.get(term),new Card(cards.get(term).getDefinition(),cards.get(term).getAnswerError() + 1));
        iOReader.write(term + " Updated card's mistake");
    }
    public  void displayCardTermAndRequestDefinition(int count) {
        //Todo fix this method must be a better to loop through and try to randomize the values
        int i = 0;

        for (var cardData : FlashCard.cards.entrySet()) {
            iOReader.write("Print the definition of \"" + cardData.getKey() + "\":");

            String answer = iOReader.read();
            String result = checkAnswer(cardData.getKey(), answer);

            iOReader.write(result);

            if (!result.equals("Correct!")){
                updateCardError(cardData.getKey());
            }
            i++;
            if (i == count){
                return;
            }
        }
    }
    public  void displayCardTermAndRequestDefinition() {
        // Todo: this method  displays all cards remove if not need or extend menu to include it later
        for (var cardData : cards.entrySet()) {
            iOReader.write("Print the definition of \"" + cardData.getKey() + "\":");
            String answer = iOReader.read();
            iOReader.write(checkAnswer(cardData.getKey(), answer));
        }
    }


    public void importCard(String filename){
        List<String> importedCard =  importCardsFromFile(filename);
        if (!importedCard.isEmpty()) {
            int count = 0;
            for (String card: importedCard) {
                String[] cardSplitArray = card.split(" == ");
                if (!cards.containsKey(cardSplitArray[0])) { // checking if card term already in list
                    // checking if each card has a hard value, value > 0 means it hard
                    int error = 0;
                    if (cardSplitArray.length > 2) {
                        error = Integer.parseInt(cardSplitArray[2]);
                    }

                    Card newCard = new Card(cardSplitArray[1],error);
                    addCard(cardSplitArray[0], newCard);
                    count++;
                }else {
                    updateCardDefinition(cardSplitArray[0], cardSplitArray[1], Integer.parseInt(cardSplitArray[2])); // updating the definition of the term found
                }
            }
            iOReader.write(count + " cards have been loaded.");
        }
    }

    public void addingNewCard() {

        String term;
        String definition;
        iOReader.write("The Card:");
        term = iOReader.read();

        if (cards.containsKey(term)) {
            iOReader.write("The card \"" + term + "\" already exists.");
            return;
        }

        iOReader.write("The definition of the card:");
        definition = iOReader.read();

        if (cards.containsValue(new Card(definition,0))) {
            iOReader.write("The definition \"" + definition + "\" already exists. Try again:");
            return;
        }

        addCard(term, new Card(definition,0));
        iOReader.write("The pair (\"" + term + "\":\"" + definition + "\") has been added");
    }

    public void removeCard(String key){
        if (cards.containsKey(key)) {
            cards.remove(key);
            iOReader.write("The card has been removed.");
        } else {
            iOReader.write("Can't remove \"" + key + "\": there is no such card.");
        }

    }

    public Map<String,Card> getCards (){
        return cards;
    }

    public String checkAnswer( String term,String answer) {
        return new Card(answer,0).equals(cards.get(term)) ? "Correct!" : getCorrectAnswer(term, answer);
    }
    private String getCorrectAnswer(String term, String answer) {
        return (cards.containsValue(new Card(answer, 0))) ?
                "Wrong. The right answer is \"" + cards.get(term).getDefinition() + "\", but your definition is correct for \"" + getKey(answer) + "\"."
                : "Wrong. The right answer is \"" + cards.get(term).getDefinition() + "\"";
    }

    public String getKey(String value){
        for (var key:cards.entrySet()) {
            if(key.getValue().getDefinition().equals(value)) {
                return key.getKey();
            }
        }
        return "Key can not be found";
    }

    public List<String> importCardsFromFile(String fileName){
        File file = new File(fileName);
        ArrayList<String> importedCard = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(file)){
            while (fileScanner.hasNext()) {
                importedCard.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new FlashcardException("File not found.",e);
        }
        return importedCard;
    }

    public void exportCardsToFile(String fileName){
        try (FileWriter fileWriter = new FileWriter(fileName,true)){
            int count=0;
            for (Map.Entry<String, Card> card:cards.entrySet()){
                fileWriter.append(card.getKey())
                        .append(" == ")
                        .append(card.getValue().getDefinition())
                        .append(" == ")
                        .append(String.valueOf(card.getValue().getAnswerError()))
                        .append("\n");
                count++;
            }
            iOReader.write(count + " cards have been saved.");
        } catch (IOException e) {
            throw new FlashcardException("Error Saving File",e);
        }
    }

    public void printAllHardestCardOld() {
        LinkedHashMap<String, Integer> hardestCards = new LinkedHashMap<>();

        cards.forEach((term,card)->{
            if(card.getAnswerError() > 0){
                hardestCards.put(term, card.getAnswerError());
            }
        });

        if (hardestCards.isEmpty()){
            iOReader.write("There are no cards with errors.");
        } else if (hardestCards.size() == 1) {
            for (Map.Entry<String, Integer> hardestCard: hardestCards.entrySet()){
                iOReader.write("The hardest card is \""+hardestCard.getKey()+"\". You have "+hardestCard.getValue()+" errors answering it");
            }
        } else {
            StringBuilder result = new StringBuilder();
            result.append("The hardest cards are " );
            int count = 0;
            int errorCount = 0;
            for (Map.Entry<String, Integer> hardestCard: hardestCards.entrySet()) {

                result.append("\"")
                        .append(hardestCard.getKey())
                        .append((count < hardestCards.size() - 1) ? "\", " : "\"");
                count++;
                errorCount = (errorCount <hardestCard.getValue()) ? hardestCard.getValue() : errorCount;
            }
            iOReader.write(result + ". You have "+ errorCount + " errors answering them.");

//          System.out.println("The hardest cards are \"France\", \"Japan\", \"Russia\". You have 3 errors answering them.");
        }
    } // Todo to current solution, remove later



    public void resetStats(){
        for (Map.Entry<String, Card> card: cards.entrySet()) {
            if (card.getValue().getAnswerError() > 0) {
                card.getValue().setAnswerError(0);
            }
        }
        iOReader.write("Card statistics have been reset.");
    }


    public void listCards(){
        for (Map.Entry<String, Card> card: cards.entrySet() ){
            iOReader.write("term :" + card.getKey() +
                    "\ndefinition :" + card.getValue().getDefinition() +
                    "\nerror count :"+card.getValue().getAnswerError());
        }
    }
    public void printAllHardestCard() {
        LinkedHashMap<String, Integer> hardestCards = new LinkedHashMap<>();

        cards.forEach((term,card)->{
            if(card.getAnswerError() > 0){
                hardestCards.put(term, card.getAnswerError());
            }
        });

        if (hardestCards.isEmpty()){
            iOReader.write("There are no cards with errors.");
        } else if (hardestCards.size() == 1) {
            for (Map.Entry<String, Integer> hardestCard: hardestCards.entrySet()){
                iOReader.write("The hardest card is \""+hardestCard.getKey()+"\". You have "+hardestCard.getValue()+" errors answering it");
            }
        } else {
            StringBuilder result = new StringBuilder();
            result.append("The hardest cards are " );
            int count = 0;
            int errorCount = 0;
            for (Map.Entry<String, Integer> hardestCard: hardestCards.entrySet()) {

                result.append("\"")
                        .append(hardestCard.getKey())
                        .append((count < hardestCards.size()-1) ? "\", " :"\"");
                count++;
                errorCount = (errorCount <hardestCard.getValue()) ? hardestCard.getValue() : errorCount;
            }
            iOReader.write(result + ". You have "+ errorCount + " errors answering them.");
        }
    }

    @Override
    public void add() {
        addingNewCard();
    }

    @Override
    public void remove() {
        iOReader.write("Which card?");
        removeCard(iOReader.read());
    }

    @Override
    public void importCards(String fileLocation) {
        importCard(fileLocation);
    }

    @Override
    public void exportCards(String fileLocation) {
        exportCardsToFile(fileLocation);
    }

    @Override
    public void ask() {
        iOReader.write("How many times to ask?");
        int numberOfAsk= Integer.parseInt(iOReader.read());
        displayCardTermAndRequestDefinition(numberOfAsk);
    }

    @Override
    public void hardest() {
        printAllHardestCard();
    }

    @Override
    public void reset() {
        resetStats();
    }
}
