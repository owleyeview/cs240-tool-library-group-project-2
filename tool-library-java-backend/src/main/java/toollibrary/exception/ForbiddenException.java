package toollibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Custom exception for when a user is forbidden from doing something
// (Such as deleting a tool they did not create)
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ForbiddenException() {
        super();
    }
}
