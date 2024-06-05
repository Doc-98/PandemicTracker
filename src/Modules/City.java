package Modules;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class City {

    private final String name;
    private final Color color;
    private LinkedHashMap<String, City> likedCities;
    private final HashMap<Color, Integer> cubes;
    private int panicLevel;
    private boolean splashed;

    public City (String name, int color, int panicLevel, boolean splashed) {

        this.name = name;

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

    public Color getColor() {
        return this.color;
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
}
