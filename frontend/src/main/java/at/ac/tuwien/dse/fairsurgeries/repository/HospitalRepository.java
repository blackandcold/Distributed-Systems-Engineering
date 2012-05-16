package at.ac.tuwien.dse.fairsurgeries.repository;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Hospital.class)
public interface HospitalRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.Hospital> findAll();
}
