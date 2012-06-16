package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import org.springframework.data.mongodb.core.geo.Circle;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;


public class HospitalServiceImpl implements HospitalService {
	private static final double DEGREES_TO_KM = 110.;
	
	/**
	 * Finds all hospitals within the given radius around the given position
	 * @param position array with 2 entries, namely the latitude and longitude of the position
	 * @param radius the radius in km to search around the given position 
	 * @return list of hospitals
	 */
	public List<Hospital> findHospitalsWithinRadius(double[] position, double radius) {
		// check for validity of parameters
		if (position.length < 2 || radius < 0.) {
			return null;
		}
		
		// the given radius is in km, findByPositionWithin wants degrees 
		// because we store our positions as lat/lng pairs, so we need to convert
		double radiusInDegrees = radius/DEGREES_TO_KM;
		
		return hospitalRepository.findByPositionWithin(new Circle(position[0], position[1], radiusInDegrees));
	}
	
	/**
	 * Finds all hospitals within the given radius around the given position, defined by given latitude and longitude
	 * @param latitude the latitude of the position
	 * @param longitude the longitude of the position
	 * @param radius the radius in km to search around the given position 
	 * @return list of hospitals
	 */
	public List<Hospital> findHospitalsWithinRadius(double latitude, double longitude, double radius) {
		return this.findHospitalsWithinRadius(new double[]{latitude,longitude}, radius);
	}
}
