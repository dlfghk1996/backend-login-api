package server.spring.auth.common.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import server.spring.auth.common.enums.ResponseCode;

/**
 * API 호출 응답 클래스
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
public class Response<T> {

  T data;

  int status;

  String message;

  public Response(T data) {
    this.data = data;
    this.status = ResponseCode.OK.getCode();
    this.message = ResponseCode.OK.getLabel();
  }

  public Response(ResponseCode responseCode) {
    this.status = responseCode.getCode();
    this.message = responseCode.getLabel();
  }

  public Response(T data, ResponseCode responseCode) {
    this.data = data;
    this.status = responseCode.getCode();
    this.message = responseCode.getLabel();
  }
}
