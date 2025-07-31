package com.travel.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate bookingDate;
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate localDate) {
		this.bookingDate = localDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int i) {
		this.userId = i;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(Integer numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getOverallCost() {
		return overallCost;
	}

	public void setOverallCost(Double overallCost) {
		this.overallCost = overallCost;
	}

	public LocalDate getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(LocalDate journeyDate) {
		this.journeyDate = journeyDate;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus pendingPayment) {
		this.bookingStatus = pendingPayment;
	}

	public JourneyStatus getJourneyStatus() {
		return journeyStatus;
	}

	public void setJourneyStatus(JourneyStatus notStarted) {
		this.journeyStatus = notStarted;
	}

	public List<PassengerDetail> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerDetail> passengers) {
		this.passengers = passengers;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	private int userId;
    private Long serviceId;
    private Integer numberOfPeople;
    private Double cost;
    private Double discount;
    private Double overallCost;
    private LocalDate journeyDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", length = 50)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "journey_status", length = 50)
    private JourneyStatus journeyStatus;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassengerDetail> passengers;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    // Getters & Setters
}
