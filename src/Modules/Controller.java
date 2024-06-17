package Modules;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import static Modules.Calculator.*;
import static Modules.DataFetcher.loadInitFile;
import static Modules.DataFetcher.loadRoadsFile;

public class Controller {

    public static LinkedHashMap<String, City> cityList = new LinkedHashMap<>();
    public static ContaminationDeck deck = new ContaminationDeck();
    public static Listener listener;
    
    static {
        try {
            listener = new Listener();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    static int contaminationLevel = 0;

    public static void init() throws IOException {
        cityListInitializer();
        deck.initializer();
    }

    public static void start(String keys) {
        StringTokenizer tokens = new StringTokenizer(keys);
        LinkedList<String> list = new LinkedList<>();
        while(tokens.hasMoreTokens()) {
            list.add(tokens.nextToken());
        }
        if(list.size() != 9) throw new IllegalArgumentException();
        deck.setup(list);
    }

    public static int getContaminationDraw() {
        return switch (contaminationLevel) {
            case 0, 1, 2 -> 2;
            case 3, 4 -> 3;
            case 5, 6 -> 4;
            default -> {
                System.out.println("Contamination level out of bounds");
                yield 0;
            }
        };
    }

    // LISTENER CALLS
    // TODO: GESTIRE CASI DI INPUT INVALIDO (QUI O NEL LISTENER)
    
    // Prende una stringa con le chiavi di ogni città pescata separate da uno spazio. Le pesca tutte.
    public static void draw(String input) {
        LinkedList<String> list = tokenSeparator(input);
        deck.discardMultiple(list);
        for(String str : list)
            cityList.get(str).addCubes();
    }

    public static void epidemic(String input) {
        deck.singleDiscard(input);
        cityList.get(input).addCubes(3);
        deck.reShuffle();
        contaminationLevel++;
    }

    public static void treat(String input) {
        StringTokenizer tokens = new StringTokenizer(input);
        City city = cityList.get(tokens.nextToken());
        if(tokens.countTokens() == 0)
            city.removeCubes(1);
        else if (tokens.countTokens() == 1)
            city.removeCubes(Integer.parseInt(tokens.nextToken()));
        else if (tokens.countTokens() == 2)
            city.removeCubes(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
        else
            throw new IllegalArgumentException();
    }

    public static void contaminate(String input) {
        StringTokenizer tokens = new StringTokenizer(input);
        City city = cityList.get(tokens.nextToken());
        if(tokens.countTokens() == 0)
            city.addCubes();
        else if(tokens.countTokens() == 1)
            city.addCubes(Integer.parseInt(tokens.nextToken()));
        else if(tokens.countTokens() == 2)
            city.addCubes(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
        else throw new IllegalArgumentException();
    }
    
    public static void outbreak(String input) {
        
        City city = cityList.get(input);
        
        for (String key : city.getLikedCities()) {
            cityList.get(key).splashCity(city.getColor());
        }
    }
    
    public static void printProbabilities() {
        printProb();
    }
    
    public static void printDeck() {
        System.out.println(deck);
    }

    public static void printCities() {
        cityList.values().forEach(System.out::println);
    }

    // PRIVATE
    private static void cityListInitializer() throws IOException {
        File f = new File("C:\\Users\\dvinc\\IdeaProjects\\PandemicTracker\\src\\Resources\\cities.csv");

        if(f.exists()) loadInitFile(f);

        f = new File("C:\\Users\\dvinc\\IdeaProjects\\PandemicTracker\\src\\Resources\\roads.csv");

        if(f.exists()) loadRoadsFile(f);
    }

    private static void setContaminationLevel(int n) {
        contaminationLevel = n;
    }

    private static LinkedList<String> tokenSeparator(String input) {
        StringTokenizer tokens = new StringTokenizer(input);
        LinkedList<String> list = new LinkedList<>();
        while (tokens.hasMoreTokens()) {
            list.add(tokens.nextToken());
        }
        return list;
    }

    // DEBUG
    public static void printDeck_DEBUG() {
        deck.getDeck().forEach(System.out::println);
    }

    public static void printCityKeys_DEBUG() {
        cityList.keySet().forEach(System.out::println);
    }
}
