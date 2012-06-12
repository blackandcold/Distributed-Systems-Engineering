package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import org.springframework.data.mongodb.core.geo.Circle;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;


public class HospitalServiceImpl implements HospitalService {
	private static final double DEGREES_TO_KM = 110.;
	
	public List<Hospital> findHospitalsWithinDistance(double[] position, double radius) {
		if (position.length < 2 || radius < 0.) {
			return null;
		}
		
		return hospitalRepository.findByPositionWithin(new Circle(position[0], position[1], radius/DEGREES_TO_KM));
	}
	
	public List<Hospital> findHospitalsWithinRadius(double latitude, double longitude, double radius) {
		return this.findHospitalsWithinDistance(new double[]{latitude,longitude}, radius);
	}
}
