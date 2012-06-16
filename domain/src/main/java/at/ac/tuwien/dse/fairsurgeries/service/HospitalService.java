package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.Hospital.class })
public interface HospitalService {
	
	/**
	 * Performs a geo search for all hospitals that are within the given radius of the given position.
	 * @param position lat/lng pair position
	 * @param radius the search radius in km
	 * @return a list of hospitals within the given radius
	 */
	public List<Hospital> findHospitalsWithinRadius(double[] position, double radius);
	
	/**
	 * Performs a geo search for all hospitals that are within the given radius of the given lat/lng pair.
	 * @param latitude the latitude of the position
	 * @param longitude the longitude of the position
	 * @param radius the search radius in km
	 * @return a list of hospitals within the given radius
	 */
	public List<Hospital> findHospitalsWithinRadius(double latitude, double longitude, double radius);
	
}
