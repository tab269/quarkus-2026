package org.acme.boundary;

import org.acme.domain.model.OrderEntity;

public class OrderMapper {

    public static OrderDTO toDTO(OrderEntity entity) {
        var dto = new OrderDTO();
        dto.setOrderId(entity.getOrderId());
        String name = entity.getCustomerName().trim();
        int lastSpace = name.lastIndexOf(' ');
        if (lastSpace < 0) {
            dto.setCustomerFirstname(null);
            dto.setCustomerLastname(name);
        } else {
            dto.setCustomerFirstname(name.substring(0, lastSpace));
            dto.setCustomerLastname(name.substring(lastSpace + 1));
        }
        dto.setItemDescription(entity.getItemDescription());
        dto.setAmount(entity.getAmount());
        return dto;
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
