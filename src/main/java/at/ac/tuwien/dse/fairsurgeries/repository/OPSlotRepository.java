package at.ac.tuwien.dse.fairsurgeries.repository;

import at.ac.tuwien.dse.fairsurgeries.domain.OPSlot;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = OPSlot.class)
public interface OPSlotRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.OPSlot> findAll();
}
