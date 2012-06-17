package at.ac.tuwien.dse.fairsurgeries.web;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import at.ac.tuwien.dse.fairsurgeries.domain.Reservation;
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.dto.OPSlotDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationFailedDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationSuccessfulDTO;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;

@Controller
public class ReservationController {

	@Autowired
	AmqpTemplate template;

	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private OPSlotService slotService;
	@Autowired
	private LogEntryService logEntryService;

	/**
	 * The method is executed when a Message is posted to the MatcherIn-Queue.
	 * It excepts the message to be an instance of Reservation and then tries
	 * to find a free slot in a hospital matching this reservation.
	 * @param message an instance of Reservation representing the desired slot reservation
	 */
	public void handleReservation(Object message) {
		logEntryService.log(Constants.Component.Matcher.toString(), "Starting handleReservation()");
		logEntryService.log(Constants.Component.Matcher.toString(), "Message: " + (message == null ? "null" : message));
		
		// To be sure that we don't get an endless loop, catch all Exceptions here
		try {
			if (message instanceof Reservation) {
				Reservation reservation = (Reservation) message;
				OPSlot foundSlot = null;
				Patient patient = null;
				Doctor doctor = null;

				logEntryService.log(Constants.Component.Matcher.toString(), "Message instanceof Reservation");
				
				if (reservation.isValid()) {
					patient = reservation.getPatient();
					doctor = reservation.getDoctor();
					
					logEntryService.log(Constants.Component.Matcher.toString(), "Message valid");

					if (patient != null && doctor != null) {
						// search all hospitals near the patient within the given radius
						double[] position = patient.getPosition();
						List<Hospital> hospitals = hospitalService.findHospitalsWithinRadius(position, reservation.getRadius());
						Date dateFrom = reservation.getDateFrom();
						Date dateTo = reservation.getDateTo();

						// search for the first matching slot of a hospital
						// (sorted by distance)
						for (Hospital hospital : hospitals) {
							List<OPSlot> slots = slotService.findAllFreeSlotsByHospital(hospital);
							
							logEntryService.log(Constants.Component.Matcher.toString(), "Found hospital: " + hospital);

							// check all free slots of all hospitals and use the first matching one
							for (OPSlot slot : slots) {
								if (slot.getDateFrom().after(dateFrom) && slot.getDateTo().before(dateTo)) {
									foundSlot = slot;
									break;
								}
							}

							if (foundSlot != null) {
								break;
							}
						}
					} else {
						logEntryService.log(Constants.Component.Matcher.toString(), "Didn't find patient or doctor.");
					}
				}

				if (foundSlot != null) {
					logEntryService.log(Constants.Component.Matcher.toString(), "Found slot: " + foundSlot);
					
					// Make reservation
					{
						SurgeryType surgeryType = reservation.getSurgeryType();

						foundSlot.setSurgeryType(surgeryType);
						foundSlot.setPatient(patient);
						foundSlot.setDoctor(doctor);

						slotService.updateOPSlot(foundSlot);
						logEntryService.log(Constants.Component.Matcher.toString(), "Updated slot: " + foundSlot);
					}

					// Post Notification
					{
						OPSlotDTO slotDTO = new OPSlotDTO(foundSlot.getId());
						ReservationSuccessfulDTO notification = new ReservationSuccessfulDTO(new ReservationDTO(reservation), slotDTO);

						logEntryService.log(Constants.Component.Matcher.toString(), "Reservation successful, sending notification");
						MessageController.sendMessage(template, Constants.Queue.NotifierIn, notification);
					}
				} else {
					ReservationFailedDTO notification = new ReservationFailedDTO(new ReservationDTO(reservation), "No Slot found.");

					logEntryService.log(Constants.Component.Matcher.toString(), "No slot found, reservation failed.");
					MessageController.sendMessage(template, Constants.Queue.NotifierIn, notification);
				}
			}

			logEntryService.log(Constants.Component.Matcher.toString(), "Finished handleReservation()");
		} catch (Exception ex) {
			logEntryService.log("Exception", ex.getMessage());
		}
	}
}
