package at.ac.tuwien.dse.fairsurgeries.dto;

import java.io.Serializable;

public class ReservationFailedDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ReservationDTO reservation;
	private String reason;

	public ReservationFailedDTO(ReservationDTO reservation, String reason) {
		this.reservation = reservation;
		this.reason = reason;
	}

	public ReservationDTO getReservation() {
		return reservation;
	}
	
	public String getReason() {
		return reason;
	}
}
