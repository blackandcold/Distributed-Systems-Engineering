package at.ac.tuwien.dse.fairsurgeries.service;

import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.Patient.class })
public interface PatientService {
	/**
	 * Removes all patients from the database
	 */
	public void deleteAllPatients();
}
