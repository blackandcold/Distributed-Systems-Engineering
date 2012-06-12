package at.ac.tuwien.dse.fairsurgeries.repository;

import java.util.List;

import org.springframework.data.mongodb.core.geo.Box;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

import at.ac.tuwien.dse.fairsurgeries.domain.Patient;

@RooMongoRepository(domainType = Patient.class)
public interface PatientRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.Patient> findAll();
    
    List<Patient> findByPositionWithin(Circle c);
	List<Patient> findByPositionWithin(Box b);
}
