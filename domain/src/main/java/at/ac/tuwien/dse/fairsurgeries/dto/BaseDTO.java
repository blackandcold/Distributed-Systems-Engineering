package at.ac.tuwien.dse.fairsurgeries.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	
	public BaseDTO(BigInteger id) {
		this.id = id;
	}
	
	public BigInteger getId() {
		return this.id;
	}
}
