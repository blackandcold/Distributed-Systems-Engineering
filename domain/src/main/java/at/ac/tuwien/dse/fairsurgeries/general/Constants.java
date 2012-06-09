package at.ac.tuwien.dse.fairsurgeries.general;

public abstract class Constants {
	public enum Queue {
		MatcherIn("fairsurgeries.queue.matcherIn"), NotifierIn("fairsurgeries.queue.notifierIn");
		
		private String name;
		
	    Queue(String name) {
	    	this.name = name;
	    }
	    
	    public String toString() {
	    	return this.name;
	    }
	}
}
