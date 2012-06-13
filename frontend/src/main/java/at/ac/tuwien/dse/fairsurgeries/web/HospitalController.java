package at.ac.tuwien.dse.fairsurgeries.web;

import java.sql.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.Reservation;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.dto.DoctorDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.PatientDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationDTO;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

@RooWebJson(jsonObject = Hospital.class)
@RequestMapping("/hospitals")
@Controller
@RooWebScaffold(path = "hospitals", formBackingObject = Hospital.class)
public class HospitalController {

	@Autowired 
	AmqpTemplate amqpTemplate;
	
	@Autowired
	DoctorService doctorService;
	@Autowired
	PatientService patientService;
	@Autowired
	OPSlotService slotService;
	

	@RequestMapping(value = "/sendMessage", produces="text/html")
	public String publish(Model model) {
		Doctor doctor = doctorService.findAllDoctors().get(0);
		Patient patient = patientService.findAllPatients().get(0);
		
		// TODO: temporary because addSlot website doesn't work
		if (slotService.findAllOPSlots().isEmpty()) {
			Hospital hospital = hospitalService.findAllHospitals().get(0);
			OPSlot slot = new OPSlot();
			
			slot.setDateFrom(Date.valueOf("2012-04-12"));
			slot.setDateTo(Date.valueOf("2012-04-13"));
			slot.setHospital(hospital);
		
			slotService.saveOPSlot(slot);
		}
		
		// Sample code to post a reservation
		Reservation reservation = new Reservation();
		reservation.setDoctor(doctor);
		reservation.setPatient(patient);
		reservation.setSurgeryType(SurgeryType.Cardiology);
		reservation.setRadius(50.);
		reservation.setDateFrom(Date.valueOf("2012-01-01"));
		reservation.setDateTo(Date.valueOf("2012-12-31"));
		
		// send persistent message
		MessageController.sendMessage(amqpTemplate, Constants.Queue.MatcherIn, reservation);
		
        return "hospitals/message_test";
    }
}

