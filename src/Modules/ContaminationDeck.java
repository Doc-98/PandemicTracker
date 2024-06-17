package Modules;

import java.util.LinkedList;

import static Modules.Calculator.*;
import static Modules.Controller.cityList;
import static Modules.Controller.getContaminationDraw;

public class ContaminationDeck {

    private LinkedList<LinkedList<String>> deck;
    private LinkedList<String> discardPile;
    private int bottomPileCheck = 48;

    // CONSTRUCTOR
    public ContaminationDeck() {
        this.deck = new LinkedList<>();
        this.deck.add(0, new LinkedList<>());
        this.discardPile = new LinkedList<>();
    }

    // ACTIONS
    public void initializer() {
        for(String str : cityList.keySet()) {
            deck.get(0).add(str);
        }
        updateProb();
    }

    public void setup(LinkedList<String> list) {
        discardMultiple(list);
        for(int i = 1; i <= 9; i++) {
            cityList.get(list.get(i - 1)).addCubes((9 - i) / 3 + 1);
        }
        updateProb();
    }

    public void reShuffle() {
        deck.addLast(discardPile);
        discardPile = new LinkedList<>();
        updateProb();
    }

    public void singleDiscard(String card) {
        discard(card);
        updateProb();
    }

    public void discardMultiple(LinkedList<String> cards) {
        for(String str : cards) {
            discard(str);
        }
        updateProb();
    }

    //INFORMATION GETTERS
    public int size(int index) {
        return deck.get(index).size();
    }

    public boolean isEmpty(int index) {
        return deck.get(index).isEmpty();
    }

    // GETTERS
    public LinkedList<LinkedList<String>> getDeck() {
        return deck;
    }

    public LinkedList<String> getSubPile(int index) {
        return deck.get(index);
    }

    public LinkedList<String> getTopPile() {
        return this.deck.getLast();
    }

    // PRIVATE
    private boolean removeCard(String card) {
        return deck.getLast().remove(card);
    }

    private void discard(String card) {
        if(!removeCard(card)) {
            System.out.println("card not found");
        }
        discardPile.add(card);
        cityList.get(card).setBottomDrawProbability(0f);
    }

    private void updateProb() {
        
        for(String str : cityList.keySet()) {
            cityList.get(str).setDrawProbability(cityProbability(str, deck.size(), getContaminationDraw()));
        }
        
        int botDeckSize = deck.get(0).size();
        
        if(bottomPileCheck != botDeckSize) {
            for(String str : deck.get(0)) {
                cityList.get(str).setBottomDrawProbability(singleProb(botDeckSize));
            }
            bottomPileCheck = botDeckSize;
        }
    }

    // DEBUG
    private boolean addCard(String card) {
        return this.deck.getLast().add(card);
    }

    private boolean addCard(int index, String card) {
        return deck.get(index).add(card);
    }

    private boolean removeCard(int index, String card) {
        return deck.get(index).remove(card);
    }

    // TO STRING
    public String toString(){
        
        StringBuilder str = new StringBuilder();
        
        str.append("--- MAZZO ---");
        for(int i = deck.size() - 1; i >= 0; i--) {
            str.append("Mazzetto #" + i + ":\n");
            for(String key : deck.get(i)) {
                str.append("\t" + cityList.get(key).getName() + "\n");
            }
        }
        
        str.append("\n\n--- SCARTI ---\n");
        for(String key : discardPile) {
            str.append("\t" + cityList.get(key).getName() + "\n");
        }
        
        return String.valueOf(str);
    }
}
