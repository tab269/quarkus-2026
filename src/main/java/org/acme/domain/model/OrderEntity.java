package org.acme.domain.model;

import java.util.UUID;

public class OrderEntity {

    private UUID orderId;
    private String customerName;
    private String itemDescription;
    private int amount;

    public OrderEntity() {}

    public OrderEntity(UUID orderId,
                       String customerName,
                       String itemDescription,
                       int amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.itemDescription = itemDescription;
        this.amount = amount;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getAmount() {
        return amount;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
