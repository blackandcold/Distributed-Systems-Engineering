package at.ac.tuwien.dse.fairsurgeries.web;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

	public void handleReservation(Object message) {
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

				template.convertAndSend(Constants.Queue.NotifierIn.toString(), notification);
			} else {
				ReservationFailedDTO notification = new ReservationFailedDTO(reservationDTO, "No Slot found.");

				template.convertAndSend(Constants.Queue.NotifierIn.toString(), notification);
			}

		}
		/*
		 * PatientDTO patient = (PatientDTO)message; Hospital hospital = new
		 * Hospital(); hospital.setName(String.valueOf(patient.getPatientId()));
		 * hospitalService.saveHospital(hospital);
		 */

		// TODO ftw
	}
}
