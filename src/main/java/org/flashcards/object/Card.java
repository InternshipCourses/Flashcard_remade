package org.flashcards.object;

import java.util.Objects;


public class Card {
     private String definition;
     private int answerError;

    public Card (){
        this.definition = "";
        this.answerError = 0;
    }

     public Card( String definition, int answerError) {
         this.definition = definition;
         this.answerError = answerError;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getAnswerError() {
        return answerError;
    }

    public void setAnswerError(int answerError) {
        this.answerError = answerError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return definition.equals(card.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(definition);
    }
}
