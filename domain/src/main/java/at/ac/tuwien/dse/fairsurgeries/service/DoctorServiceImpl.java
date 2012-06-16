package at.ac.tuwien.dse.fairsurgeries.service;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;


public class DoctorServiceImpl implements DoctorService {
	@Override
	public void deleteAllDoctors() {
		for (Doctor doctor : this.findAllDoctors()) {
			this.deleteDoctor(doctor);
		}
	}
}
