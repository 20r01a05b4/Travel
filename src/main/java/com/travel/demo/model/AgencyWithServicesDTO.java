package com.travel.demo.model;

import java.util.List;

/**
 * DTO class to transfer agency and its services.
 */
public class AgencyWithServicesDTO {
    private int agencyId;
    private String agencyName;
    private String agencyEmail;
    private String agencyPhone;
    private String agencyAddress;
    private List<AgencyService> services;

    // Constructor
    public AgencyWithServicesDTO(Agency agency, List<AgencyService> services) {
        this.agencyId = agency.getAgencyId();
        this.agencyName = agency.getAgencyName();
        this.agencyEmail = agency.getAgencyEmail();
        this.agencyPhone = agency.getAgencyPhone();
        this.agencyAddress = agency.getAgencyAddress();
        this.services = services;
    }

    // Getters and Setters
    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyEmail() {
        return agencyEmail;
    }

    public void setAgencyEmail(String agencyEmail) {
        this.agencyEmail = agencyEmail;
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    public String getAgencyAddress() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress = agencyAddress;
    }

    public List<AgencyService> getServices() {
        return services;
    }

    public void setServices(List<AgencyService> services) {
        this.services = services;
    }
}
