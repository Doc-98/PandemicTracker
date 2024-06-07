package Modules;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class DataFetcher {

    public static LinkedHashMap<String, City> cityList = new LinkedHashMap<>();
    private static int counter = 0;

    static void addCity(StringTokenizer info) {

        StringTokenizer st = new StringTokenizer(info.nextToken(), "-");
        String key = info.nextToken();
        int color = Integer.parseInt(info.nextToken());
        int panic = Integer.parseInt(info.nextToken());
        boolean splash = Boolean.parseBoolean(info.nextToken());

        StringBuilder name = new StringBuilder();
        while (st.hasMoreTokens()) {
            name.append(st.nextToken());
            if (st.hasMoreTokens()) name.append(" ");
        }

        cityList.put(key, new City(name.toString(), key, color, panic, splash));
    }

    static void addRoad(StringTokenizer info) {
        String key = info.nextToken();
        while (info.hasMoreTokens()) {
            cityList.get(key).getLikedCities().add(info.nextToken());
        }
    }

    static void checkKey(StringTokenizer info) {
        String key = info.nextToken();
        if (!cityList.containsKey(key)) {
            System.out.println(key);
        } else {
            counter = counter + 1;
            System.out.println("" + counter);
        }
    }

    public static void loadInitFile(File f) throws IOException {

        // Creiamo un Buffered Reader a cui passiamo il file da leggere.
        BufferedReader reader = new BufferedReader(new FileReader(f));

        // Creiamo un cursore linea.
        String cursoreLinea = reader.readLine(); //bruciamo riga header
        StringTokenizer cursoreToken = null;

        // Usiamo un ciclo per scorrere il file linea per linea fino alla fine.
        for ( ; ; ) {
            // Salviamo la prossima linea nel nostro cursore.
            cursoreLinea = reader.readLine();

            // Creiamo la nostra condizione di uscita.
            if (cursoreLinea == null) break;


            cursoreToken = new StringTokenizer(cursoreLinea, ",");
            if (cursoreToken.countTokens() == 5) {
                addCity(cursoreToken);
            } else throw new IllegalArgumentException();

        }
    }

    public static void loadRoadsFile(File f) throws IOException {

        // Creiamo un Buffered Reader a cui passiamo il file da leggere.
        BufferedReader reader = new BufferedReader(new FileReader(f));

        // Creiamo un cursore linea.
        String cursoreLinea = reader.readLine(); //bruciamo riga header
        StringTokenizer cursoreToken = null;

        // Usiamo un ciclo per scorrere il file linea per linea fino alla fine.
        for ( ; ; ) {
            // Salviamo la prossima linea nel nostro cursore.
            cursoreLinea = reader.readLine();

            // Creiamo la nostra condizione di uscita.
            if (cursoreLinea == null) break;

            cursoreToken = new StringTokenizer(cursoreLinea, ",");
            //checkKey(cursoreToken);
            addRoad(cursoreToken);
        }
    }
}
