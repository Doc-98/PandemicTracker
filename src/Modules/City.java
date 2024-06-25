package Modules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static Modules.Calculator.riskCities;
import static Modules.Calculator.splashedCities;

public class City {

    private final String name;
    private final String key;
    private final Color color;
    private final Set<String> likedCities =  new HashSet<String>();
    private final HashMap<Color, Integer> cubes;
    private int panicLevel;
    private float drawProbability;
    private float bottomDrawProbability;
    private boolean eradicated = false;
    private boolean quarantined = false;
    private boolean enclosed = false;

    // CONSTRUCTOR
    public City (String name, String key,  int color, int panicLevel) {

        this.name = name;

        this.key = key;

        this.color = findColor(color);

        this.cubes = initializeCubes();

        this.panicLevel = panicLevel;

        this.drawProbability = 0f;

        this.bottomDrawProbability = 0f;
    }

    // PUBLIC METHODS
    // Adds one cube of the corresponding color to the city. Gives alert in case of outbreak.
    public void addCubes() {
        addCubesEx(this.color, 1);
    }
    // Adds the specified number of cubes of the corresponding color to the city. Gives alert in case of outbreak and sets cubes to 3.
    public void addCubes(int cubes) {
        addCubesEx(this.color, cubes);
    }
    // Adds the specified number of cubes of the specified color to the city. Gives alert in case of outbreak and sets cubes to 3.
    public void addCubes(int cubes, int color) {
        addCubesEx(findColor(color), cubes);
    }

    public void removeCubes(int cubes) {
        removeCubesEx(this.color, cubes);
    }
    public void removeCubes(int cubes, int color) {
        removeCubesEx(findColor(color), cubes);
    }
    
    public void splashCity(Color color) {
        addCubes(1, findColor(color));
        if(color == this.color) {
            splashedCities.add(key);
        }
    }
    
    public void checkEradicate(int color) {
        if(this.color == findColor(color)) {
            eradicate();
        }
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

    public int getCubes(){
        return this.cubes.get(this.color);
    }

    public float getDrawProbability() {
        return this.drawProbability;
    }

    public float getBottomDrawProbability() {
        return this.bottomDrawProbability;
    }
    
    public boolean isEradicated() {
        return this.eradicated;
    }
    
    public boolean isQuarantined() {
        return this.quarantined;
    }
    
    public boolean isEnclosed() {
        return this.enclosed;
    }

    // SETTERS
    public void setPanicLevel(int panicLevel) {
        this.panicLevel = panicLevel;
    }

    public void setDrawProbability(float drawProbability) {
        this.drawProbability = drawProbability;
    }

    public void setBottomDrawProbability(float bottomDrawProbability) {
        this.bottomDrawProbability = bottomDrawProbability;
    }
    
    public void setEradicated(boolean eradicated) {
        this.eradicated = eradicated;
    }
    
    public void setQuarantined(boolean quarantined) {
        this.quarantined = quarantined;
    }
    
    public void setEnclosed(boolean enclosed) {
        this.enclosed = enclosed;
    }

    // PRIVATE METHODS
    private Color findColor(int color) {
        return switch (color) {
            case 1 -> Color.YELLOW;
            case 2 -> Color.RED;
            case 3 -> Color.BLUE;
            case 4 -> Color.BLACK;
            case 5 -> Color.GHOST;
            default -> throw new IllegalArgumentException();
        };
    }
    private int findColor(Color color) {
        return switch (color) {
            case YELLOW -> 1;
            case RED -> 2;
            case BLUE -> 3;
            case BLACK -> 4;
            case GHOST -> 5;
        };
    }

    private HashMap<Color, Integer> initializeCubes() {

        HashMap<Color, Integer> cubes = new HashMap<>();

        cubes.put(Color.YELLOW, 0);
        cubes.put(Color.RED, 0);
        cubes.put(Color.BLUE, 0);
        cubes.put(Color.BLACK, 0);
        cubes.put(Color.GHOST, 0);

        return cubes;
    }

    private void addCubesEx(Color color, int cubes) {
        
        if (isEradicated() || cubes < 1) return;
        if (isQuarantined()) {
            this.quarantined = false;
            return;
        }
        
        int newCubes = this.cubes.get(color) + cubes;
        
        if (newCubes >= 3) {
            maxOutCubes(color);
            if (newCubes > 3) outbreakAlert();
        } else
            this.cubes.replace(color, newCubes);
    }
    private void maxOutCubes(Color color) {
        this.cubes.replace(color, 3);
        if(!this.enclosed) riskCities.add(this.key);
    }

    private void removeCubesEx(Color color, int cubes) {
        
        int newCubes = this.cubes.get(color) - cubes;
        
        if(newCubes < 1) {
            clearCubes(color);
        } else
            this.cubes.replace(color, newCubes);
        
        for(int cubeCursor : this.cubes.values()) {
            if(cubeCursor == 3) return;
        }
        riskCities.remove(this.key);
    }
    private void clearCubes(Color color) {
        this.cubes.replace(color, 0);
    }

    private void outbreakAlert() {
        this.panicLevel++;
        if(this.enclosed) return;
        System.out.println("\n~~~~~~~~~~~~~~~~~~ FOCOLAIO DEL P.D. ~~~~~~~~~~~~~~~~~~");
        System.out.println("\t" + this.name.toUpperCase() + " -> Cities Affected: " + this.likedCities);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    
    private void eradicate() {
        this.eradicated = true;
    }

    // DEBUG
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.name + " (" + this.key + ")" + ":\n\t");
        str.append("Color: " + this.color + "\n\t");
        str.append("Links: " + this.likedCities + "\n\t");
        str.append("Panic Level: " + this.panicLevel + "\n\t");
        str.append("Draw Probability: " + this.drawProbability + "\n\t");
        str.append("Draw from Bottom Probability: " + this.bottomDrawProbability + "\n\t");
        str.append("Cubes:\n\t" + this.cubes + "\n" );

        return str.toString();
    }
    
    public String summary(){
        
        int namesColWidth = 30, cubesColWidth = 8, probColWidth = 21;
        StringBuilder str = new StringBuilder();
        
        str.append(String.format("%-" + namesColWidth + "s", this.name + ":") +
                String.format("%-" + cubesColWidth + "s", this.cubes.get(this.color)) +
                String.format("%-" + probColWidth + "s", "TP: " + String.format("%.2f", this.drawProbability * 100) + "%") +
                String.format("%-" + probColWidth + "s", "BP: " + String.format("%.2f", this.bottomDrawProbability * 100) + "%"));
        
        return str.toString();
    }
}
