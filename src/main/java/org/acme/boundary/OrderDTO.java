package org.acme.boundary;

import jakarta.validation.constraints.*;
import lombok.*;
import org.acme.common.Alphabetic;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder()
@NoArgsConstructor
@AllArgsConstructor
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
    @Alphabetic
    public String itemDescription;

    @Min(1)
    @Max(100)
    public int amount;
}
