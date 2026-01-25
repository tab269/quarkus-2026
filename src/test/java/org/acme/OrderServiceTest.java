package org.acme;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService cut; // class under test

    private Map<UUID, Order> ordersMapMock;

    @Test
    void findAll_shouldCall_valuesOnOrdersMap() {
        // Mock erzeugen
        ordersMapMock = mock(Map.class);

        // Verhalten definieren (hier optional, weil Map nicht von uns stammt
        // und wir nicht von einem Fehlverhalten in java.util ausgehen ;)
        // Wir wollen nicht die Map testen sondern, ob der Service die Map korrekt aufruft.
        // Mockito.when(ordersMapMock.values()).thenReturn(List.of());

        // Mock in CUT (Service) injizieren
        cut = new OrderService(ordersMapMock);

        // Ergebnis testen (Chicago-Style, hier optional weil wir von funktionierender Map ausgehen)
        // assertEquals(0, cut.findAll().size());
        // hier reicht der reine Aufruf
        cut.findAll();

        // überprüfen, dass die Methoden im Service aufgerufen wurden (London-Style)
        verify(ordersMapMock, times(1)).values();
    }
}
