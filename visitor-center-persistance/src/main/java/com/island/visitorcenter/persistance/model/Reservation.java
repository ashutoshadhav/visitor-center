package com.island.visitorcenter.persistance.model;

import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "reservation")
    @Lazy
    private Collection<Inventory> inventoryList = new ArrayList<Inventory>();

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "RESERVATION_DATE")
    private Date reservationDate;

    @Column(name = "STATUS")
    private String status;

    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Inventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(Collection<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
