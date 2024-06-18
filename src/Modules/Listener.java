package Modules;

import java.io.*;
import java.util.Scanner;

import static Modules.Controller.*;

@SuppressWarnings("t")
public class Listener {

    Scanner scanner;
    String input;
    
    File record;
    PrintWriter out;
    
    String saveName;
    
    public Listener() throws IOException {
        
        scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Specify save name:");
            saveName = scanner.nextLine();
            record = new File(saveName + ".txt");
            
            if (!record.exists()) {
                break;
            }
            System.out.println("\n--- TRIED TO OVERRIDE A SAVE ->\n--- TRY AGAIN ->\n");
        }
        
        input = "zzz";
        out = new PrintWriter(new FileWriter(record), true);
    }
    
    public void listen() throws IOException {
        
        while(!input.equals("exit")) {
            System.out.println("\n\nProssimo comando:");
            input = getInput();
            
            switch(input) {
                case "draw":
                    System.out.println("Inserire il codice delle città pescate:");
                    input = getInput();
                    if(!draw(input)) {
                        printError("n");
                        continue;
                    }
                    printProbabilities();
                    break;
                case "epid":
                    System.out.println("Inserire il codice della città estratta dal fondo:");
                    input = getInput();
                    if(!epidemic(input)) {
                        printError("1");
                        continue;
                    }
                    break;
                case "treat":
                    System.out.println("Inserire il codice della città arginata e il numero di cubi rimossi:");
//                    System.out.println("(Nel caso in cui i cubi rimossi siano di un colore diverso da quello della città aggiungere codice colore alla fine)");
                    input = getInput();
                    if(!treat(input)) {
                        printError("1");
                        continue;
                    }
                    break;
                case "draw pd":
                    System.out.println("Inserire il codice della città del colore coda pescata dal mazzo giocatore:");
                    input = getInput();
                    if (!splash(input)) {
                        printError("1");
                        continue;
                    }
                    printProbabilities();
                    break;
                case "pd":
                    System.out.println("Inserire il codice della città focolaio: ");
                    input = getInput();
                    if(!outbreak(input)) {
                        System.out.println("Rilevato codice città errato.\n");
                        continue;
                    }
                    printProbabilities();
                    break;
                case "block":
                    System.out.println("Inserire il codice della città dalla quale si sta bloccando, seguito dal codice delle città bloccate:");
                    input = getInput();
                    if(!roadBlocks(input)) {
                        printError("n");
                        continue;
                    }
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
                case "start":
                    System.out.println("Inserire il codice delle 9 città estratte in ordine:");
                    input = getInput();
                    if(!start(input)) {
                        printError("Rilevati uno o più codici città errati o numero di codici città inseriti diverso da 9.\n");
                        continue;
                    }
                    break;
                case "load":
                    input = scanner.nextLine();
                    if (!load(input)) {
                        printError("File inesistente.\n");
                        continue;
                    }
                    printProbabilities();
                    break;
                case "erad":
                    System.out.println("Inserire il codice colore della malattia che è stata debellata:");
                    input = getInput();
                    if (!eradicate(input)) {
                        printError("cc");
                        continue;
                    }
                    break;
                case "abs":
                    System.out.println(record.getAbsolutePath());
                    break;
                case "edit cubes":
                    System.out.println("Struttura comando -> \"±chiave_città\" \"num_cubi\" \"codice_colore\" :");
                    System.out.println("( + per aggiungere cubi, - per toglierli )");
                    input = getInput();
                    if(!editCubes(input)) {
                        printError("1");
                        continue;
                    }
                    break;
                default:
                    if(!input.equals("exit")) System.out.println("Comando non valido, riprova ->");
                    continue;
            }
            System.out.println("\n*************************************************** COMANDO ESEGUITO ***************************************************");
        }
        scanner.close();
        out.close();
    }
    
    public boolean load(String fileName) throws IOException {
        
        File f = new File(fileName  + ".txt");
        if(!f.exists()) return false;
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line = "zzz";
        
        while(!line.equals("exit")) {
            
            line = reader.readLine();
            if(line == null) break;
            
            switch(line) {
                case "start" -> start(reader.readLine());
                case "draw" -> draw(reader.readLine());
                case "epid" -> epidemic(reader.readLine());
                case "treat" -> treat(reader.readLine());
                case "draw pd" -> contaminate(reader.readLine());
                case "pd" -> outbreak(reader.readLine());
                case "block" -> roadBlocks(reader.readLine());
                case "erad" -> eradicate(reader.readLine());
                case "edit cubes" -> editCubes(reader.readLine());
            }
        }
        return true;
    }
    
    private String getInput(){
        String str = scanner.nextLine();
        out.println(str);
        return str;
    }
    
    private void printError(String error) {
        System.out.print("\n\tERRORE: ");
        
        switch (error) {
            case "1" -> System.out.print("Rilevato codice città errato.\n");
            case "n" -> System.out.print("Rilevato uno o più codici città errati.\n");
            case "cc" -> System.out.print("Rilevati codice città e/o codice colore errati.\n");
            default -> System.out.print(error);
            
        }
    }
}
