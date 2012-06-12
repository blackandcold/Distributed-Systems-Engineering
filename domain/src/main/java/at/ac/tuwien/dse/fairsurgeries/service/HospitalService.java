package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.Hospital.class })
public interface HospitalService {
	
	public List<Hospital> findHospitalsWithinDistance(double[] position, double radius);
	public List<Hospital> findHospitalsWithinRadius(double latitude, double longitude, double radius);
	
}
