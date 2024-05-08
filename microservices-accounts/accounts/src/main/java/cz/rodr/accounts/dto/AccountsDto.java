package cz.rodr.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(
            description = "Account number of EazyBank account", example = "09876543210"
    )
    @NotEmpty(message = "Account number can not be empty or null.")
    @Pattern(regexp = "(^$|\\d{10})", message = "Account number must be 10 digits.")
    private Long accountNumber;

    @Schema(
            description = "Account type of EazyBank account", example = "Savings"
    )
    @NotEmpty(message = "Account type can not be empty or null.")
    private String accountType;

    @Schema(
            description = "EazyBank branch address", example = "4 Fake St., Notown, AZ"
    )
    @NotEmpty(message = "Branch address can not be empty or null.")
    private String branchAddress;
}
