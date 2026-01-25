package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class OrderEntity extends PanacheEntity {

    // nicht notwendig, wenn von PanacheEntity abgeleitet
    // technische ID als Primary Key
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    // fachlicher Schlüssel
    @Column(nullable = false,  unique = true, updatable = false, columnDefinition = "CHAR(36)")
    private UUID orderId;

    @Column(length = 80)
    private String customerName;

    @Column(length = 40)
    private String itemDescription;

    private int amount;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
