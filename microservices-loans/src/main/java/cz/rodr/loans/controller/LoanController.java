package cz.rodr.loans.controller;

import cz.rodr.loans.constants.LoanConstants;
import cz.rodr.loans.dto.LoanContactInfoDto;
import cz.rodr.loans.dto.LoanDto;
import cz.rodr.loans.dto.ResponseDto;
import cz.rodr.loans.dto.ErrorResponseDto;
import cz.rodr.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(
        name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH and DELETE Loans"
)
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@AllArgsConstructor
@Validated
public class LoanController {

    private ILoanService loanService;

    public LoanController(ILoanService iLoanService) {
        this.loanService = iLoanService;
    }

    // reading properties method n. 1
    @Value("${build.version}")
    private String buildVersion;

    // reading properties method n. 2
    @Autowired
    private Environment environment;

    @Autowired
    private LoanContactInfoDto loanContactInfoDto;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new Loan in EazyBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(
            @Valid
            @RequestParam
            @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits.")
            String mobileNumber) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstants.STATUS_201,LoanConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Get loan details REST API",
            description = "REST API to get details of a Loan in EazyBank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> getLoanDetails(
            @Valid
            @RequestParam
            @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits.")
            String mobileNumber) {
        LoanDto loanDto = loanService.getLoanDetails(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanDto);
    }

    @Operation(
            summary = "Update loan REST API",
            description = "REST API to update a loan in EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoanDto loanDto) {
        boolean isUpdated = loanService.updateLoan(loanDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete loan REST API",
            description = "REST API to delete a loan in EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )}
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(
            @Valid
            @RequestParam
            @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits.")
            String mobileNumber) {
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_DELETE));
        }
    }

    // reading properties using @Value annotated field (from application.yml)
    @Operation(
            summary = "Get build information REST API",
            description = "REST API to get build information that is deployed into loans microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    // reading properties using Environment interface field (from local environment variables)
    @Operation(
            summary = "Get Java version REST API",
            description = "REST API to get Java version that is installed into loans microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    // reading properties using @ConfigurationProperties annotation (from application.yml + AccountsContactInfoDto class)
    @Operation(
            summary = "Get contact info REST API",
            description = "REST API to get contact on loans microservice developer"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @GetMapping("/contact-info")
    public ResponseEntity<Map<String, String>> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanContactInfoDto.contactDetails());
    }


}
