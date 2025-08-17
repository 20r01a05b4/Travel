package com.travel.demo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.demo.model.*;
import com.travel.demo.repository.*;

@Service
public class TravelService {

    @Autowired
    private TravelRepository agencyRepository;



    public void saveAgencyWithServices(Agency agency, List<AgencyService> services) {
        for (AgencyService service : services) {
        	  service.setAgency(agency);
        }
        agency.setServices(services);
        agencyRepository.saveAgency(agency);
      for(AgencyService service:services) {
    	  ServiceCapacity servicecapacity=new ServiceCapacity();
	      	servicecapacity.setJourneyDate(service.getServiceProvidedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	      	servicecapacity.setService(service);
	      	servicecapacity.setTotalCapacity(service.getCapacity());
	      	servicecapacity.setSeatsConfirmed(0);
	      	agencyRepository.saveServiceCapacity(servicecapacity);
      }
        
        
    }

    public Map<Agency, List<AgencyService>> getAgenciesWithServices() {
        List<Agency> agencies = agencyRepository.findAllAgencies();
        Map<Agency, List<AgencyService>> agencyMap = new LinkedHashMap<>();
        for (Agency agency : agencies) {
            agencyMap.put(agency, agency.getServices());
        }
        return agencyMap;
    }

    public Agency getAgencyById(int id) {
    	Agency foundAgency=agencyRepository.getAgencyById(id);
    	if(foundAgency!=null) {
    		return agencyRepository.getAgencyById(id);
    	}
        return null;
    }

    public void saveService(AgencyService service) {
        agencyRepository.saveService(service);
    }
    
    public void updateAgency(Agency updatedAgency) {
        agencyRepository.saveAgency(updatedAgency);
    }

    public void deleteAgencyById(int id) {
        agencyRepository.deleteAgencyById(id);
    }

    public AgencyService getServiceById(Long id) {
    	AgencyService foundAgencyService=agencyRepository.getServiceById(id);
    	if(foundAgencyService!=null) {
    		return foundAgencyService;
    	}
        return foundAgencyService ;
    }

    public void updateService(AgencyService updatedService) {
    	agencyRepository.updateService(updatedService);

        // Find the existing ServiceCapacity record based on service ID and journeyDate
        ServiceCapacity sc = agencyRepository.findServiceCapacityByServiceAndDate(
            updatedService.getId(),
            updatedService.getServiceProvidedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        if (sc != null) {
            // Update total capacity if service capacity is changed
            sc.setTotalCapacity(updatedService.getCapacity());
            agencyRepository.updateServiceCapacity(sc);
        }
    	
    }

    public void deleteServiceById(Long id) {
    	agencyRepository.deleteServiceById(id);
    }

    public List<AgencyWithServicesDTO> getAgenciesWithSourceAndDestination(String source, String dest, String date, String sortBy) {
        List<AgencyService> services = agencyRepository.getAgenciesWithSourceAndDestination(source, dest,date);

        if ("rating".equalsIgnoreCase(sortBy)) {
            services.sort(Comparator.comparing(AgencyService::getRating).reversed());
        } else if ("cost".equalsIgnoreCase(sortBy)) {
            services.sort(Comparator.comparingDouble(AgencyService::getCost));
        } else if ("days".equalsIgnoreCase(sortBy)) {
            services.sort(Comparator.comparingInt(AgencyService::getDays));
        } else if ("discount".equalsIgnoreCase(sortBy)) {
            services.sort(Comparator.comparingDouble(AgencyService::getDiscount).reversed());
        }

        Map<Agency, List<AgencyService>> grouped = services.stream()
                .collect(Collectors.groupingBy(AgencyService::getAgency, LinkedHashMap::new, Collectors.toList()));

        return grouped.entrySet().stream()
                .map(entry -> new AgencyWithServicesDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

	
	public Booking createBookingWithPassengers(Booking booking) {
	   // Booking booking = new Booking();
//	    booking.setServiceId(request.getServiceId());
    booking.setBookingStatus(BookingStatus.PENDING_PAYMENT);
    booking.setBookingDate(LocalDate.now());
    booking.setJourneyStatus(JourneyStatus.NOT_STARTED);
    booking.setUserId(10);
    booking.setNumberOfPeople(booking.getPassengers().size());
	    

	    List<PassengerDetail> savedPassengers = new ArrayList<>();
	    for (PassengerDetail pd : booking.getPassengers()) {
	        PassengerDetail passenger = new PassengerDetail();
	        passenger.setName(pd.getName());
	        passenger.setAge(pd.getAge());
	        passenger.setGender(pd.getGender());
	        passenger.setAadhaarNumber(pd.getAadhaarNumber());
	        passenger.setBooking(booking);
	        savedPassengers.add(passenger);
	    }

	    booking.setPassengers(savedPassengers);
	     agencyRepository.saveBooking(booking); // will cascade save passengers
	     return booking;
	}

	public  Booking getBookingById(Long bookingId) {
		return agencyRepository.getBookingById(bookingId);
		
	}

	public  void savePayment(Payment payment) {
		// TODO Auto-generated method stub
		agencyRepository.savePayment(payment);
	}

	public Payment getPaymentByBookingId(Long bookingId) {
		// TODO Auto-generated method stub
	return 	agencyRepository.getPaymentById(bookingId);
	}

	public void updateCapacity(Long id, Date serviceProvidedDate, Integer numberOfPeople) {
		// TODO Auto-generated method stub
		agencyRepository.updateCapacity(id,serviceProvidedDate,numberOfPeople);
		
	}

	public Map<String, Integer> peopleFit(Long serviceId, LocalDate journeyDate, int count) {
		// TODO Auto-generated method stub
		Map<String, Integer> result=new HashMap<String, Integer>();
		int remain=agencyRepository.peopleFit(serviceId,journeyDate);
		if(count<=remain) {
			result.put("can", remain);
		}
		else {
			result.put("cannot", remain);
		}
			return result;
		
		
	}


}
