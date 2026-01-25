# Aktueller Stand
- Unsere Order-Klasse beschreibt eigentlich DTOs. Sie sind *nicht* die eigentlichen `Order`s sondern nur eine Sicht der
  Außenwelt darauf.
- Das, mit dem wir intern arbeiten, ist eine `OrderEntity`, die auch in der Datenbank persistiert wird. Objekte, die
  via REST (o.ä.) ins System kommen, sollten immer mit `XyzDTO` benannt werden.

# Aufgaben

## RQ10: Refaktoriere indem Du `Order` in `OrderDTO` umbenennst
- Benenne `Order` in `OrderDTO` um und nutze dazu möglichst die Refactor-Rename-Funktionalität Deiner IDE.
- Denke auch an Testklassen, andere Klassen und deren Methoden, die `OrderDTO` verwenden.
- Prüfe anschließend, ob noch alle Tests ohne Fehler auf _grün_ durchlaufen.
