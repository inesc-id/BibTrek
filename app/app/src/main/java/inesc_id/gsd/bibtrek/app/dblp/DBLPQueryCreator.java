package inesc_id.gsd.bibtrek.app.dblp;

import inesc_id.gsd.bibtrek.app.sanitizer.DBLPSanitizer;

public class DBLPQueryCreator {
	
	private final static String BASE_URL = "https://dblp.org/search";
	
	private final static String SEARCH_AUTHORS = "/author/api?q=";
	private final static String SEARCH_PUBLICATIONS = "/publ/api?q=";
	
	private final static String FORMAT_JSON = "&format=json";
	
	public DBLPQueryCreator() {		
	}
	
	public String searchAuthorByName(String authorName) {
		String query;
		DBLPSanitizer dblpSanitizer;
		
		dblpSanitizer = new DBLPSanitizer(authorName);
		authorName = dblpSanitizer.sanitize();
		
		query = BASE_URL + SEARCH_AUTHORS + authorName + FORMAT_JSON;
		
		return query;
	}
	
	public String searchForPublication(String publicationTitle) {
		String query;
		DBLPSanitizer dblpSanitizer;
		
		dblpSanitizer = new DBLPSanitizer(publicationTitle);
		publicationTitle = dblpSanitizer.sanitize();
		
		query = BASE_URL + SEARCH_PUBLICATIONS + publicationTitle + FORMAT_JSON;
		
		return query;
	}
	
	
}
