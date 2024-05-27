package server.spring.auth.user.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum RoleType {
  GUEST("ROLE_GUEST", "손님"),
  USER("ROLE_USER", "회원");

  @JsonValue
  private final String code;
  private final String label;

  RoleType(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public String getCode() {
    return this.code;
  }

  private static final Map<String, RoleType> BY_CODE = new HashMap<>();

  static {
    for (RoleType r: values()) {
      BY_CODE.put(r.code, r);
    }
  }
  public static RoleType valueOfCode(String label) {
    return BY_CODE.get(label);
  }
}
