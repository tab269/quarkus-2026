package org.acme;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class Order {

    private UUID orderId;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 40)
    private String customerLastname;

    @Size(min = 2, max = 40)
    private String customerFirstname;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 40)
    private String itemDescription;

    @Min(1)
    @Max(100)
    private int amount;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerFirstname(String customerFirstname) {
        if (customerFirstname == null || customerFirstname.isBlank()) {
            this.customerFirstname = null;
        } else {
            this.customerFirstname = customerFirstname;
        }
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
