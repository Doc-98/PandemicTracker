package Modules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class City {

    private final String name;
    private final String key;
    private final Color color;
    private Set<String> likedCities =  new HashSet<String>();
    private final HashMap<Color, Integer> cubes;
    private int panicLevel;
    private boolean splashed;

    public City (String name, String key,  int color, int panicLevel, boolean splashed) {

        this.name = name;

        this.key = key;

        this.color = findColor(color);

        this.cubes = initializeCubes();

        this.panicLevel = panicLevel;

        this.splashed = splashed;
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

    // PUBLIC METHODS

    public void addCubes(int cubes) {
        addCubesEx(this.color, cubes);
    }

    public void addCubes(int color, int cubes) {
        addCubesEx(findColor(color), cubes);
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
        str.append("Splashed: " + this.splashed + "\n");

        return str.toString();
    }
}
