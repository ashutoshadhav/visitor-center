package com.island.visitorcenter.reservations.service;

import com.island.visitorcenter.api.model.Inventory;
import com.island.visitorcenter.api.model.ReservationRequest;
import com.island.visitorcenter.reservations.util.Utils;

import java.util.Date;

public class ServiceValidations {

    public static String isValidRequest(ReservationRequest reservationRequest) {

        if (new Date().compareTo(reservationRequest.getReservationArrivalDate()) > 0) {
            return "reservationRequestStartDate can not be from the past";
        }

        if (reservationRequest.getReservationDepartureDate().
                compareTo(reservationRequest.getReservationArrivalDate()) < 0) {
            return "reservationRequestStartDate can not be after reservationRequestEndDate";
        }

        if (Utils.numberOfDaysBetweenDates(new Date(), reservationRequest.getReservationDepartureDate()) >
                Utils.maxDaysInCurrentMonth(
                        reservationRequest.getReservationArrivalDate())) {
            return "reservationRequestEndDate can not be after one month date from today";
        }

        if (Utils.numberOfDaysBetweenDates(new Date(), reservationRequest.getReservationArrivalDate()) < 1) {
            return "Reservation has to be made at least one day in advance";
        }
        if (Utils.numberOfDaysBetweenDates(reservationRequest.getReservationArrivalDate(),
                reservationRequest.getReservationDepartureDate()) > 3) {
            return "Reservation can not be made for more than 3 days";
        }

        return null;
    }


    public static String isValidRequest(Inventory inventory) {
        if (new Date().compareTo(inventory.getDate()) > 0) {
            return "inventory date can not be from the past";
        }
        return null;
    }
}
