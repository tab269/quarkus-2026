package org.acme.boundary;

import org.acme.domain.model.OrderEntity;

public class OrderMapper {

    public static OrderDTO toDTO(OrderEntity entity) {
        var builder = OrderDTO.builder()
                .orderId(entity.getOrderId());
        String name = entity.getCustomerName().trim();
        int lastSpace = name.lastIndexOf(' ');
        if (lastSpace < 0) {
            builder.customerFirstname(null);
            builder.customerLastname(name);
        } else {
            builder.customerFirstname(name.substring(0, lastSpace));
            builder.customerLastname(name.substring(lastSpace + 1));
        }
        builder.itemDescription(entity.getItemDescription());
        builder.amount(entity.getAmount());
        return builder.build();
    }

    public static OrderEntity toEntity(OrderDTO orderDTO) {
        var entity = new OrderEntity();
        entity.setOrderId(orderDTO.orderId);
        if (orderDTO.customerFirstname == null) {
            entity.setCustomerName(orderDTO.customerLastname);
        } else {
            entity.setCustomerName(orderDTO.customerFirstname + " " + orderDTO.customerLastname);
        }
        entity.setItemDescription(orderDTO.itemDescription);
        entity.setAmount(orderDTO.amount);
        return entity;
    }
}
