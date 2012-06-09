package at.ac.tuwien.dse.fairsurgeries.dto;

import java.io.Serializable;
import java.util.Date;

import at.ac.tuwien.dse.fairsurgeries.domain.SurgeryType;

public class ReservationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private DoctorDTO doctor;
	private PatientDTO patient;
	private SurgeryType surgeryType;
	private Double radius;
	private Date dateFrom;
	private Date dateTo;
	
	public ReservationDTO(DoctorDTO doctor, PatientDTO patient, SurgeryType surgeryType, Double radius, Date dateFrom, Date dateTo) {
		this.doctor = doctor;
		this.patient = patient;
		this.surgeryType = surgeryType;
		this.radius = radius;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public DoctorDTO getDoctor() {
		return doctor;
	}

	public PatientDTO getPatient() {
		return patient;
	}
	
	public SurgeryType getSurgeryType() {
		return surgeryType;
	}

	public Double getRadius() {
		return radius;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}
	
	
	public boolean isValid() {
		return this.doctor != null && this.patient != null && this.surgeryType != null && 
			   this.radius > 0. && this.dateFrom != null && this.dateTo != null;
	}
}
