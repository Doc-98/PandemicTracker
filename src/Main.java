import java.io.File;
import java.io.IOException;

import static Modules.DataFetcher.*;


public class Main {
    public static void main(String[] args) throws IOException {

        File f = new File("C:\\Users\\dvinc\\IdeaProjects\\PandemicTracker\\src\\Resources\\cities.csv");

        if(f.exists()) {
            loadInitFile(f);
        }

        f = new File("C:\\Users\\dvinc\\IdeaProjects\\PandemicTracker\\src\\Resources\\roads.csv");

        if(f.exists()) {
            loadRoadsFile(f);
        }

        for(String c : cityList.keySet()){
            System.out.println(cityList.get(c));
        }
    }
}