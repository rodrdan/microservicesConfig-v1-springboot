package cz.rodr.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Error",
        description = "Schema to hold error information"
)
public class ErrorResponseDto {

    @Schema(
            description = "API path invoked by client"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error"
    )
    private String errorMessage;

    @Schema(
            description = "Time of the error"
    )
    private LocalDateTime errorTime;
}
