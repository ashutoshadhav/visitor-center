package com.island.visitorcenter.reservations.service.helper;

import com.island.visitorcenter.api.model.Inventory;
import com.island.visitorcenter.api.model.ReservationRequest;
import com.island.visitorcenter.api.model.ReservationResponse;
import com.island.visitorcenter.api.model.common.EntityConstants;
import com.island.visitorcenter.persistance.model.Campsite;
import com.island.visitorcenter.persistance.model.Reservation;
import com.island.visitorcenter.persistance.model.User;

import java.util.Date;

public class ServiceHelper {

    public static Reservation getReservationPersistanceModel(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(new Date());
        reservation.setStatus(EntityConstants.ReservationStatus.RESERVED.name());
        User user = new User();
        user.setEmail(reservationRequest.getEmail());
        reservation.setUser(user);
        return reservation;
    }

    public static ReservationResponse getReservationAPIResponse(Reservation reservation, Campsite campsite) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setReservationId(reservation.getId());
        reservationResponse.setSpecialInstructions(campsite.getSpecialInstructions());
        return reservationResponse;
    }

    public static User getUserFromReservationRequest(ReservationRequest reservationRequest) {
        User user = new User();
        user.setEmail(reservationRequest.getEmail());
        user.setFirstName(reservationRequest.getFirstName());
        user.setLastName(reservationRequest.getLastName());
        user.setStatus(EntityConstants.UserStatus.ACTIVE.name());
        return user;
    }

    public static Inventory getInventoryAPIModel(com.island.visitorcenter.persistance.model.Inventory inventory) {
        Inventory inventoryApi = new Inventory();
        inventoryApi.setId(inventory.getId());
        inventoryApi.setDate(inventory.getDate());
        inventoryApi.setCampsiteId(inventory.getCampsite().getId());
        inventoryApi.setStatus(inventory.getStatus());
        return inventoryApi;
    }
}
