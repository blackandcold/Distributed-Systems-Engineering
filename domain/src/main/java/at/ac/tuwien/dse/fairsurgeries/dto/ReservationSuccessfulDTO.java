package at.ac.tuwien.dse.fairsurgeries.dto;

import java.io.Serializable;

public class ReservationSuccessfulDTO implements Serializable {
private static final long serialVersionUID = 1L;
	
	private ReservationDTO reservation;

	public ReservationSuccessfulDTO(ReservationDTO reservation) {
		this.reservation = reservation;
	}

	public ReservationDTO getReservation() {
		return reservation;
	}
}
