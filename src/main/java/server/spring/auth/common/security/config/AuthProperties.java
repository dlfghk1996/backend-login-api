package server.spring.auth.common.security.config;

public class AuthProperties {
  public static final String AUTH_HEADER_NAME = "Authorization";
  public static final long ACCESS_TOKEN_EXPIRATION_TIME = 90 * 24 * 60 * 60 * 1000L; // 90일
  public static final long REFRESH_TOKEN_EXPIRATION_TIME = 90 * 24 * 60 * 60 * 1000L; // 90일

  public static final String KEY_PAIR_ALIAS = "mykey";
  public static final String KEY_PAIR_SECRET = "123456";
  public static final String KEY_PAIR_FILE_NAME = "jwtKey.jks";
}
