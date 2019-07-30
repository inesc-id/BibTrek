package inesc_id.gsd.bibtrek.app.dblp.parsing;

import org.json.JSONObject;

public class AuthorJSONParser extends JSONParser {
	
	private static final String AUTHOR = "author";
	private static final String URL = "url";
	
	public AuthorJSONParser() {		
	}

	@Override
	Object[] displayInfo(JSONObject info, int counter) {
		Object[] tuple;
		String author, url;
		
		author = (String) info.get(AUTHOR);
		url = (String) info.get(URL);
		
		
		System.out.println("(" + (counter+1) + ")");
		System.out.print("- Author: ");
		System.out.println(author);
		System.out.print("- URL: ");
		System.out.println(url);
		
		tuple = new Object[]{author, url};		
		
		return tuple;
	}
}
