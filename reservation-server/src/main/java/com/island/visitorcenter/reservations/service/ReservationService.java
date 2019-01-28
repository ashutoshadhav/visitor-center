package com.island.visitorcenter.reservations.service;

import com.island.visitorcenter.api.model.ReservationRequest;
import com.island.visitorcenter.api.model.ReservationResponse;
import org.springframework.stereotype.Service;

/**
 * Service for managing reservations
 *  
 * @author aadhav
 *
 */
@Service
public interface ReservationService {
    ReservationResponse reserve (ReservationRequest reservationRequest);

    void cancel(Long reservationId);
}
