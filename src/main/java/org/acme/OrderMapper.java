package org.acme;

public class OrderMapper {

    public static OrderEntity toEntity(OrderDTO dto) {
        var entity = new OrderEntity();
        entity.id = dto.getId();
        entity.setOrderId(dto.getOrderId());
        if (dto.getCustomerFirstname() == null) {
            entity.setCustomerName(dto.getCustomerLastname());
        } else {
            entity.setCustomerName(dto.getCustomerFirstname() + " " + dto.getCustomerLastname());
        }
        entity.setItemDescription(dto.getItemDescription());
        entity.setAmount(dto.getAmount());
        return entity;
    }

    public static OrderDTO toDTO(OrderEntity entity) {
        var dto = new OrderDTO();
        dto.setId(entity.id);
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
}
