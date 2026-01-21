# Aufgaben
Implementiere einen (Micro-)Service _Orders_, der Bestellungen verwalten kann.
Gehe dabei inkrementell vor und beginne mit einer _REST-API_.
Aktuell braucht es noch keine Datenbank zum Persistieren.
Für den Moment reicht eine Map als (flüchtiger) Datenspeicher.

## RQ1: Bestellungen anzeigen
- _Beschreibung_: Der Anwender kann alle existierenden Bestellungen anzeigen.
- _Endpunkt_: `GET` auf `/orders`
- _Eingabedaten_: `keine`
- _Ausgabedaten_: Die bisher angelegten Bestellungen werden als (ggf. leere) Liste nacheinander ausgegeben.
  Eine Bestellung (`order`) hat die folgenden Felder:
  - `orderId`
  - `customerLastname`
  - `customerFirstname`
  - `itemDescription`
  - `amount`

## RQ2: Bestellung aufgeben
- _Beschreibung_: Der Anwender kann eine neue Bestellung anlegen.
- _Endpunkt_: `POST` auf `/orders`
- _Eingabedaten_: Felder einer Bestellung (ohne `orderId`)
- _Ausgabedaten_: Die angelegte Bestellung wird mit einer vom System generierten `orderId` quittiert.

## RQ3: Einzelne Bestellung anzeigen
- _Beschreibung_: Der Anwender kann eine bestimmte Bestellung anzeigen.
- _Endpunkt_: `GET` auf `/orders/{orderId}`
- _Eingabedaten_: `orderId` (im Pfad)
- _Ausgabedaten_: Die Bestellung, die durch die `orderId` identifiziert ist, wird angezeigt, oder eine Fehlermeldung,
  wenn sie nicht existiert.
