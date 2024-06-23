import java.io.IOException;

import static Modules.Controller.*;



public class Main {
    public static void main(String[] args) throws IOException {

        init();

//        start("bue kha stpt new was atl kin cai hcm");

//        listener.load("pipo");
//        easyPrintAll();
//        easyPrintRisk();
        
        listener.listen();

   }
}