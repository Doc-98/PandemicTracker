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
    
    public void listen_old() throws IOException {
        
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
                    printProbabilities();
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
                    if (!pdDraw(input)) {
                        printError("1");
                        continue;
                    }
                    printProbabilities();
                    break;
                case "pd":
                    System.out.println("Inserire il codice della città focolaio: ");
                    input = getInput();
                    if(!outbreak(input)) {
                        printError("1");
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
                case "quar":
                    System.out.println("Inserire il codice della città che si sta mettendo in quarantena:");
                    input = getInput();
                    if (!quarantine(input)){
                        printError("1");
                        continue;
                    }
                    printProbabilities();
                    break;
                case "encl":
                    System.out.println("Inserire il codice della città che si sta isolando");
                    input = getInput();
                    if (!enclose(input)){
                        printError("1");
                        continue;
                    }
                    break;
                case "prob":
                    printProbabilities();
                    break;
                case "deck":
                    printDeck();
                    break;
                case "view":
                    easyPrintAll();
                    break;
                case "viewr":
                    easyPrintRisk();
                    break;
                case "view-db":
                    printCities();
                    break;
                case "start":
                    System.out.println("Inserire il codice delle 9 città estratte in ordine:");
                    input = getInput();
                    if(!start(input)) {
                        printError("start");
                        continue;
                    }
                    break;
                case "load":
                    System.out.println("Inserire il nome del file da caricare:");
                    input = scanner.nextLine();
                    if (!load(input)) {
                        printError("f");
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
        
        File f = new File(fileName + ".txt");
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
    
    public void listen() throws IOException {
        while(!input.equals("exit")) {
            System.out.println("\n\nProssimo comando:");
            input = getInput();
            if(processCommand(input)) {
                System.out.println("\n*************************************************** COMANDO ESEGUITO ***************************************************");
            }
        }
        scanner.close();
        out.close();
    }
    
    private boolean processCommand(String command) throws IOException {
        
        String str = processMessage(command);
        
        if (str.equals("-")) return true;
        else if (str.equals("err")) {
            System.out.println("Comando non valido, riprova ->");
            return false;
        }
        
        System.out.println(str);
        
        input = getInput();
        
        str = executeCommand(command, input);
        if (str.equals("ok"))
            return true;
        printError(str);
        return false;
    }
    
    private String executeCommand(String command, String param) throws IOException {
        switch (command) {
            case "draw":
                if (!draw(param)) return "n";
                printProbabilities();
                return "ok";
            case "epid":
                if (!epidemic(param)) return "1";
                printProbabilities();
                return "ok";
            case "treat":
                if (!treat(param)) return "1";
                return "ok";
            case "draw pd":
                if (!pdDraw(param)) return "1";
                printProbabilities();
                return "ok";
            case "pd":
                if (!outbreak(param)) return "1";
                printProbabilities();
                return "ok";
            case "block":
                if (!roadBlocks(param)) return "n";
                return "ok";
            case "quar":
                if (!quarantine(param)) return "1";
                printProbabilities();
                return "ok";
            case "encl":
                if (!enclose(param)) return "1";
                return "ok";
            case "prob":
                printProbabilities();
                return "ok";
            case "deck":
                printDeck();
                return "ok";
            case "view":
                easyPrintAll();
                return "ok";
            case "viewr":
                easyPrintRisk();
                return "ok";
            case "view-db":
                printCities();
                return "ok";
            case "start":
                if (!start(param)) return "start";
                return "ok";
            case "load":
                if (!load(param)) return "f";
                printProbabilities();
                return "ok";
            case "abs":
                System.out.println(record.getAbsolutePath());
                return "ok";
            case "erad":
                if (!eradicate(param)) return "cc";
                return "ok";
            case "edit cubes":
                if (!editCubes(param)) return "1";
                return "ok";
            default:
                return command;
        }
    }
    
    private String processMessage(String input) {
        return switch (input) {
            case "draw" -> "Inserire il codice delle città pescate:";
            case "epid" -> "Inserire il codice della città estratta dal fondo:";
            case "treat" -> "Inserire il codice della città arginata e il numero di cubi rimossi:";
            case "draw pd" -> "Inserire il codice della città del colore coda pescata dal mazzo giocatore:";
            case "pd" -> "Inserire il codice della città focolaio:";
            case "block" -> "Inserire il codice della città dalla quale si sta bloccando, seguito dal codice delle città bloccate:";
            case "quar" -> "Inserire il codice della città che si sta mettendo in quarantena:";
            case "encl" -> "Inserire il codice della città che si sta isolando";
            case "start" -> "Inserire il codice delle 9 città estratte in ordine:";
            case "load" -> "Inserire il nome del file da caricare:";
            case "erad" -> "Inserire il codice colore della malattia che è stata debellata:";
            case "edit cubes" -> "Struttura comando -> \"±chiave_città\" \"num_cubi\" \"codice_colore\" :\n( + per aggiungere cubi, - per toglierli )";
            case "prob", "view-db", "deck", "view", "abs" -> "-";
            default -> "err";
        };
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
            case "start" -> System.out.println("Rilevati uno o più codici città errati o numero di codici città inseriti diverso da 9.\n");
            case "f" -> System.out.println("File inesistente.\n");
            default -> System.out.print(error);
            
        }
    }
}
