package Modules;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static Modules.Controller.cityList;
import static Modules.Controller.deck;

public class Calculator {

    public static Set<String> riskCities = new HashSet<>();

    public static float cityProbability(String city, int deckSize, int numDraws) {

        LinkedList<String> pile = deck.getSubPile(deckSize - 1);
        int pileSize = pile.size();

        if(pile.contains(city)) {
            return drawProb(pileSize, numDraws);
        } else {
            if(pileSize < numDraws) {
                return cityProbability(city, deckSize - 1, numDraws - pileSize);
            }
        }
        return 0f;
    }

    private static float drawProb(int size, int numDraws) {

        if(size <= numDraws) return 1f;

        float p = 0;
        for(int i = 0; i < numDraws; i++) {
            p += singleProb(size - i);
        }
        return p;
    }

    public static float singleProb(int size) {
        return 1f/(float)(size);
    }

    public static float outbreakProbability(){

        float prob = 0f;

        for(String str : riskCities) {
            prob = prob + cityList.get(str).getDrawProbability() + cityList.get(str).getBottomDrawProbability();
        }

        return prob;
    }

}
