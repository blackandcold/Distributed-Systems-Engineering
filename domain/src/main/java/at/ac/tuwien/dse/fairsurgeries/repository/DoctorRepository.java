package at.ac.tuwien.dse.fairsurgeries.repository;

import at.ac.tuwien.dse.fairsurgeries.domain.Doctor;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Doctor.class)
public interface DoctorRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.Doctor> findAll();
}
