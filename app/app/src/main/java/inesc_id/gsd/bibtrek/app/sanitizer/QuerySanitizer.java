package inesc_id.gsd.bibtrek.app.sanitizer;

public abstract class QuerySanitizer {
	
	private String query; 
	
	public QuerySanitizer() {		
	}
	
	public String sanitize() {
		String sanitizedQuery;
		
		sanitizedQuery = query.replaceAll(" ", "");
		
		return sanitizedQuery;
	}
	
}
