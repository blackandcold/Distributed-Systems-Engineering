package at.ac.tuwien.dse.fairsurgeries.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
@RooJson(deepSerialize = true)
public class Hospital {

    @NotNull
    private String name;
    
    @GeoSpatialIndexed
    private double[] position;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<OPSlot> opSlots = new HashSet<OPSlot>();
}
