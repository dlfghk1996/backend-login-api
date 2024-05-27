package server.spring.auth.token.enums;

public enum ClaimsKey {
  USERID_KEY("userId", "pk"),
  ROLE_KEY("role", "role");

  private final String name;
  private final String label;

  ClaimsKey(String name, String label) {
    this.name = name;
    this.label = label;
  }

  public String getName() {
    return this.name();
  }

  public String getLabel() {
    return this.label;
  }
}
