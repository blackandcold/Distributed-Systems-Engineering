package at.ac.tuwien.dse.fairsurgeries.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.dto.PatientDTO;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;

@Controller
public class ReservationController {
	
	@Autowired
	private HospitalService hospitalService;
	
	public void handleReservation(Object message) {
		PatientDTO patient = (PatientDTO)message;
		Hospital hospital = new Hospital();
		hospital.setName(String.valueOf(patient.getPatientId()));
		hospitalService.saveHospital(hospital);
		
		// TODO ftw
	}
}
