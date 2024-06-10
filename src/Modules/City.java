package Modules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class City {

    private final String name;
    private final String key;
    private final Color color;
    private final Set<String> likedCities =  new HashSet<String>();
    private final HashMap<Color, Integer> cubes;
    private int panicLevel;
    private boolean splashed;
    private float drawProbability;

    // CONSTRUCTOR
    public City (String name, String key,  int color, int panicLevel, boolean splashed) {

        this.name = name;

        this.key = key;

        this.color = findColor(color);

        this.cubes = initializeCubes();

        this.panicLevel = panicLevel;

        this.splashed = splashed;

        this.drawProbability = (1/39f + 1/38f);
    }

    // PRIVATE METHODS
    private Color findColor(int color) {
        return switch (color) {
            case 1 -> Color.YELLOW;
            case 2 -> Color.RED;
            case 3 -> Color.BLUE;
            case 4 -> Color.BLACK;
            default -> throw new IllegalArgumentException();
        };
    }

    private HashMap<Color, Integer> initializeCubes() {

        HashMap<Color, Integer> cubes = new HashMap<>();

        cubes.put(Color.YELLOW, 0);
        cubes.put(Color.RED, 0);
        cubes.put(Color.BLUE, 0);
        cubes.put(Color.BLACK, 0);

        return cubes;
    }

    private void addCubesEx(Color color, int cubes) {
        this.cubes.replace(color, this.cubes.get(color) + cubes);
    }
    private void maxOutCubes(Color color) {
        this.cubes.replace(color, 3);
    }

    private void removeCubesEx(Color color, int cubes) {
        this.cubes.replace(color, this.cubes.get(color) - cubes);
    }

    private void outbreakAlert() {
        System.out.println("OUTBREAK!\n\t" + this.name);
        System.out.println("\tCities Affected: " + this.likedCities);
        maxOutCubes(this.color);
        this.panicLevel++;
    }

    // PUBLIC METHODS
    // Adds one cube of the corresponding color to the city. Gives alert in case of outbreak.
    public void addCubes() {
        if(this.cubes.get(this.color) == 3) {
            outbreakAlert();
        } else addCubesEx(this.color, 1);
    }
    // Adds the specified number of cubes of the corresponding color to the city. Gives alert in case of outbreak and sets cubes to 3.
    public void addCubes(int cubes) {
        if(this.cubes.get(this.color) + cubes > 3) {
            outbreakAlert();
        } else addCubesEx(this.color, cubes);
    }
    // Adds the specified number of cubes of the specified color to the city. Gives alert in case of outbreak and sets cubes to 3.
    public void addCubes(int color, int cubes) {
        if(this.cubes.get(this.color) + cubes > 3) {
            outbreakAlert();
        } else addCubesEx(findColor(color), cubes);
    }

    public void removeCubes(int cubes) {
        removeCubesEx(this.color, cubes);
    }

    public void removeCubes(int cubes, int color) {
        removeCubesEx(findColor(color), cubes);
    }

    // GETTERS
    public String getName() {
        return this.name;
    }

    public String getKey() {
        return this.key;
    }

    public Color getColor() {
        return this.color;
    }

    public Set<String> getLikedCities() {
        return this.likedCities;
    }

    public int getPanicLevel() {
        return this.panicLevel;
    }

    public boolean isSplashed() {
        return this.splashed;
    }

    public int getCubes(){
        return this.cubes.get(this.color);
    }

    // SETTERS
    public void setPanicLevel(int panicLevel) {
        this.panicLevel = panicLevel;
    }

    public void setSplashed(boolean splashed) {
        this.splashed = splashed;
    }

    // DEBUG
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.name + " (" + this.key + ")" + ":\n\t");
        str.append("Color: " + this.color + "\n\t");
        str.append("Links: " + this.likedCities + "\n\t");
        str.append("Panic Level: " + this.panicLevel + "\n\t");
        str.append("Splashed: " + this.splashed + "\n\t");
        str.append("Draw Probability: " + this.drawProbability + "\n\t");
        str.append("Cubes: \n\t" + this.cubes + "\n" );

        return str.toString();
    }
}
