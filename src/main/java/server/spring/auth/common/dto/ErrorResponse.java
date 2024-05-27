package server.spring.auth.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.spring.auth.common.enums.ResponseCode;

/**
 * Exception 발생 Response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    int status;
    String label;
    String message;

    public ErrorResponse(ResponseCode responseCode) {
        this.label = String.valueOf(responseCode);
        this.status = responseCode.getCode();
        this.message = responseCode.getLabel();
    }

    public ErrorResponse(ResponseCode responseCode, String customMessage) {
        this.label = String.valueOf(responseCode);
        this.status = responseCode.getCode();
        this.message = customMessage;
    }
}
