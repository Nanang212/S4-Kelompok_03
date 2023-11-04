package id.co.mii.serverapp.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ErrorUtils {
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> error(ResponseStatusException exception) {
    return ResponseEntity
            .status(exception.getStatus())
            .body(new ErrorResponse(exception.getStatus().getReasonPhrase(), exception.getReason(), exception.getStatus().value()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> error(AccessDeniedException exception) {
    return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse("Forbidden", exception.getMessage(), HttpStatus.FORBIDDEN.value()));
  }

  @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
  public ResponseEntity<ErrorResponse> error(HttpClientErrorException.Unauthorized exception) {
    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), exception.getMessage(), HttpStatus.UNAUTHORIZED.value()));
  }

//  @ExceptionHandler(BadCredentialsException.class)
//  public ResponseEntity<ErrorResponse> error(BadCredentialsException exception) {
//    return ResponseEntity
//            .status(HttpStatus.INTERNAL_SERVER_ERROR)
//            .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), exception.getMessage(), HttpStatus.UNAUTHORIZED.value()));
//  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> error(Exception exception) {
    exception.printStackTrace();
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class ErrorResponse {
    private String error;
    private String message;
    private Integer code;
  }
}
