package Modules;

import java.io.*;
import java.util.Scanner;

import static Modules.Controller.*;

public class Listener {

    Scanner scanner;
    String input;
    
    File record;
    PrintWriter out;
    
    String saveName;
    
    public Listener() throws IOException {
        
        scanner = new Scanner(System.in);
        
        System.out.println("Specify save name:");
        saveName = scanner.nextLine();
        
        input = "zzz";
        
        record = new File(saveName);
        
        if (record.exists()) {
            System.out.println("--- TRIED TO OVERRIDE A SAVE ---");
            throw new IllegalArgumentException();
        }
        
        out = new PrintWriter(new FileWriter(record), true);
    }
    
    public void listen() throws IOException {
        //TODO: GESTIRE CASI DI INPUT INVALIDO (QUI O NEL CONTROLLER)
        while(!input.equals("exit")) {
            System.out.println("\n\nInsert next command:");
            input = getInput();
            
            switch(input) {
                case "start":
                    System.out.println("Inserire il codice delle città estratte:");
                    input = getInput();
                    start(input);
                    break;
                case "draw":
                    System.out.println("Inserire il codice delle città pescate:");
                    input = getInput();
                    draw(input);
                    break;
                case "epid":
                    System.out.println("Inserire il codice della città estratta dal fondo:");
                    input = getInput();
                    epidemic(input);
                    break;
                case "arg":
                    System.out.println("Inserire il codice della città arginata e il numero di cubi rimossi:");
//                    System.out.println("(Nel caso in cui i cubi rimossi siano di un colore diverso da quello della città aggiungere codice colore alla fine)");
                    input = getInput();
                    treat(input);
                    break;
                case "draw pd":
                    System.out.println("Inserire il codice della città del colore coda pescata dal mazzo giocatore:");
                    input = getInput();
                    contaminate(input);
                    break;
                case "pd":
                    System.out.println("Inserire il codice della città focolaio: ");
                    input = getInput();
                    outbreak(input);
                    break;
                case "prob":
                    printProbabilities();
                    break;
                case "deck":
                    printDeck();
                    break;
                case "view-b":
                    printCities();
                    break;
                case "load":
                    input = scanner.nextLine();
                    load(input);
                    break;
                case "abs":
                    System.out.println(record.getAbsolutePath());
                    break;
                default:
                    if(!input.equals("exit")) System.out.println("invalid command");
            }
            System.out.println("\n\n*****************************************************************************************************");
        }
        scanner.close();
        out.close();
    }
    
    private String getInput(){
        String str = scanner.nextLine();
        out.println(str);
        return str;
    }
    
    public void load(String fileName) throws IOException {
        
        File f = new File(fileName  + ".txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line = "zzz";
        
        while(!line.equals("exit")) {
            line = reader.readLine();
            
            switch(line) {
                case "draw" -> draw(reader.readLine());
                case "epid" -> epidemic(reader.readLine());
                case "arg" -> treat(reader.readLine());
                case "draw pd" -> contaminate(reader.readLine());
                case "pd" -> outbreak(reader.readLine());
            }
        }
    }
}
