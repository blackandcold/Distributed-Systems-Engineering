package at.ac.tuwien.dse.fairsurgeries.repository;

import at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = PublicPerson.class)
public interface PublicPersonRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.PublicPerson> findAll();
}
