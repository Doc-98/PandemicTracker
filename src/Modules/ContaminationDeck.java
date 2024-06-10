package Modules;

import java.util.LinkedList;

import static Modules.Controller.cityList;

public class ContaminationDeck {

    private LinkedList<LinkedList<String>> deck;
    private LinkedList<String> discardPile;

    // CONSTRUCTOR
    public ContaminationDeck() {
        this.deck = new LinkedList<>();
        this.deck.add(0, new LinkedList<>());
        this.discardPile = new LinkedList<>();
    }

    public void initializer() {
        for(String str : cityList.keySet())
            deck.get(0).add(str);
    }

    public boolean addCard(String card) {
        return this.deck.getLast().add(card);
    }

    public boolean addCard(int index, String card) {
        return deck.get(index).add(card);
    }

    public boolean removeCard(String card) {
        return deck.getLast().remove(card);
    }

    public boolean removeCard(int index, String card) {
        return deck.get(index).remove(card);
    }

    public boolean isEmpty(int index) {
        return deck.get(index).isEmpty();
    }

    public int size(int index) {
        return deck.get(index).size();
    }

    public void reShuffle() {
        deck.addLast(discardPile);
        discardPile = new LinkedList<>();
    }

    public void discard(String card) {
        if(!removeCard(card)) {
            System.out.println("card not found");
            discardPile.add(card);
        }
    }

    public void discardMultiple(LinkedList<String> cards) {
        for(String str : cards) {
            discard(str);
        }
    }

    // GETTERS
    public LinkedList<LinkedList<String>> getDeck() {
        return deck;
    }

    public LinkedList<String> getSubPile(int index) {
        return deck.get(index);
    }

    public int getTopPileSize() {
        return this.deck.size();
    }

    public int getSubPileSize(int index) {
        return this.deck.get(index).size();
    }
}
