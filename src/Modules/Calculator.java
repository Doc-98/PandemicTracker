package Modules;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static Modules.Controller.cityList;
import static Modules.Controller.deck;

public class Calculator {

    public static Set<String> riskCities = new HashSet<>();
    public static Set<String> splashedCities = new HashSet<>();

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

    public static float outbreakProb() {

        float prob = 0f;

        for(String str : riskCities) {
            if(deck.getTopPile().contains(str) && !cityList.get(str).isQuarantined())
                prob = prob + cityList.get(str).getDrawProbability();
        }

        return prob;
    }
    
    public static float bottomOutbreakProb() {
        
        float prob = 0f;
        
        for(String str : splashedCities) {
            if (!cityList.get(str).isQuarantined()) prob += cityList.get(str).getBottomDrawProbability();
        }
        
        return prob;
    }
    
    public static void printProb() {
        
        System.out.println("\n########################################################################################\n");
        System.out.println("La probabilità di focolaio per CONTAMINAZIONE è pari al " + outbreakProb() * 100 + "%");
        System.out.println("Le città a rischio NEL MAZZO sono le seguenti:\n");
        
        for (String city : riskCities) {
            if(deck.getTopPile().contains(city))
                System.out.println("\t" + cityList.get(city).getName());
        }
        
        System.out.println("\nLe città a rischio NEL MAZZO DEGLI SCARTI sono le seguenti:\n");
        for (String city : riskCities) {
            if(deck.getDiscardPile().contains(city))
                System.out.println("\t" + cityList.get(city).getName());
        }
        
        System.out.println("\n---------------------------------------------------------------------------------\n");
        
        System.out.println("La probabilità di focolaio per EPIDEMIA è pari al " + bottomOutbreakProb() * 100 + "%");
        System.out.println("Le città a rischio che possono essere PESCATE DAL FONDO sono le seguenti:\n");
        
        for (String city : splashedCities) {
            System.out.println("\t" + cityList.get(city).getName());
        }
        System.out.println("\n########################################################################################");
    }

}
