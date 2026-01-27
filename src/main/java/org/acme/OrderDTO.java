package org.acme;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class OrderDTO {

    public UUID orderId;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 40, message = "")
    public String customerLastname;

    @Size(min = 2, max = 40, message = "")
    public String customerFirstname;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 40, message = "")
    public String itemDescription;

    @Min(1)
    @Max(100)
    public int amount;
}
