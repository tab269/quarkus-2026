# Infos und Erkenntnisse
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
[https://github.com/tab269/quarkus-2026](https://github.com/tab269/quarkus-2026)

### OpenAPI (inkl. Swagger-UI) hinzugügen
`./mvnw quarkus:add-extension -Dextensions="io.quarkus:quarkus-smallrye-openapi"`

### Zalando RESTful API Guidelines
[https://opensource.zalando.com/restful-api-guidelines/](https://opensource.zalando.com/restful-api-guidelines/)

