# PandemicTracker
Sviluppata dal dr. Gianfranco Ricci.

## Cheat Sheet

<table>
  <tr>
    <th align="center">
      <img width="441" height="1">
        <p> 
          <font size = "5">
            CODICI COLORE
          </font>
        </p>
    </th>
    <th align="center">
      <img width="441" height="1">
        <p> 
          <font size = "5">
            CODICI CITTÀ
          </font>
        </p>
    </th>
  </tr>
  <tr>
    <td align="center">
        1 -> YELLOW <br> 2 -> RED <br> 3 -> BLUE <br> 4 -> BLACK <br> 5 -> GHOST
    </td>
    <td align="center">
I codici città coincidono tutti con le prime 3 lettere del nome della città.
<br><br> ECCEZIONI:
<br> Città di Ho Chi Min -> hcm
<br> Il Cairo -> cai
<br> San Francisco -> stf
<br> San Paolo -> stpa
<br> San Pietroburgo -> stpt
    </td>
    </tr>
</table>

> [!NOTE]
> I comandi contrasseggnati da un asterisco ("*") stampano lo stato delle probabilità dopo essere stati risolti.

## Comandi -> input

### start -> "key_1" "key_2" ... "key_9"
> Effettua la contaminazione ordinata delle 9 città "key_[1-9]" pescate durante il setup.

Prende come input un elenco di 9 chiavi città che va separato con spazi.

Estrae dal mazzo di contaminazione le carte pescate e aggiunge i cubi corrispondenti ad ognuna.

L'ordine di inserimento determina quanti cubi vengono assegnati.
- Le prime 3 ricevono 3 cubi.
- Le successive 3 ricevono 2 cubi.
- Le ultime 3 ricevono 1 cubo.

### *draw -> "key_1" "key_2" ... "key_n"
> Pesca le "n" carte dal mazzo contaminazione con chiavi "key_[1-n]" e ci aggiunge un cubo.

Prende come input un elenco di chiavi città che va separato con spazi.

Estrae dal mazzo di contaminazione le carte pescate e aggiunge un cubo ad ognuna.

### *epid -> "key"
> Pesca la carta epidemia con chiave "key", ripone gli scarti in cima e aggiunge i 3 cubi malattia.

Prende come input una chiave città.

Estrae dal fondo del mazzo contaminazione la carta pescata. <br> La aggiunge alla pila degli scarti, che poi viene riposto in cima al mazzo contaminazione. <br> Infine aggiunge 3 cubi alla città corrispondente alla carta pescata dal fondo. 

### treat -> "key" ("num_cubi" ("codice_colore"))
> Rimuove dalla città con chiave "key" un cubo (oppure "num_cubi") dello stesso colore della città (o di colore "codice_colori").

Prende come input obbligatorio una chiave città. <br> Senza ulteriori argomenti, rimuove un singolo cubo dello stesso colore della città.

Se viene aggiunto il secondo argomento nell'input, rimuove un numero di cubi pari a tale numero, sempre dello stesso colore della città.

Se viene aggiunto anche il terzo argomento, rimuove il numero specificato di cubi del colore specificato dalla città.

### *draw-pd -> "key"
> Aggiunge un cubo alla città "key" senza pescarla dal mazzo contaminazione e contrassegnandola come "splashata".

Prende come input una chiave città "key" del CODA pescata dal mazzo giocatore (Player Deck).

Risolve l'aggiunta di un cubo senza estrarre la carta dal mazzo contaminazione e aggiunge la città alla lista di quelle contaminate tramite splash.

(È tecnicamente possibile passare una chiave di una città qualsiasi, non necessariamente del CODA, anche se il comando è pensato per gestire il suddetto caso)

### *pd -> "key"
> Effettua un focolaio sulla città "key".

Prende come input una chiave città "key".

Applica e risolve il focolaio su quella città. Non gestisce focolai concatenati (che però genereranno comunque il loro alert nel caso dovessero avvenire).

### block -> "origin_key" "key_1" "key_2" ... "key_n"
> Applica un blocco stradale sui collegamenti fra "origin_key" e "key_[1-n]".

Prende come input un elenco di chiavi città che va separato con spazi.

Partendo dalla città "origin_key", se una città "key_[1-n]" è presente fra i suoi collegamenti, tale collegamento viene eliminato in entrambe le città.

Se una città "key_[1-n]" non è presente fra i collegamenti di "origin_key", viene ignorata.

### prob
> Stampa lo stato delle probabilità e le città a rischio focolaio.

### deck
> Stampa lo stato del mazzo contaminazione e della pila degli scarti.

### view-b
> Stampa l'intero elenco delle città, ognuna con tutte le sue informazioni.

### *load -> "file_path"
> Carica tutti i comandi presenti nel file salvataggio "file_path"

Prende come input il percorso del file da aprire.

Legge il file ed esegue tutti i comandi rilevanti (che alterano lo stato di gioco) presenti in esso.

### erad -> "codice_colore"
> Debella la malattia "codice_colore"

Prende come input il codice numerico "codice_colore".

Debella la malattia corrispondente a quel colore, cambiando lo stato del flag corrispondente in ogni città di quel colore.

Non verranno più aggiunti cubi di un colore corrispondente ad una malattia debellata.

### abs
> Ritorna il percorso del file di salvataggio attuale.

### edit cubes -> "±key" ("num_cubi" ("codice_colore"))
> Modifica in maniera diretta i cubi di una città, senza estrarre carte.

Funziona in maniera simile al comando "treat".

L'unica differenza sta nella parte iniziale. Attaccato al codice della città interessata, va aggiunto un "+" o un "-".

Il "+" indica che vogliamo aggiungere, il "-" che vogliamo rimuovere. <br> Il resto funziona allo stesso modo, compreso il comportamento opzionale degli altri parametri.

### exit
> Termina il programma.


<br><br><br><br>

powered by:
<p align="right"> <i>dr. Gianfranco Ricci™</i> </p>

![- dr. Gianfranco Ricci](https://github.com/Doc-98/PandemicTracker/assets/93143423/84c48e10-5808-4cf1-aa75-842de792f198)




