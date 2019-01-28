package com.island.visitorcenter.persistance.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "CAMPSITE")
public class Campsite {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 2, max = 60, message = "Site name can be between 2 to 60 chars")
    @Column(name = "NAME")
    private String name;
    @Column(name = "SPECIAL_INSTRUCTIONS")
    private String specialInstructions;
    @Column(name = "STATUS")
    private String status;

    @Column(name = "TYPE")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
