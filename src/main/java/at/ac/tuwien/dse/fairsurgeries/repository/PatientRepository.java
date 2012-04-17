package at.ac.tuwien.dse.fairsurgeries.repository;

import at.ac.tuwien.dse.fairsurgeries.domain.Patient;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Patient.class)
public interface PatientRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.Patient> findAll();
}
