package at.ac.tuwien.dse.fairsurgeries.service;

import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.LogEntry.class })
public interface LogEntryService {
	
	public void log(String component, String message);
	
	public void clearLog();
	
}
