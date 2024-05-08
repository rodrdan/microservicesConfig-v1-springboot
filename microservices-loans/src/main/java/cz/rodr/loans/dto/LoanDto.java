package cz.rodr.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(
        name = "Loan",
        description = "Schema to hold Loan information"
)
public class LoanDto {

    @Schema(
            description = "10-digit mobile number of EazyBank loan", example = "09876543210"
    )
    @NotEmpty(message = "Mobile number can not be null or empty.")
    @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits.")
    private String mobileNumber;

    @Schema(
            description = "12-digit loan number of EazyBank loan", example = "0987654321012"
    )
    @NotEmpty(message = "Loan number can not be null or empty.")
    @Pattern(regexp = "(^$|\\d{12})", message = "Loan number must be 12 digits.")
    private String loanNumber;

    @Schema(
            description = "Loan type of EazyBank loan", example = "Home loan"
    )
    @NotEmpty(message = "Loan type can not be null or empty.")
    private String loanType;

    @Schema(description = "Total amount of the loan")
    @Positive(message = "Total loan must be greater than zero.")
    private Integer totalLoan;

    @Schema(description = "Amount already paid back")
    @PositiveOrZero(message = "Amount paid must be zero or more.")
    private Integer amountPaid;

    @Schema(description = "Amount left to pay")
    @PositiveOrZero(message = "Outstanding amount must be zero or more.")
    private Integer outstandingAmount;
}
