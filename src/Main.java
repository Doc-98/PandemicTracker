import java.io.File;
import java.io.IOException;

import static Modules.Controller.*;
import static Modules.DataFetcher.*;


public class Main {
    public static void main(String[] args) throws IOException {

        init();

        start("bue kha stpt new was atl kin cai hcm");

        printCities();

        listener.listen();

   }
}