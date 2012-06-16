package at.ac.tuwien.dse.fairsurgeries.service;

import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.Doctor.class })
public interface DoctorService {
	/**
	 * Removes all doctors from the database
	 */
	public void deleteAllDoctors();
}
