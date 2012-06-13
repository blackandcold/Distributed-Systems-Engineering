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
import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;
import at.ac.tuwien.dse.fairsurgeries.dto.OPSlotDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationFailedDTO;
import at.ac.tuwien.dse.fairsurgeries.dto.ReservationSuccessfulDTO;
import at.ac.tuwien.dse.fairsurgeries.general.Constants;
import at.ac.tuwien.dse.fairsurgeries.service.DoctorService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import at.ac.tuwien.dse.fairsurgeries.service.OPSlotService;
import at.ac.tuwien.dse.fairsurgeries.service.PatientService;

@Controller
public class ReservationController {

	@Autowired
	AmqpTemplate template;

	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private OPSlotService slotService;
	@Autowired
	private LogEntryService logEntryService;

	public void handleReservation(Object message) {
		logEntryService.log(Constants.Component.Matcher.toString(), "Starting handleReservation()");

		if (message instanceof ReservationDTO) {
			ReservationDTO reservationDTO = (ReservationDTO) message;
			OPSlot foundSlot = null;
			Patient patient = null;
			Doctor doctor = null;

			if (reservationDTO.isValid()) {
				patient = patientService.findPatient(reservationDTO.getPatient().getId());
				doctor = doctorService.findDoctor(reservationDTO.getDoctor().getId());

				if (patient != null && doctor != null) {
					double[] position = patient.getPosition();
					List<Hospital> hospitals = hospitalService.findHospitalsWithinRadius(position[0], position[1], reservationDTO.getRadius());
					Date dateFrom = reservationDTO.getDateFrom();
					Date dateTo = reservationDTO.getDateTo();

					// search for the first matching slot of a hospital (sorted
					// by distance)
					for (Hospital hospital : hospitals) {
						logEntryService.log(Constants.Component.Matcher.toString(), "Found hospital: " + hospital);
						List<OPSlot> slots = slotService.findByHospital(hospital);
						logEntryService.log(Constants.Component.Matcher.toString(), "Found slots: " + slots);

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

			logEntryService.log(Constants.Component.Matcher.toString(), "Found slot: " + foundSlot);

			if (foundSlot != null) {
				// Make reservation
				{
					SurgeryType surgeryType = reservationDTO.getSurgeryType();

					foundSlot.setSurgeryType(surgeryType);
					foundSlot.setPatient(patient);
					foundSlot.setDoctor(doctor);
					
					slotService.saveOPSlot(foundSlot);
				}

				// Post Notification
				{
					OPSlotDTO slotDTO = new OPSlotDTO(foundSlot.getId());
					ReservationSuccessfulDTO notification = new ReservationSuccessfulDTO(reservationDTO, slotDTO);

					logEntryService.log(Constants.Component.Matcher.toString(), "Reservation successful, sending notification");
					template.convertAndSend(Constants.Queue.NotifierIn.toString(), notification);
				}
			} else {
				ReservationFailedDTO notification = new ReservationFailedDTO(reservationDTO, "No Slot found.");

				logEntryService.log(Constants.Component.Matcher.toString(), "Reservation failed, sending notification");
				template.convertAndSend(Constants.Queue.NotifierIn.toString(), notification);
			}
		}

		logEntryService.log(Constants.Component.Matcher.toString(), "Finished handleReservation()");
	}
}
