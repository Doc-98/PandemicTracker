package Modules;

import java.io.*;
import java.util.StringTokenizer;
import static Modules.Controller.cityList;

public class DataFetcher {

    // PRIVATE METHODS-----------------------------------------------------------------------

    // Adds one city to the list of all cities based on the info passed via tokens.
    private static void addCity(StringTokenizer info) {

        // get data
        StringTokenizer st = new StringTokenizer(info.nextToken(), "-");
        
        String key = info.nextToken();
        int color = Integer.parseInt(info.nextToken());
        int panic = Integer.parseInt(info.nextToken());

        // remove "-" characters from names
        StringBuilder name = new StringBuilder();
        while (st.hasMoreTokens()) {
            name.append(st.nextToken());
            if (st.hasMoreTokens()) name.append(" ");
        }

        // save data
        cityList.put(key, new City(name.toString(), key, color, panic));
    }

    // Adds the links of one city to its attribute "linked cities" via the passed tokens
    private static void addRoads(StringTokenizer info) {
        String key = info.nextToken();
        while (info.hasMoreTokens()) {
            cityList.get(key).getLikedCities().add(info.nextToken());
        }
    }

    // PUBLIC METHODS------------------------------------------------------------------------

    // Loads the initialization file to create the list of cities
    public static void loadInitFile(File f) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(f));

        String cursoreLinea = reader.readLine(); // burn header
        StringTokenizer cursoreToken = null;

        // reading the whole file
        for (cursoreLinea = reader.readLine(); cursoreLinea != null; cursoreLinea = reader.readLine()) {

            cursoreToken = new StringTokenizer(cursoreLinea, ",");

            if (cursoreToken.countTokens() == 4) {
                addCity(cursoreToken);
            } else throw new IllegalArgumentException(); // verify integrity of data (we need the correct number of arguments)
        }
    }

    public static void loadRoadsFile(File f) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(f));

        String cursoreLinea = reader.readLine(); //burn header
        StringTokenizer cursoreToken = null;

        // reading the whole file
        for (cursoreLinea = reader.readLine() ; cursoreLinea != null; cursoreLinea = reader.readLine()) {

            cursoreToken = new StringTokenizer(cursoreLinea, ",");
            //checkKey(cursoreToken); //DEBUGGING
            addRoads(cursoreToken);
        }
    }

    // DEBUG---------------------------------------------------------------------------------
    private static int counter = 0;
    static void checkKey(StringTokenizer info) {
        String key = info.nextToken();
        if (!cityList.containsKey(key)) {
            System.out.println(key);
        } else {
            counter = counter + 1;
            System.out.println("" + counter);
        }
    }
}
