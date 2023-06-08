package toollibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Custom exception for when a user must be signed in but the token is invalid
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotSignedInException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public NotSignedInException() {
        super();
    }
}
