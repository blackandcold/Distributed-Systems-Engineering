package at.ac.tuwien.dse.fairsurgeries.service;

import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { at.ac.tuwien.dse.fairsurgeries.domain.LogEntry.class })
public interface LogEntryService {
	
	/**
	 * Stores a LogEntry to the database
	 * @param component the component that logs this message
	 * @param message the message to log
	 */
	public void log(String component, String message);
	
	/**
	 * Removes all log entries from the database
	 */
	public void clearLog();
	
}
