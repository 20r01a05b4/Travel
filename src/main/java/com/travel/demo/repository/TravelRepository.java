package com.travel.demo.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.travel.demo.model.Agency;
import com.travel.demo.model.AgencyService;
import com.travel.demo.model.Booking;
import com.travel.demo.model.Payment;
import com.travel.demo.model.ServiceCapacity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class TravelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // === Agency Methods ===

    public void saveAgency(Agency agency) {
    	System.out.println("Only this saveAGency triggers");
    	
    	entityManager.persist(agency);
    }

    public Agency getAgencyById(int id) {
        return entityManager.find(Agency.class, id);
    }

    public List<Agency> findAllAgencies() {
        return entityManager.createQuery("from Agency", Agency.class).getResultList();
    }

    public void deleteAgencyById(int id) {
        Agency agency = getAgencyById(id);
        if (agency != null) {
            entityManager.remove(agency);
        }
    }

    // === AgencyService Methods ===
 
    public void saveService(AgencyService service) {
    	System.out.println("saveService also triggers");
        entityManager.persist(service);
    }

    public AgencyService getServiceById(Long id) {
        return entityManager.find(AgencyService.class, id);
    }

    public void updateService(AgencyService service) {
    	System.out.println("This one will trigger");
        entityManager.merge(service);
        
    }

    public void deleteServiceById(Long id) {
        AgencyService service = getServiceById(id);
        if (service != null) {
            entityManager.remove(service);
        }
    }

    public List<AgencyService> getAgenciesWithSourceAndDestination(String source, String destination, String date) {
        // Parse the input string (format: yyyy-MM-dd) to java.util.Date
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = sdf.parse(date);
            
            return entityManager.createQuery(
            	    "SELECT s FROM AgencyService s " +
            	    "JOIN ServiceCapacity sc ON sc.service = s " +
            	    "WHERE LOWER(s.source) = LOWER(:source) " +
            	    "AND LOWER(s.destination) = LOWER(:destination) " +
            	    "AND s.serviceProvidedDate = :date " +
            	    "AND sc.seatsConfirmed < sc.totalCapacity",
            	    AgencyService.class)
            	    .setParameter("source", source)
            	    .setParameter("destination", destination)
            	    .setParameter("date", parsedDate)
            	    .getResultList();


        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format. Please use yyyy-MM-dd", e);
        }
    }

	public Booking saveBooking(Booking booking) {
		// TODO Auto-generated method stub
		 entityManager.persist(booking);
		 return null;
		
	}

	public Booking getBookingById(Long bookingId) {
		// TODO Auto-generated method stub
		 return entityManager.find(Booking.class, bookingId);
		
	}

	public void savePayment(Payment payment) {
		// TODO Auto-generated method stub
		entityManager.persist(payment);
		
	}

	public Payment getPaymentById(Long bookingId) {
		 return entityManager.find(Payment.class, bookingId);
	}

	@Transactional
	public void updateCapacity(Long serviceId, Date serviceProvidedDate, Integer numberOfPeople) {
		LocalDate journeyDate = ((java.sql.Date) serviceProvidedDate).toLocalDate();

	    // Fetch the existing ServiceCapacity record
	    ServiceCapacity capacity = entityManager.createQuery(
	            "SELECT sc FROM ServiceCapacity sc " +
	            "WHERE sc.service.id = :serviceId AND sc.journeyDate = :journeyDate", 
	            ServiceCapacity.class)
	            .setParameter("serviceId", serviceId)
	            .setParameter("journeyDate", journeyDate)
	            .getSingleResult();

	    if (capacity != null) {
	        capacity.setSeatsConfirmed(capacity.getSeatsConfirmed() + numberOfPeople);
	        entityManager.merge(capacity); // optional; not needed if entity is managed
	    }
	}

	public int peopleFit(Long serviceId, LocalDate journeyDate) {
	    try {
	        Object[] result = (Object[]) entityManager.createQuery(
	            "SELECT sc.totalCapacity, sc.seatsConfirmed " +
	            "FROM ServiceCapacity sc " +
	            "WHERE sc.service.id = :serviceId AND sc.journeyDate = :journeyDate"
	        )
	        .setParameter("serviceId", serviceId)
	        .setParameter("journeyDate", journeyDate)
	        .getSingleResult();

	        int capacity = (int) result[0];
	        int confirmed = (int) result[1];

	        return capacity - confirmed;  // remaining seats
	    } catch (NoResultException e) {
	        // If no entry exists yet, assume all seats are available
	        return 0;
	    }
	}

	public void saveServiceCapacity(ServiceCapacity servicecapacity) {
		// TODO Auto-generated method stub
		entityManager.persist(servicecapacity);
		
	}

	public ServiceCapacity findServiceCapacityByServiceAndDate(Long serviceId, LocalDate journeyDate) {
	    try {
	        return entityManager.createQuery(
	            "SELECT sc FROM ServiceCapacity sc WHERE sc.service.id = :serviceId AND sc.journeyDate = :journeyDate",
	            ServiceCapacity.class
	        )
	        .setParameter("serviceId", serviceId)
	        .setParameter("journeyDate", journeyDate)
	        .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
   
	
	public void updateServiceCapacity(ServiceCapacity serviceCapacity) {
	    entityManager.merge(serviceCapacity);
	}


}
