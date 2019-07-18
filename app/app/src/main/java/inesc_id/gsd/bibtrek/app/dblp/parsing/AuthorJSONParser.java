package inesc_id.gsd.bibtrek.app.dblp.parsing;

import org.json.JSONObject;

public class AuthorJSONParser extends JSONParser {
	
	private static final String AUTHOR = "author";
	private static final String URL = "url";
	
	public AuthorJSONParser() {		
	}

	@Override
	void displayInfo(JSONObject info) {
		System.out.print("Author: ");
		System.out.println(info.get(AUTHOR));
		System.out.print("URL: ");
		System.out.println(info.get(URL));		
	}
}
