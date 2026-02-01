# Infos, Erkenntnisse und Ergebnisse
## Montag
### Projekt erstellen
```
mvn io.quarkus:quarkus-maven-plugin:create \
    -DplatformVersion=3.27.2 \
    -DprojectGroupId=org.acme \
    -DprojectArtifactId=quarkus-demo \
    -DclassName="org.acme.GreetingResource" \
    -Dpath="/hello"
```

### Anwendung starten
`./mvnw quarkus:dev`

### Musterlösungen (Github)
[github.com/tab269/quarkus-2026](https://github.com/tab269/quarkus-2026)

### OpenAPI (inkl. Swagger-UI) hinzugügen
`./mvnw quarkus:add-extension -Dextensions="io.quarkus:quarkus-smallrye-openapi"`

### Zalando RESTful API Guidelines
[opensource.zalando.com/restful-api-guidelines/](https://opensource.zalando.com/restful-api-guidelines/)

## Donnerstag + Freitag
### Reaktives Programmieren
- siehe Branch/Tag */reactive
- optimiert bzgl. Reaktivität innerhalb der JVM (verringert das Warten von Threads auf Ergebnisse)
- reactive-Variante der Extension verwenden
- Uni<T>- oder Multi<T>-Datentyp als Container für Ergebnis in der Zukunft verwenden

### Messaging
- entkoppelt Microservices und optimiert damit prozessübergreifend bzgl.
  - der Reaktivität und
  - der Nichtverfügbarkeit von Kommunikationspartnern
  des Gesamtsystems via fire-and-forget-Mechanismus
- funktioniert leider nicht, da wir den Embedded Artemis-Server (AMQP-Broker) nicht zum Laufen bekommen haben
- **Literatur**
  - [MicroProfile Reactive Messaging specification 3.0](https://microprofile.io/specifications/reactive-messaging/3-0/)
  - Quarkus-Guide: [Quarkus Messaging Extensions](https://quarkus.io/guides/messaging)
  - [Apache Artemis™](https://artemis.apache.org/components/artemis/documentation/latest/)

### Security
- SmallRye-JWT-Extension
- Mit `GenerateTestTokens` (im `src/test/java/`-Ordner) können Test-Tokens ausgestellt werden, die kopiert und manuell via _Authenticate_-Button auf der Swagger-UI angegeben werden können. Damit kann man sich für die REST-Endpunkte authorisieren lassen (oder eben nicht, wenn die entsprechende Rolle im Token fehlt).
- **Literatur**
  - Quarkus-Guide zu Role-Based Access Control (RBAC) mit JWT: [Using JWT RBAC](https://quarkus.io/guides/security-jwt)
  - Jones, M., Bradley, J., & Sakimura, N. (2015). RFC 7519: JSON Web Token (JWT). RFC Editor. [https://www.rfc-editor.org/info/rfc7519](https://www.rfc-editor.org/info/rfc7519)
  - [MicroProfile JWT 2.1](https://microprofile.io/specifications/jwt/2-1/)

### Health
- SmallRye-Health-Extension
- siehe `SimpleHealthCheck`

### Mit CDI-Events auf Events zum Hochfahren- und Herunterfahren der App reagieren
- siehe `AppLifecycleHandler`

### Lombok
- Boilerplate-Code in DTOs minimieren
- sparsam verwenden (ggf. Java-Records verwenden)
- problematisch in JPA-Entity-Klassen
- Maven-Dependency hinzufügen
- Annotation-Processing im Maven-Compiler-Plugin konfigurieren

### The Twelve-Factor App
- bewährte Leitlinien beim Entwickeln von Anwendungen im Zeitalter von _Software-as-a-Service_
- [12factor.net/](https://12factor.net/)
