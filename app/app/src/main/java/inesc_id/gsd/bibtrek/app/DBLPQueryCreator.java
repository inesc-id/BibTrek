package inesc_id.gsd.bibtrek.app;

public class DBLPQueryCreator {
	
	private final static String BASE_URL = "https://dblp.org/search";
	
	private final static String SEARCH_AUTHORS = "/author/api?q=";
	private final static String SEARCH_PUBLICATIONS = "/publ/api?q=";
	private final static String AUTHOR = "author=";
	
	private final static String FORMAT_JSON = "&format=json";
	
	public DBLPQueryCreator() {		
	}
	
	public String searchAuthorByName(String authorName) {
		String query;
		
		query = BASE_URL + SEARCH_AUTHORS + authorName + FORMAT_JSON;
		
		return query;
	}
	
	public String searchAuthorsPublications(String authorName) {
		String query;
		
		query = BASE_URL + SEARCH_PUBLICATIONS + AUTHOR + authorName + FORMAT_JSON;
		
		return query;
	}
	
	public String searchForPublication(String publicationTitle) {
		String query;
		
		query = BASE_URL + SEARCH_PUBLICATIONS + publicationTitle + FORMAT_JSON;
		
		return query;
	}
}
