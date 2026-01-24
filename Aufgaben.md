# Aktueller Stand
## Was können wir aktuell:
- Orders erzeugen
- Orders anzeigen

## Was ist mit ungültigen Daten?
- `NULL`-, `EMPTY`- oder `BLANK`-Werten z.B.
  - `customerLastname = null`
  - `customerFirstname = ""`
  - `itemDescription = "     "`
- _out-of-bounds_-Werten z.B.
  - `amount = -5`
  - `amount = 3.4`

# Aufgaben
Baue Prüfungen ein, welche die übergebenen Daten vor Annahme auf Gültigkeit überprüfen.

## RQ4: Validität der übergebenen Daten prüfen
- `customerLastname`
  - darf nicht `null` sein
  - darf nicht "" (empty)
  - darf nicht "   " (blank) sein, d.h. nur aus Leerzeichen bestehen
  - muss aus mindestens zwei und maximal 40 Zeichen bestehen
- `customerFirstname`
  - darf `null`, "" (empty), "    " sein, wird dann intern aber als `null` gesetzt
  - wenn das Feld intern nicht `null` ist, muss es aus mindestens zwei und maximal 40 Zeichen bestehen
- `itemDescription`
  - wie `customerLastname`
- `amount`
  - muss positiv sein
  - und kleiner gleich 100

# Optionale Zusatzaufgabe

## RQ5: Validität von `itemDescription` einschränken
- `itemDescription` soll zusätzlich nur aus Groß- und Kleinbuchstaben bestehen dürfen
