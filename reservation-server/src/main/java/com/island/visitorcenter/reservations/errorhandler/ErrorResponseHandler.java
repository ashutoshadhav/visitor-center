package com.island.visitorcenter.reservations.errorhandler;

import com.island.visitorcenter.reservations.exception.ReservationValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.core.HttpHeaders;
import java.util.Date;

@ControllerAdvice
@RestController
public class ErrorResponseHandler extends ResponseEntityExceptionHandler {
    //@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorResponseDetails errorDetails = new ErrorResponseDetails(new Date(), "Validation Failed",
                ex.getBindingResult().toString());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ReservationValidationException.class)
    public final ResponseEntity<ErrorResponseDetails>
    handleValidationErrorsForReservationServices(ReservationValidationException ex, WebRequest request) {
        ErrorResponseDetails errorDetails = new ErrorResponseDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllOtherExceptions(Exception ex, WebRequest request) {
        ErrorResponseDetails errorDetails = new ErrorResponseDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
