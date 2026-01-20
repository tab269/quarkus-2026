# Aufgaben
## Erste Schritte mit einem REST-Endpunkt
1. Starte die Quarkus-App auf der Kommandozeile bzw. Konsole `./mvnw compile quarkus:dev`.
2. Besuche mit einem Browser [http://localhost:8080/](http://localhost:8080/).
3. Passe den ausgegebenen Text in der `GreetingResource` an, sodass Dein Vorname darin vorkommt.
4. Ändere den Pfad des Endpunktes von `/hello` zu `/hello-name`. Überprüfe, dass unter `/hello` kein Dienst mehr
   erreichbar ist dafür unter `/hello-name`.

## DEV UI
Mache Dich mit der DEV UI der Anwendung vertraut, indem Du den `VISIT THE DEV UI`-Button klickst oder
   [http://localhost:8080/q/dev-ui/](http://localhost:8080/q/dev-ui/) besuchst.

## Richte Deine IDE ein (optional)
Wenn Du Dir das Leben leichter machen willst, richte Deine IDE ein, indem Du der Anleitung _Set up your IDE_ rechts auf
der Welcome-Seite folgst und die Quarkus Tools installierst.
