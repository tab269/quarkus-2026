# Aktueller Stand
Die Ressource macht schon ganz schön viel: Sie
- nimmt Daten von außen entgegen,
- macht die Validierung und
- kümmert sich um eine adäquate Antwort.

Zusätzlich kommuniziert sie auch nach innen mit der Map, was später die Datenbank sein wird.
- Dazu erzeugt sie beim Erstellen auch eine eindeutige `orderId`.

- Kurz: sie führt (weitere) Business-Logik aus.

Um diese gänzlich unterschiedlichen Verantwortlichkeiten sauber zu trennen, sollte sich die Order-Ressource nur mit
ihren Kernaufgaben, der REST-Kommunikation, beschäftigen und andere Aufgaben an einen Service delegieren.

# Aufgaben

## RQ6: Führe einen `OrderService` ein
- mit den Methoden
  - `findAll()`
  - `findById(UUID)`
  - `persist(Order)`
- der die Map (später die Datenbankverbindung) enthält und verwendet.
- Verwende diesen `OrderService` in der Ressource.

## RQ7: Mache die Ressource RequestScoped
- und stelle fest, dass nichts mehr gespeichert wird: Daten sind nach jedem Request weg.
- Schaue Dir ggf. mit `@PostConstruct` und `@PreDestroy` (aus `jakarta.annotation`) den Lebenszyklus der Ressource an. 

## RQ8: Injiziere den `OrderService` via _CDI_
- statt ihn mit `new` zu erzeugen
- nutze dafür `@Inject`
- Was ist besser: _Constructor Injection_, _Field Injection_ oder _Setter Injection_?
    Diskutiert die Vor- und Nachteile.
- Warum lässt sich der `OrderService` nicht injizieren?
  - Weshalb kennt _CDI_ den `OrderService` nicht?
  - Was brauchen wir dafür?
- Stelle sicher, dass alle Tests weiterhin laufen.

## RQ9: Schreibe neue Tests für den `OrderService`
- einen (schnellen) _UnitTest_ (verwende dazu Mockito, um die Map zu mocken)
- einen vollständigeren `@QuarkusTest` (Integrationstest, der auch CDI testet)
