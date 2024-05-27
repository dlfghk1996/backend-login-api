package server.spring.auth.common.exception;


import lombok.Data;
import server.spring.auth.common.enums.ResponseCode;

@Data
public class BizException extends RuntimeException{
    private int errorCode;
    private ResponseCode responseCode;

    public BizException() {
        super();
    }

    public BizException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BizException(ResponseCode responseCode) {
        super(responseCode.getLabel());
        this.errorCode = responseCode.getCode();
        this.responseCode = responseCode;
    }
}
