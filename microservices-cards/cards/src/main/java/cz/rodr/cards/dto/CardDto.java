package cz.rodr.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Card",
        description = "Schema to hold card information"
)
public class CardDto {

    @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits.")
    @Schema(description = "10-digit mobile number registered with EazyBank card", example = "0123456789")
    private String mobileNumber;

    @NotEmpty
    @Pattern(regexp = "(^$|\\d{12})", message = "Card number must be 12 digits.")
    @Schema(description = "12-digit EazyBank card number", example = "012345678901")
    private String cardNumber;

    @Schema(description = "Type of EazyBank card")
    private String cardType;

    @Min(value = 0, message = "Limit must be between 0 and 100 000.")
    @Max(value = 100000, message = "Limit must be between 0 and 100 000.")
    @Schema(description = "Maximum amount available on the card")
    private Integer totalLimit;

    @Min(value = 0, message = "Amount must be between 0 and 100 000.")
    @Max(value = 100000, message = "Amount must be between 0 and 100 000.")
    @Schema(description = "Amount already used")
    private Integer amountUsed;

    @Min(value = 0, message = "Amount must be between 0 and 100 000.")
    @Max(value = 100000, message = "Amount must be between 0 and 100 000.")
    @Schema(description = "Current amount available on the card")
    private Integer availableAmount;
}
