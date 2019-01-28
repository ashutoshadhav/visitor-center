package com.island.visitorcenter.reservations.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReservationValidationException extends RuntimeException {
    public ReservationValidationException(String exception) {
        super(exception);
    }

}
