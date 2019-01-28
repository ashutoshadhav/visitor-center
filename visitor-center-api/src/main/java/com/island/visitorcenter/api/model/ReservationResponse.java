package com.island.visitorcenter.api.model;

import org.springframework.stereotype.Component;

@Component
public class ReservationResponse {
    private Long reservationId;
    private String specialInstructions;

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
}
