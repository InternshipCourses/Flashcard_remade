package org.flashcards;

import java.util.LinkedHashMap;



public class FlashCard {
    private String term;
     private Card card;

    public static LinkedHashMap<String,Card> cards;

    static {
        cards = new LinkedHashMap<>();
    }
    
    public FlashCard() {
        this.term = "";
        this.card = new Card();
    }

    public FlashCard(String term,Card card) {
        this.term = term;
        this.card = card ;
    }

    public FlashCard(FlashCard flashCard) {
        this.term = flashCard.term;
        this.card = flashCard.card;
    }

    /*List of getters and setters */
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public static void setCards(LinkedHashMap<String, Card> cards) {
        FlashCard.cards = cards;
    }




    /*Custom methods*/


}
//The hardest cards are "Japan", "Russia". You have 3 errors answering them.
//The hardest cards are "Japan", "Russia". You have 3 errors answering them.
//The hardest cards are "Japan", "Russia". You have 3 errors answering them.
//The hardest cards are "Russia", "France". You have 1 errors answering them.