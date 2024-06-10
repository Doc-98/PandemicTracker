package Modules;

import java.util.Scanner;

import static Modules.Controller.*;

public class Listener {

    Scanner scanner = new Scanner(System.in);
    String input = "zzz";

    public void listen() {
        //TODO: GESTIRE CASI DI INPUT INVALIDO (QUI O NEL CONTROLLER)
        while(!input.equals("exit")) {
            System.out.println("\nInsert next command:");
            input = scanner.nextLine();
            switch(input) {
                case "draw":
                    System.out.println("Inserire il codice delle città pescate:");
                    input = scanner.nextLine();
                    draw(input);
                    break;
                case "epid":
                    System.out.println("Inserire il codice della città estratta dal fondo:");
                    input = scanner.nextLine();
                    epidemic(input);
                    break;
                case "arg":
                    System.out.println("Inserire il codice della città arginata e il numero di cubi rimossi:");
//                    System.out.println("(Nel caso in cui i cubi rimossi siano di un colore diverso da quello della città aggiungere codice colore alla fine)");
                    input = scanner.nextLine();
                    treat(input);
                    break;
                case "pd-draw":
                    System.out.println("Inserire il codice della città del colore coda pescata dal mazzo giocatore:");
                    input = scanner.nextLine();
                    add(input);
                    break;
                case "view-b":
                    printCities();
                    break;
                case "view-p":

                default:
                    System.out.println("invalid command");
            }
        }
        scanner.close();
    }
}
