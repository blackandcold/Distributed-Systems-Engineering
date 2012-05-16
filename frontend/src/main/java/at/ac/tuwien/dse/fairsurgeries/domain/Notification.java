package at.ac.tuwien.dse.fairsurgeries.domain;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
public class Notification {

    @NotNull
    @Enumerated
    private NotificationReason reason;

    private String description;

    @ManyToOne
    private OPSlot opSlot;
}
