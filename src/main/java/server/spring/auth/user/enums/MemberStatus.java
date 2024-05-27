package server.spring.auth.user.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MemberStatus {
  ACTIVATED("ACTIVATED", "활성화 또는 연결 상태"),
  DELETED("DELETED", "탈퇴 상태");

  @JsonValue
  private final String code;
  private final String label;

  MemberStatus(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public String getCode() {
    return this.code;
  }
}
