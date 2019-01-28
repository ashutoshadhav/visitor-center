package com.island.visitorcenter.api.model;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Component
public class Inventory {

    private Long id;
    @NotNull
    private Long campsiteId;
    private String status;
    @NotNull
    private Date date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampsiteId() {
        return campsiteId;
    }

    public void setCampsiteId(Long campsiteId) {
        this.campsiteId = campsiteId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
