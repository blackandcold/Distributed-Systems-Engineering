package at.ac.tuwien.dse.fairsurgeries.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
@RooJson(deepSerialize = true)
public class Patient {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private Set<OPSlot> opSlots = new HashSet<OPSlot>();

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfBirth;
}
