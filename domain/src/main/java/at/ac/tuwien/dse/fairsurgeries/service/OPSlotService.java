package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlotStatus;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.OPSlot.class })
public interface OPSlotService {
	
	// this method only exists because we couldn't find a way to use
	// the built-in "query by example" functionality of MongoDB with
	// Spring Roo
	/**
	 * Performs a findByExample-search for the given slot, ignoring the read-only property "status".
	 * @param slot the example slot to match
	 * @return a list of all slots matching the given criteria
	 */
	public List<OPSlot> findByExample(OPSlot slot);
	
	/**
	 * Performs a findByExample-search for the given slot
	 * @param slot the example slot to match
	 * @param status the status to match
	 * @return a list of all slots matching the given criteria
	 */
	public List<OPSlot> findByExample(OPSlot slot, OPSlotStatus status);
	
	// those methods are shortcuts for findByExample
	// we only need those because --mappedBy doesn't work :(
	
	/**
	 * Shortcut for findByExample with a set patient.
	 * @param patient the patient of the example-slot
	 * @return a list of all slots matching the given criteria
	 */
	public List<OPSlot> findByPatient(Patient patient);
	
	/**
	 * Shortcut for findByExample with a set doctor.
	 * @param doctor the doctor of the example-slot
	 * @return a list of all slots matching the given criteria
	 */
	public List<OPSlot> findByDoctor(Doctor doctor);
	
	/**
	 * Shortcut for findByExample with a set hospital.
	 * @param hospital the hospital of the example-slot
	 * @return a list of all slots matching the given criteria
	 */
	public List<OPSlot> findByHospital(Hospital hospital);
	
	/**
	 * Shortcut for findByExample with a set hospital and the status OPSlotStatus.Free
	 * @param hospital the hospital of the example-slot
	 * @return a list of all slots matching the given criteria
	 */
	public List<OPSlot> findAllFreeSlotsByHospital(Hospital hospital);
	
}
