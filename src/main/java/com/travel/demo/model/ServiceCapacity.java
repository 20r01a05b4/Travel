package com.travel.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "servicecapacity")
public class ServiceCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "journey_date", nullable = false)
    private LocalDate journeyDate;

    @Column(name = "total_capacity", nullable = false)
    private int totalCapacity;

    @Column(name = "seats_confirmed", nullable = false)
    private int seatsConfirmed = 0;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private AgencyService service;

    // Constructors
    public ServiceCapacity() {
    }

    public ServiceCapacity(LocalDate journeyDate, int totalCapacity, int seatsConfirmed, AgencyService service) {
        this.journeyDate = journeyDate;
        this.totalCapacity = totalCapacity;
        this.seatsConfirmed = seatsConfirmed;
        this.service = service;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getSeatsConfirmed() {
        return seatsConfirmed;
    }

    public void setSeatsConfirmed(int seatsConfirmed) {
        this.seatsConfirmed = seatsConfirmed;
    }

    public AgencyService getService() {
        return service;
    }

    public void setService(AgencyService service) {
        this.service = service;
    }
}
