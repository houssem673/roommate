package roommate.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import roommate.application.custom_exception.BookingNotFoundException;
import roommate.application.custom_exception.WorkspaceNotFoundException;

@ControllerAdvice
public class ErrorHandlerAdvice {

    @ExceptionHandler(WorkspaceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleWorkspaceNotFoundException(WorkspaceNotFoundException exception) {
        return "ErrorHandling/WorkspaceNotFound";
    }
    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleWorkspaceNotFoundException(BookingNotFoundException exception) {
        return "ErrorHandling/BookingNotFound";
    }
}