package at.ac.tuwien.dse.fairsurgeries.dto;
import java.io.Serializable;

public class PatientDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int patientId;
	
	public PatientDTO(int patientId){
		this.setPatientId(patientId);
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
}
