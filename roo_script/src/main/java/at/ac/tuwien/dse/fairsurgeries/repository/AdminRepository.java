package at.ac.tuwien.dse.fairsurgeries.repository;

import at.ac.tuwien.dse.fairsurgeries.domain.Admin;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Admin.class)
public interface AdminRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.Admin> findAll();
}
