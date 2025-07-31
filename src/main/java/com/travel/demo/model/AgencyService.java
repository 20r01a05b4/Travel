package com.travel.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "services")
public class AgencyService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double cost;

    @Column(length = 20)
    private String source;

    @Column(length = 20)
    private String destination;

    private int days;
    private double discount;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "created_user", length = 100)
    private String createdUser;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "modified_user", length = 100)
    private String modifiedUser;

    @Column(length = 1000)
    private String images;

    @Column(name = "route_plan", length = 1000)
    private String routePlan;

    private Double rating;

    @Column(length = 1000)
    private String review;

    @Column(name = "service_provided_date")
    @Temporal(TemporalType.DATE)
    private Date serviceProvidedDate;

    private Integer capacity;



    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRoutePlan() {
        return routePlan;
    }

    public void setRoutePlan(String routePlan) {
        this.routePlan = routePlan;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getServiceProvidedDate() {
        return serviceProvidedDate;
    }

    public void setServiceProvidedDate(Date serviceProvidedDate) {
        this.serviceProvidedDate = serviceProvidedDate;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
