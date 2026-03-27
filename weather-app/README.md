Weather App (Java GUI)
📌 Panoramica del progetto

Questa applicazione Java permette all’utente di inserire il nome di una città e ottenere i dati meteo correnti utilizzando le API gratuite di Open-Meteo.

Rispetto alla versione iniziale da console, l’app è stata migliorata con una interfaccia grafica (GUI) e nuove funzionalità.

L’app:

Recupera le coordinate (latitudine e longitudine) tramite API di geocoding
Ottiene i dati meteo correnti
Mostra temperatura, descrizione e velocità del vento
Gestisce errori e input non validi
Salva la cronologia delle ricerche

Funzionalità:
Interfaccia grafica (GUI) con Swing
Inserimento città tramite input
Pulsante per avviare la ricerca
Temperatura in tempo reale
Velocità del vento
Descrizione del meteo
Segnalazione risultati approssimati

Gestione errori:
città non trovata
input vuoto
errori di rete/API

Salvataggio cronologia in history.txt
Colore dinamico della UI in base al meteo

Struttura del progetto
weather-app/
└── src/
    └── com/
        └── weatherapp/
            ├── Main.java
            ├── WeatherGUI.java
            ├── service/
            │   └── WeatherFetcher.java
            ├── api/
            │   └── OpenMeteoClient.java
            └── util/
                └── HistoryManager.java

Istruzioni di installazione
Requisiti:
Java JDK 11 o superiore
Visual Studio Code (consigliato)
Estensione Java per VS Code

Setup
Scarica o clona il progetto
Apri la cartella weather-app in VS Code
Verifica la struttura delle cartelle

Guida all’uso:
Metodo consigliato (GUI)
Apri il file WeatherGUI.java
Clicca su ▶️ Run
Inserisci il nome della città
Premi Cerca

Output di esempio
🌡 18.2°C | 💨 6.5 km/h | Sereno
Modalità test (console)

Nel file Main.java è ancora disponibile la modalità test:

boolean TEST_MODE = true;

Esegue automaticamente:

Città valide (Roma, Milano)
Input inesistenti
Errori di battitura

⚠️ Limitazioni:
Possibili ambiguità tra città con lo stesso nome (es. Roma → Romania)
Parsing JSON manuale (senza librerie esterne)
Interfaccia grafica semplice (Swing)

Miglioramenti futuri:
Filtrare risultati per paese (es. Italia)
Utilizzare librerie JSON (es. Gson)
Migliorare l’interfaccia grafica (stile moderno)
Aggiungere icone meteo
Salvare e visualizzare cronologia nell’app
Supporto multi-lingua
Versione web o mobile

Autore:
Progetto sviluppato a scopo didattico

API utilizzate:
Open-Meteo Geocoding API
Open-Meteo Weather API

Sito ufficiale: https://open-meteo.com/

Nota sull’uso dell’IA:

Durante lo sviluppo sono stati utilizzati strumenti di intelligenza artificiale per:

implementare l’interfaccia grafica
risolvere errori di codice
migliorare la struttura del progetto
ottimizzare la gestione delle API
