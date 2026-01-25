package org.acme;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService cut; // class under test

    private OrderRepository orderRepositoryMock;

    @Test
    void findAll_shouldCall_listAllOnRepository() {
        // Mock erzeugen
        orderRepositoryMock = mock(OrderRepository.class);

        // Verhalten definieren (hier optional, weil listAll() am Repository nicht von uns stammt
        // und wir nicht von einem Fehlverhalten in Hibernate/Panache ausgehen ;)
        // Wir wollen nicht die Hibernate/Panache testen sondern, ob der Service das Repository korrekt aufruft.
        // Mockito.when(orderRepositoryMock.listAll()).thenReturn(List.of());

        // Mock in CUT (Service) injizieren
        cut = new OrderService(orderRepositoryMock);

        // Ergebnis testen (Chicago-Style, hier optional weil wir von funktionierender Map ausgehen)
        // assertEquals(0, cut.findAll().size());
        // hier reicht der reine Aufruf
        cut.findAll();

        // überprüfen, dass die Methoden im Service aufgerufen wurden (London-Style)
        verify(orderRepositoryMock, times(1)).listAll();
    }
}
