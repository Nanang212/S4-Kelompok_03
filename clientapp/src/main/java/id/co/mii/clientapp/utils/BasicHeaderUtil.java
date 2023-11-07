package id.co.mii.clientapp.utils;

import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class BasicHeaderUtil {
    private AuthenticationSessionUtil session;

    public static String createBasicToken(String username, String password) {
    String auth = username + ":" + password;
    byte[] encodedAuth = Base64.encodeBase64(
        auth.getBytes(Charset.forName("US-ASCII")));
    return new String(encodedAuth);
  }

  public HttpHeaders createBasicHeader() {
    return new HttpHeaders() {
      {
        set(
            "Authorization",
            "Basic " +
                createBasicToken(
                    // session.authentication().getName(),
                    session.authentication().getPrincipal().toString(),
                    session.authentication().getCredentials().toString()));
      }
    };
  }
}
