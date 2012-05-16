package at.ac.tuwien.dse.fairsurgeries.repository;

import at.ac.tuwien.dse.fairsurgeries.domain.Notification;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Notification.class)
public interface NotificationRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.Notification> findAll();
}
