package at.ac.tuwien.dse.fairsurgeries.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.LogEntry;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.dto.OPSlotDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationFailedDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationSuccessfulDTO;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

import com.mongodb.DB;
import com.mongodb.Mongo;

@Controller
public class ReservationController {

	@Autowired
	AmqpTemplate template;
	
	@Autowired(required = false)
	MongoDbFactory mongoDbFactory;

	@Autowired(required = false)
	MongoTemplate mongoTemplate;

	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private LogEntryService logEntryService;

	public void handleReservation(Object message) {
		// delete old logentries
		for(LogEntry entry : logEntryService.findAllLogEntrys()) {
			logEntryService.deleteLogEntry(entry);
		}
		
		logEntryService.log(Constants.Component.Matcher.toString(), "Starting handleReservation()");
		
		try {
			String log = "MongoDB Collections: ";
			for(String collectionName : mongoTemplate.getCollectionNames()) {
				log += collectionName + ", ";
			}
			logEntryService.log(Constants.Component.Matcher.toString(), log);
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logEntryService.log(Constants.Component.Matcher.toString(), stacktrace);
		}
		
		
		if (message instanceof ReservationDTO) {
			ReservationDTO reservationDTO = (ReservationDTO) message;
			OPSlot foundSlot = null;

			if (reservationDTO.isValid()) {
				Patient patient = patientService.findPatient(reservationDTO.getPatient().getId());
				Doctor doctor = doctorService.findDoctor(reservationDTO.getDoctor().getId());

				if (patient != null && doctor != null) {
					List<Hospital> hospitals = hospitalService.findHospitalsWithinRadius(patient.getLatitude(), patient.getLongitude(), reservationDTO.getRadius());
					SurgeryType surgeryType = reservationDTO.getSurgeryType();
					Date dateFrom = reservationDTO.getDateFrom();
					Date dateTo = reservationDTO.getDateTo();

					// TODO: Do we need to find the "best" Slot?
					// this just searches for the first matching one
					for (Hospital hospital : hospitals) {
						Set<OPSlot> slots = hospital.getOpSlots();

						for (OPSlot slot : slots) {
							if (slot.getSurgeryType().equals(surgeryType) && slot.getDateFrom().after(dateFrom) && slot.getDateTo().before(dateTo)) {
								foundSlot = slot;
								break;
							}
						}

						if (foundSlot != null) {
							break;
						}
					}
				}
			}

			if (foundSlot != null) {
				OPSlotDTO slotDTO = new OPSlotDTO(foundSlot.getId());
				ReservationSuccessfulDTO notification = new ReservationSuccessfulDTO(reservationDTO, slotDTO);
				
				logEntryService.log(Constants.Component.Matcher.toString(), "Reservation successful, sending notification");

				template.convertAndSend(Constants.Queue.NotifierIn.toString(), notification);
			} else {
				ReservationFailedDTO notification = new ReservationFailedDTO(reservationDTO, "No Slot found.");
				
				logEntryService.log(Constants.Component.Matcher.toString(), "Reservation failed, sending notification");

				template.convertAndSend(Constants.Queue.NotifierIn.toString(), notification);
			}

		}
		logEntryService.log(Constants.Component.Matcher.toString(), "Finished handleReservation()");
	}
}
