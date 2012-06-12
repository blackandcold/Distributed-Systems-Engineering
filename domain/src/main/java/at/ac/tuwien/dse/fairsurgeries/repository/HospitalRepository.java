package at.ac.tuwien.dse.fairsurgeries.repository;

import java.util.List;

import org.springframework.data.mongodb.core.geo.Box;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

import at.ac.tuwien.dse.fairsurgeries.domain.Hospital;

@RooMongoRepository(domainType = Hospital.class)
public interface HospitalRepository {

    List<at.ac.tuwien.dse.fairsurgeries.domain.Hospital> findAll();
    
    List<Hospital> findByPositionWithin(Circle c);
	List<Hospital> findByPositionWithin(Box b);
}
