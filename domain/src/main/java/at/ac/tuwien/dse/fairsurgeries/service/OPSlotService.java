package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import at.ac.tuwien.dse.fairsurgeries.domain.Patient;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.OPSlot.class })
public interface OPSlotService {
	
	// this method only exists because we couldn't find a way to use
	// the built-in "query by example" functionality of MongoDB with
	// Spring Roo
	public List<OPSlot> findByExample(OPSlot slot);
	
	// those methods are shortcuts for findByExample
	// we only need those because --mappedBy doesn't work :(
	public List<OPSlot> findByPatient(Patient patient);
	public List<OPSlot> findByDoctor(Doctor doctor);
	public List<OPSlot> findByHospital(Hospital hospital);
	
	public List<OPSlot> findAllFreeSlotsByHospital(Hospital hospital);
	
}
