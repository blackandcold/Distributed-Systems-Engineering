package at.ac.tuwien.dse.fairsurgeries.repository;

import java.util.List;

import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

import at.ac.tuwien.dse.fairsurgeries.domain.LogEntry;

@RooMongoRepository(domainType = LogEntry.class)
public interface LogEntryRepository {

	List<at.ac.tuwien.dse.fairsurgeries.domain.LogEntry> findAll();
	
}
