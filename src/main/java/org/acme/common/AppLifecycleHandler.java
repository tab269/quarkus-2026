package org.acme.common;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class AppLifecycleHandler {

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Starting application");
    }

    void onStop(@Observes ShutdownEvent ev) {
        System.out.println("Stopping application");
    }
}
