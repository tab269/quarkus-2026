package org.acme.domain.service;

import io.quarkus.arc.profile.UnlessBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.acme.domain.model.OrderEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class MapProducer {

    @Produces
    @UnlessBuildProfile("test")
    Map<UUID, OrderEntity> createOrderMap() {
        return new HashMap<>();
    }

//    @Produces
//    @IfBuildProfile("test")
//    Map<UUID, OrderEntity> createTestOrderMap() {
//        Map<UUID, OrderEntity> orderMap = new HashMap<>();
//        OrderEntity orderEntity = new OrderEntity(
//                UUID.randomUUID(), "Carola", null, 0);
//
//        return orderMap;
//
//    }
}
