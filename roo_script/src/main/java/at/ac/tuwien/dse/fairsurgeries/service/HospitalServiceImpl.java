package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.List;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;


public class HospitalServiceImpl implements HospitalService {

	@Override
	public List<Hospital> findHospitalsWithinRadius(Double latitude, Double longitude, Double radius) {
		List<Hospital> hospitals = this.findAllHospitals();
		
		// TODO: Use Geo search feature of MongoDB and only return hospital within radius of locaiton
		
		return hospitals;
	}
}
