package server.spring.auth.common.enums;


import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;


public enum ResponseCode {

    OK(0, "success", SC_OK),

    /** ** [ 2000 ~ 2999 ] : INVALID_REQUEST *** */
    INVALID_REQUEST(2000, "잘 못 된 요청입니다.", 2000),

    // Token error


    /** ** [ 4000 ~ 4999 ] : Auth Error *** */
    ERROR_NO_ACCESS_DENIED(4000, "접근권한이 없습니다.", SC_FORBIDDEN),
    ERROR_AUTHENTICAION(4001, "인증정보가 없습니다.", SC_UNAUTHORIZED),

    ACCESS_TOKEN_NOT_FOUND(4002, "Access Token을 찾을 수 없습니다.", SC_OK),
    ACCESS_TOKEN_INVALID_REQUEST(4003, "잘못된 Access Token입니다.", SC_OK),
    TOKEN_EXPIRED(4004, "이미 만료된 토큰입니다.", 2101),
    ACCESS_TOKEN_MISMATCH_PATTERN(4005, "지원하지않는 토큰입니다.", SC_OK),


    /** ** [ 3000 ~ 3999 ] : MEMBER ERROR *** */
    MEMBER_INVALID_ACCOUNT(6001, "아이디 또는 비밀번호를 확인해 주세요.", SC_OK),
    MEMBER_STATUS_NOT_ACTIVE(6002, "사용할 수 없는 계정입니다.", SC_OK),


    ERROR(9000, "SERVER", SC_OK),
    ERROR_SQL(9002, "SQL ERROR", SC_OK);

    private final int code;
    private final String label;
    private final int httpStatusCode;

    ResponseCode(int code, String label, int httpStatusCode) {
        this.code = code;
        this.label = label;
        this.httpStatusCode = httpStatusCode;
    }

    public int getCode() {
        return this.code;
    }


    public String getLabel() {
        return this.label;
    }

    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }


}
