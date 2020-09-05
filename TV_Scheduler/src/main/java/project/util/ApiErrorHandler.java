package project.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ApiErrorHandler {

    ApiErrorHandler() {
    }

    public void validate(boolean predicate, HttpStatus httpStatus, String httpMessage) {
        if (!predicate) {
            throw new ResponseStatusException(httpStatus, httpMessage);
        }
    }

    public void throwError(HttpStatus httpStatus, String httpMessage, Exception exception) {
        throw new ResponseStatusException(httpStatus, httpMessage, exception);
    }

}
