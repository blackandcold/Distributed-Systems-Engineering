package at.ac.tuwien.dse.fairsurgeries.repository;

import at.ac.tuwien.dse.fairsurgeries.domain.Reservation;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Reservation.class)
public interface ReservationRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.Reservation> findAll();
}
