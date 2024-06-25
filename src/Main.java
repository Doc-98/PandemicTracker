import java.io.IOException;

import static Modules.Controller.*;



public class Main {
    public static void main(String[] args) throws IOException {

        init();
        
        if(args.length != 0) {
            listener.load("test");
            easyPrintRisk();
        }
        else listener.listen();

   }
}