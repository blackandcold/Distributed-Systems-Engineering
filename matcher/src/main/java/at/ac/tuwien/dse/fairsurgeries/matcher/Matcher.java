package at.ac.tuwien.dse.fairsurgeries.matcher;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalService;
import at.ac.tuwien.dse.fairsurgeries.service.HospitalServiceImpl;

public class Matcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Matcher matcher = new Matcher();
		matcher.testDB();
	}
	
	public void testDB() {
		Hospital hospital = new Hospital();
		hospital.setName("AKH Wien");
		
		HospitalService service = new HospitalServiceImpl();
		service.saveHospital(hospital);
	}

}
