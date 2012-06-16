package at.ac.tuwien.dse.fairsurgeries.service;

import at.ac.tuwien.dse.fairsurgeries.domain.Patient;


public class PatientServiceImpl implements PatientService {
	@Override
	public void deleteAllPatients() {
		for (Patient patient : this.findAllPatients()) {
			this.deletePatient(patient);
		}
	}
}
