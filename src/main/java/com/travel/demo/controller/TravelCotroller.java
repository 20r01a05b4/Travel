package com.travel.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel.demo.model.Agency;
import com.travel.demo.model.AgencyService;
import com.travel.demo.model.AgencyWithServicesDTO;
import com.travel.demo.model.Booking;
import com.travel.demo.model.Payment;
import com.travel.demo.service.TravelService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TravelCotroller {
	
	
	@Autowired 
	TravelService Travelservice;
	
@GetMapping("/")
public String homepage(){
	return "newHome";
}

@GetMapping("/upload")
public String uploadAgencyDetails() {
	return "uploadagencydetails";
}

@PostMapping("/upload")
@CrossOrigin(origins = "*")
@ResponseBody
public ResponseEntity<Map<String, String>> uploadAgency(
		@RequestBody Agency agency
) {
	  System.out.println("Agency Name: " + agency.getAgencyName());
	  System.out.println("Total Services: " + agency.getServices().size());

    Travelservice.saveAgencyWithServices(agency, agency.getServices());
    Map<String, String> response = new HashMap<>();
    response.put("message", "success");
    return ResponseEntity.ok(response);
    
}


@GetMapping("/agencies")
public String getAllAgencies(Model model) {
    Map<Agency, List<AgencyService>> agencyMap = Travelservice.getAgenciesWithServices();
    model.addAttribute("agencyMap", agencyMap);
    return "displayagencies";
}
@PostMapping("/agency/update")
@ResponseBody
public ResponseEntity<String> updateAgency(@RequestBody Agency agencyData) {
    Agency agency = Travelservice.getAgencyById(agencyData.getAgencyId());
    if (agency != null) {
        agency.setAgencyName(agencyData.getAgencyName());
        agency.setAgencyEmail(agencyData.getAgencyEmail());     // ADD THIS
        agency.setAgencyPhone(agencyData.getAgencyPhone());     // ADD THIS
        agency.setAgencyAddress(agencyData.getAgencyAddress()); // ADD THIS
        agency.setDescription(agencyData.getDescription());     // ADD THIS

        Travelservice.updateAgency(agency);
        return ResponseEntity.ok("Updated");
    }
    return ResponseEntity.notFound().build();
}



@DeleteMapping("/agency/delete/{id}")
@ResponseBody
public ResponseEntity<String> deleteAgency(@PathVariable int id) {
    Travelservice.deleteAgencyById(id);
    return ResponseEntity.ok("Deleted");
}

@PostMapping("/service/update")
@ResponseBody
public ResponseEntity<String> updateService(@RequestBody AgencyService serviceData) {
    AgencyService service = Travelservice.getServiceById(serviceData.getId());
    if (service != null) {
        service.setSource(serviceData.getSource());
        
        service.setDestination(serviceData.getDestination());
        service.setDays(serviceData.getDays());
        service.setCost(serviceData.getCost());
        service.setDiscount(serviceData.getDiscount());
        service.setDescription(serviceData.getDescription());
        service.setCapacity(serviceData.getCapacity());
        service.setRating(serviceData.getRating());
        service.setRoutePlan(serviceData.getRoutePlan());
        service.setReview(serviceData.getReview());
        service.setModifiedDate(LocalDateTime.now());
        service.setImages(serviceData.getImages());
        service.setServiceProvidedDate(serviceData.getServiceProvidedDate());
        
        Travelservice.updateService(service);
        return ResponseEntity.ok("Updated");
    }
    return ResponseEntity.notFound().build();
}

@DeleteMapping("/service/delete/{id}")
@ResponseBody
public ResponseEntity<String> deleteService(@PathVariable Long id) {
    Travelservice.deleteServiceById(id);
    return ResponseEntity.ok("Deleted");
}

//@GetMapping("/getAgencies")
//@ResponseBody
//public List<AgencyWithServicesDTO>  getAllAgenciesBySourceAndDestination(Model model,
//                                                   @RequestParam String source,
//                                                   @RequestParam String destination) {
//return Travelservice.getAgenciesWithSourceAndDestination(source, destination);
//  
//  
//}


	@GetMapping("/home")
	public String homepage1(){
		return "newHome";
	}
	@GetMapping("/search")
	@ResponseBody
	public List<AgencyWithServicesDTO> agenciesFilter(@RequestParam String source,
	                                                  @RequestParam String destination,
	                                                  @RequestParam String journeyDate,
	                                                  @RequestParam String filter) {
	    System.out.println("Filter value: " + filter);
	    return Travelservice.getAgenciesWithSourceAndDestination(source, destination, journeyDate, filter);
	}
	@GetMapping("/book")
	public String showBookingPage(@RequestParam Long serviceId, Model model) {
		AgencyService service = Travelservice.getServiceById(serviceId);
	    model.addAttribute("service", service);
	    return "Booking";
	}
	
	@PostMapping("/submitBooking")
	public String handleBooking(@ModelAttribute Booking bookingRequest,
	                            HttpSession session,
	                            RedirectAttributes redirectAttributes) {
	    
//	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//	    // Check if user is not logged in
//	    boolean isNotLoggedIn = auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName());
//       System.out.println("user logged status "+isNotLoggedIn);
//	    if (isNotLoggedIn) {
//	        // Save the bookingRequest to session to restore after login
//	        session.setAttribute("pendingBooking", bookingRequest);
//
//	        // Redirect to login with an alert trigger
//	        return "redirect:/login?error=auth";
//	    }

	    // User is logged in, proceed with booking
	    Booking booking = Travelservice.createBookingWithPassengers(bookingRequest);
	    System.out.println("Booking id is " + booking.getId());

	    // Redirect to /payment with bookingId as query parameter
	    redirectAttributes.addAttribute("bookingId", booking.getId());
	    return "redirect:/payment";
	}

	//payment
	@GetMapping("/payment")
	public String showPaymentPage(@RequestParam Long bookingId, Model model) {
	    Booking booking = Travelservice.getBookingById(bookingId);
	    Payment payment = new Payment();
	    payment.setBooking(booking);
	    payment.setAmountPaid(booking.getOverallCost());
	    model.addAttribute("booking", booking);
	    model.addAttribute("payment", payment);
	    return "payment";
	}

	@PostMapping("/processPayment")
	public String processPayment(@RequestParam("bookingId") Long bookingId,
	                             @RequestParam("overallcost") Double overallCost,
	                             @ModelAttribute Payment payment,
	                             RedirectAttributes redirectAttributes,Model model) {
	    Booking booking = Travelservice.getBookingById(bookingId); // You must implement this
	    if (booking == null) {
	        redirectAttributes.addFlashAttribute("message", "Booking not found!");
	        return "redirect:/error";
	    }

	    payment.setBooking(booking);
	    payment.setAmountPaid(overallCost); // from form
	    payment.setPaymentDate(LocalDateTime.now());
	    payment.setPaymentStatus("Success");

	    Travelservice.savePayment(payment);
	    

	    redirectAttributes.addFlashAttribute("message", "Payment Successful!");
	   

	    redirectAttributes.addAttribute("bookingId", booking.getId());
	    return "redirect:/Thankyou";
	}
	
	@GetMapping("/Thankyou")
	public String getThankPage(@RequestParam("bookingId") Long bookingId, Model model) {
	    Booking booking = Travelservice.getBookingById(bookingId);
	     // optional
       System.out.println("The booking id "+bookingId);
	    AgencyService service = Travelservice.getServiceById(booking.getServiceId());
	    Agency agency = Travelservice.getAgencyById(service.getAgency().getAgencyId());
	    
	    Travelservice.updateCapacity(service.getId(),service.getServiceProvidedDate(),booking.getNumberOfPeople());

	    model.addAttribute("booking", booking);
	    model.addAttribute("service", service);
	    model.addAttribute("agency", agency);
	    model.addAttribute("payment",booking.getPayment());

	    return "Thankyou";
	}

	@GetMapping("/check-capacity")
	@ResponseBody
	public Map<String, Integer> checkCapacity(@RequestParam Long serviceId,
	                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate,
	                                         @RequestParam int count) {

	 return	Travelservice.peopleFit(serviceId,journeyDate,count);
	
	}
	
	@GetMapping("/navbar")
	public String navbar() {
		return "navbar";
	}
}
