package com.island.visitorcenter.api.model;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Component
public class ReservationRequest {
    @NotNull
    @Size(min = 2, max = 60, message = "Site name can be between 2 to 60 chars")
    private String firstName;
    @NotNull
    @Size(min = 2, max = 60, message = "Site name can be between 2 to 60 chars")
    private String lastName;
    @NotNull
    @Size(min = 4, max = 60, message = "Site name can be between 2 to 120 chars")
    private String email;
    @NotNull
    private Date reservationArrivalDate;
    @NotNull
    private Date reservationDepartureDate;

    @NotNull
    private long campsiteId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getReservationArrivalDate() {
        return reservationArrivalDate;
    }

    public void setReservationArrivalDate(Date reservationArrivalDate) {
        this.reservationArrivalDate = reservationArrivalDate;
    }

    public Date getReservationDepartureDate() {
        return reservationDepartureDate;
    }

    public void setReservationDepartureDate(Date reservationDepartureDate) {
        this.reservationDepartureDate = reservationDepartureDate;
    }

    public long getCampsiteId() {
        return campsiteId;
    }

    public void setCampsiteId(long campsiteId) {
        this.campsiteId = campsiteId;
    }
}
