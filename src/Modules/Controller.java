package Modules;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static Modules.Calculator.*;
import static Modules.DataFetcher.loadInitFile;
import static Modules.DataFetcher.loadRoadsFile;

public class Controller {

    public static LinkedHashMap<String, City> cityList = new LinkedHashMap<>();
    public static ContaminationDeck deck = new ContaminationDeck();
    static int contaminationLevel = 0;
    public static Listener listener;
    
    static {
        try {
            listener = new Listener();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static void init() throws IOException {
        cityListInitializer();
        deck.initializer();
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

    // LISTENER CALLS---------------------------------------------------------------------------------------
    public static boolean start(String keys) {
        StringTokenizer tokens = new StringTokenizer(keys);
        LinkedList<String> list = new LinkedList<>();
        while(tokens.hasMoreTokens()) {
            String str = tokens.nextToken();
            if (!cityList.containsKey(str)) return false;
            list.add(str);
        }
        if(list.size() != 9) return false;
        deck.setup(list);
        return true;
    }
    
    public static boolean draw(String input) {
        
        LinkedList<String> list = tokenSeparator(input);
        
        for(String str : list) {
            if(!cityList.containsKey(str)) return false;
        }
        
        deck.discardMultiple(list);
        for(String str : list)
            cityList.get(str).addCubes();
        return true;
    }

    public static boolean epidemic(String input) {
        
        if(!cityList.containsKey(input)) return false;
        
        deck.epidDiscard(input);
        cityList.get(input).addCubes(3);
        deck.reShuffle();
        contaminationLevel++;
        
        return true;
    }

    public static boolean treat(String input) {
        
        StringTokenizer tokens = new StringTokenizer(input);
        String cursor = tokens.nextToken();
        int cubes, colorCode;
        
        if (!cityList.containsKey(cursor)) return false;
        City city = cityList.get(cursor);
        
        if(!tokens.hasMoreTokens())
            city.removeCubes(1);
        else {
            
            cubes = Integer.parseInt(tokens.nextToken());
            
            if (tokens.hasMoreTokens()) {
                colorCode = Integer.parseInt(tokens.nextToken());
                if (invalidColorCode(colorCode)) return false;
                city.removeCubes(cubes, colorCode);
            }
            else city.removeCubes(cubes);
        }
        return true;
    }

    public static boolean contaminate(String input) {
        
        StringTokenizer tokens = new StringTokenizer(input);
        String cursor = tokens.nextToken();
        int cubes, colorCode;
        
        if (!cityList.containsKey(cursor)) return false;
        City city = cityList.get(cursor);
        
        if(!tokens.hasMoreTokens())
            city.addCubes();
        else {
            
            cubes = Integer.parseInt(tokens.nextToken());
            
            if (tokens.hasMoreTokens()) {
                colorCode = Integer.parseInt(tokens.nextToken());
                if (invalidColorCode(colorCode)) return false;
                city.addCubes(cubes, colorCode);
            }
            else city.addCubes(cubes);
        }
        return true;
    }
    
    public static boolean pdDraw(String input) {
        if (!cityList.containsKey(input)) return false;
        City city = cityList.get(input);
        if (deck.getSubPile(0).contains(input))
            city.splashCity(city.getColor());
        else city.addCubes();
        return true;
    }
    
    public static boolean outbreak(String input) {
        
        if (!cityList.containsKey(input)) return false;
        City city = cityList.get(input);
        
        for (String key : city.getLikedCities()) {
            if(deck.getSubPile(0).contains(key))
                cityList.get(key).splashCity(city.getColor());
            else city.addCubes();
        }
        return true;
    }
    
    public static boolean eradicate(String input) {
        
        int colorCode = Integer.parseInt(input);
        
        if (invalidColorCode(colorCode)) return false;
        if (colorCode == 5) return false;
        
        for (String key : cityList.keySet()) {
            cityList.get(key).checkEradicate(colorCode);
        }
        return true;
    }
    
    public static boolean roadBlocks(String input) {
        
        LinkedList<String> list = tokenSeparator(input);
        
        String city = list.pop();
        if (!cityList.containsKey(city)) return false;
        Set<String> cityRoads = cityList.get(city).getLikedCities();
        
        for (String road : list) {
            if(!cityRoads.contains(road)) return false;
            else deleteRoad(city, road);
        }
        return true;
    }
    
    public static boolean enclose(String input) {
        
        if (!cityList.containsKey(input)) return false;
        
        City city = cityList.get(input);
        
        for (String road : city.getLikedCities()) {
            deleteRoad(road, input);
        }
        
        city.setEnclosed(true);
        
        return true;
    }
    
    public static boolean quarantine(String input) {
        if (!cityList.containsKey(input)) return false;
        cityList.get(input).setQuarantined(true);
        return true;
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
    
    public static void easyPrintRisk() {
        LinkedList<City> cities = new LinkedList<>();
        riskCities.forEach(risk -> cities.addLast(cityList.get(risk)));
        splashedCities.forEach(splash -> cities.addLast(cityList.get(splash)));
        System.out.println("~".repeat(37) + " CITTÀ A RISCHIO " + "~".repeat(38));
        easyPrint(cities);
    }
    
    public static void easyPrintAll() {
        System.out.println("~".repeat(53) + " STATO CITTÀ " + "~".repeat(54));
        easyPrint(cityList.values());
    }
    
    public static boolean editCubes(String input) {
        if (input.startsWith("-")) return treat(input.substring(1));
        if (input.startsWith("+")) return contaminate(input.substring(1));
        return false;
    }

    // PRIVATE---------------------------------------------------------------------------------------------------
    private static void easyPrint(Collection<City> cities) {
        System.out.println("-".repeat(92));
        for (City c : cities) {
            System.out.print(c.summary());
            for(LinkedList<String> pile : deck.getDeck()) {
                if(deck.getDiscardPile().contains(c.getKey())) {
                    System.out.print("pila scarti\n");
                    break;
                }
                else if(pile.contains(c.getKey())) {
                    System.out.print("pila #" + deck.getDeck().indexOf(pile) + "\n");
                    break;
                }
            }
            System.out.println("-".repeat(92));
        }
    }
    
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
    
    private static boolean invalidColorCode(int colorCode) {
        return colorCode < 1 || colorCode > 5;
    }
    
    private static void deleteRoad(String c1, String c2) {
        cityList.get(c1).getLikedCities().remove(c2);
        cityList.get(c2).getLikedCities().remove(c1);
    }

    // DEBUG-----------------------------------------------------------------------------------------------------
    public static void printDeck_DEBUG() {
        deck.getDeck().forEach(System.out::println);
    }

    public static void printCityKeys_DEBUG() {
        cityList.keySet().forEach(System.out::println);
    }
}
