package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.Calendar;

import at.ac.tuwien.dse.fairsurgeries.domain.LogEntry;


public class LogEntryServiceImpl implements LogEntryService {
	
	/**
	 * Saves a log entry with the given component name and message into MongoDB
	 * @param component the name of the component creating the log entry
	 * @param message the message to log 
	 */
	public void log(String component, String message) {
		LogEntry entry = new LogEntry();
		entry.setComponent(component);
		entry.setMessage(message);
		entry.setExecutionTimestamp(Calendar.getInstance().getTime());
		
		this.saveLogEntry(entry);
	}
	
	/**
	 * Deletes all log entries from MongoDB
	 */
	public void clearLog() {
		for (LogEntry entry : this.findAllLogEntrys()) {
			this.deleteLogEntry(entry);
		}
	}
	
}
