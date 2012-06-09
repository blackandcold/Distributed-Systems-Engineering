package at.ac.tuwien.dse.fairsurgeries.dto;

import java.io.Serializable;
import java.util.Date;

public class ReservationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private DoctorDTO doctor;
	private PatientDTO patient;
	private HospitalDTO hospital;
	private Double radius;
	private Date dateFrom;
	private Date dateTo;
	
	public ReservationDTO(DoctorDTO doctor, PatientDTO patient, HospitalDTO hospital, Double radius, Date dateFrom, Date dateTo) {
		this.doctor = doctor;
		this.patient = patient;
		this.hospital = hospital;
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

	public HospitalDTO getHospital() {
		return hospital;
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
}
