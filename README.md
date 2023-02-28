# evameg


## Willkommen!

Das ist das **Git-Repository** für unser Projekt für das Modul **PME**, Programmierung mobiler Endgeräte.

**Wir** sind:
- Celina Ludwigs
- Mohammad Zidane
- Niklas Herzog

Wir nehmen alle am Studiengang Angewandte Informatik in der Vertiefungsrichtung "Medieninformatik" im 5. Semester teil.


## Projekt:
__EVAmeG__ ist eine __E-Government App__, die den deutschen Behördenalltag entschlacken will. Sie wird zunächst nur für Android entwickelt.


### Features:
- __Behördenmap__ mit Terminerstellfunktion
- __Formulare__ ausfüllen und abschicken (Backend wird lediglich simuliert)
- __Suchfunktion__ für Anliegen für die Formulare oder passende Ämter vorgeschlagen werden
- __Profile__ um Daten nur einmal eingeben zu müssen


### Technologien:
- __Kotlin__
- __Android Studio__
- Gitlab (this one right here)
- Open Street Maps and OSMDroid
- SQLite Open Helper
- ... and more


### Quick Start:
- "master" Branch clonen / herunterladen, in Android Studio öffnen, kompilieren und fertig!


## Zusätzliche Dokumentation

### Navigationsgraph der App:

- ![__Link__](https://git.ai.fh-erfurt.de/team-evameg/evameg/-/blob/master/DOC/graphics/evameg_Flowchart_Navigationgraph.drawio.png)


### Datenbank-ER-Modell der App:

- ![__Link__](https://git.ai.fh-erfurt.de/team-evameg/evameg/-/blob/master/DOC/graphics/evameg_ER-Modell.drawio.png)


### Bekannte Fehler:

- Sprachenänderungen werden nicht sofort übernommen
- das Einstellungsmenu aufzurufen nachdem man bereits einmal die Sprache geändert hat kann zu einem Crah führen
- Dark Mode und Feedback im Einstellungsmenü haben keinen Effekt
- das betätigen des "Zurück"-Knopfes führt den Nutzer immer zu den zuvor betätigten Aktionen
- wird man von der App auf ein Fragment verwiesen kann es sein, dass das BottomNavigationMenu noch bei der alten Option bleibt

