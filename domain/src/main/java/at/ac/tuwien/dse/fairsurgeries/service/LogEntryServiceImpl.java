package at.ac.tuwien.dse.fairsurgeries.service;

import java.util.Calendar;

import at.ac.tuwien.dse.fairsurgeries.domain.LogEntry;


public class LogEntryServiceImpl implements LogEntryService {

	public void log(String component, String message) {
		LogEntry entry = new LogEntry();
		entry.setComponent(component);
		entry.setMessage(message);
		entry.setExecutionTimestamp(Calendar.getInstance().getTime());
		
		this.saveLogEntry(entry);
	}
	public void clearLog() {
		for (LogEntry entry : this.findAllLogEntrys()) {
			this.deleteLogEntry(entry);
		}
	}
	
}
