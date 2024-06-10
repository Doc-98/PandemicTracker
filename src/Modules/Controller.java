package Modules;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import static Modules.DataFetcher.loadInitFile;
import static Modules.DataFetcher.loadRoadsFile;

public class Controller {

    public static LinkedHashMap<String, City> cityList = new LinkedHashMap<>();
    public static ContaminationDeck deck = new ContaminationDeck();
    public static Listener listener = new Listener();
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
        deck.discardMultiple(list);
        for(int i = 1; i <= 9; i++) {
            cityList.get(list.get(i - 1)).addCubes((9 - i) / 3 + 1);
        }
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
        deck.discard(input);
        cityList.get(input).addCubes(3);
        deck.reShuffle();
        contaminationLevel++;
    }

    public static void treat(String input) {
        StringTokenizer tokens = new StringTokenizer(input);
        City city = cityList.get(tokens.nextToken());
        if (tokens.countTokens() == 2)
            city.removeCubes(Integer.parseInt(tokens.nextToken()));
        else if (tokens.countTokens() == 3)
            city.removeCubes(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
        else
            throw new IllegalArgumentException();
    }

    public static void add(String input) {
        StringTokenizer tokens = new StringTokenizer(input);
        City city = cityList.get(tokens.nextToken());
        if(tokens.countTokens() == 1)
            city.addCubes();
        else if(tokens.countTokens() == 2)
            city.addCubes(Integer.parseInt(tokens.nextToken()));
        else if(tokens.countTokens() == 3)
            city.addCubes(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
        else throw new IllegalArgumentException();
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
    public static void printDecks() {
        deck.getDeck().forEach(System.out::println);
    }

    public static void printCityKeys() {
        cityList.keySet().forEach(System.out::println);
    }
}
