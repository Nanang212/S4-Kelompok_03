package id.co.mii.clientapp.utils;

import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@AllArgsConstructor
public class RequestInterceptorUtil implements ClientHttpRequestInterceptor {

    private AuthenticationSessionUtil session;

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {
        if (session != null && session.authentication() != null) {
            // Pastikan session dan propertinya sudah diinisialisasi
            if (!request.getURI().getPath().equals("/api/v1/auth/login") && !request.getURI().getPath().equals("/api/v1/auth/registration")) {
                request
                        .getHeaders()
                        .add(
                                "Authorization",
                                "Basic " +
                                        BasicHeaderUtil.createBasicToken(
                                                session.authentication().getName(),
                                                session.authentication().getCredentials().toString()));
            }
        }
        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }
}
