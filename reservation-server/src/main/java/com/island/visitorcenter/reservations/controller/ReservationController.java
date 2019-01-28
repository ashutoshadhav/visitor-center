package com.island.visitorcenter.reservations.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.island.visitorcenter.api.model.ReservationRequest;
import com.island.visitorcenter.api.model.ReservationResponse;
import com.island.visitorcenter.reservations.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Rest controller to manage reservation of campsite
 * 
 * @author aadhav
 */
@RestController
@RequestMapping(value = "api/v1/reservation")
public class ReservationController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReservationResponse> reserve(@RequestBody @Valid ReservationRequest reservationRequest)
            throws JsonProcessingException {
        logger.info("Received Reservation request for startDate={} with endDate={} for user email={}",
                reservationRequest.getReservationArrivalDate(),
                reservationRequest.getReservationDepartureDate(),
                reservationRequest.getEmail());

        ReservationResponse reservationResponse = reservationService.reserve(reservationRequest);
        logger.debug("Reservation request for startDate={} with endDate={} for user email={} Processed : " +
                        reservationRequest.getReservationArrivalDate(),
                reservationRequest.getReservationDepartureDate(),
                reservationRequest.getEmail());
        return new ResponseEntity(reservationResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{reservationId}",  method = RequestMethod.DELETE)
    public void findByDateBetween(@PathVariable("reservationId") @Valid  Long reservationId)
            throws JsonProcessingException {

        logger.info("Received Reservation Cancellation request for reservationId={}", reservationId);
        reservationService.cancel(reservationId);
    }

}
