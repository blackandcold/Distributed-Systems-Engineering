package at.ac.tuwien.dse.fairsurgeries.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

@RooJavaBean
@RooMongoEntity
@RooJson(deepSerialize = true)
public class OPSlot implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public OPSlot() {}
	
	public OPSlot(Hospital hospital, Date dateFrom, Date dateTo) {
		this.hospital = hospital;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MS")
    private Date dateFrom;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MS")
    private Date dateTo;

    @Enumerated
    private SurgeryType surgeryType;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @NotNull
    @ManyToOne
    private Hospital hospital;
    
    
    public OPSlotStatus getStatus() {
    	if (this.patient != null) {
    		return OPSlotStatus.RESERVED;
    	} else {
    		return OPSlotStatus.FREE;
    	}
    }
    
    @Override
    public String toString() {
    	DateFormat format = new SimpleDateFormat("dd.MM HH:mm");
    	
    	return (dateFrom != null ? format.format(dateFrom) : "NULL") + " - " + (dateTo != null ? format.format(dateTo) : "NULL") + " - " + hospital;
    }
}
