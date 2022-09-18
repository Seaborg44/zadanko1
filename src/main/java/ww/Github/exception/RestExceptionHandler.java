package ww.Github.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    protected ResponseEntity<Object> notFoundException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "No such user can be found";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, bodyOfResponse,
                httpHeaders, HttpStatus.NOT_FOUND, request);
    }
}
