package inesc_id.gsd.bibtrek.app.dblp.parsing;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import inesc_id.gsd.bibtrek.app.utils.JSONUtils;

public class AuthorsPublicationsJSONParser extends JSONParser {
	
	private static final String AUTHORS = "authors";
	private static final String AUTHOR = "author";
	private static final String DOI = "doi";
	private static final String EE = "ee";
	private static final String KEY = "key";
	private static final String TITLE = "title";
	private static final String TYPE = "type";
	private static final String URL = "url";
	private static final String VENUE = "venue";
	private static final String YEAR = "year";
	
	public AuthorsPublicationsJSONParser() {		
	}
	
	@Override
	Object[] displayInfo(JSONObject info, int counter) {
		Object[] tuple;
		String title, ee, venue, year, type, key, url, doi;
		ArrayList<Object> authors;
		
		authors = JSONUtils.convertJSONArrayToArrayList((JSONArray) ((JSONObject) info.get(AUTHORS)).get(AUTHOR));
		doi = (String) info.get(DOI);
		ee = (String) info.get(EE);
		key = (String) info.get(KEY);
		title = (String) info.get(TITLE);
		type = (String) info.get(TYPE);
		url = (String) info.get(URL);
		venue = (String) info.get(VENUE);
		year = (String) info.get(YEAR);		
		
		System.out.println("(" + (counter+1) + ")");
		System.out.print("- Title: ");
		System.out.println(title);
		System.out.print("- Authors: ");
		System.out.println("");
		this.displayAuthorsList(authors);
		System.out.print("- EE: ");
		System.out.println(ee);
		System.out.print("- Venue: ");
		System.out.println(venue);
		System.out.print("- Year: ");
		System.out.println(year);
		System.out.print("- Type: ");
		System.out.println(type);
		System.out.print("- URL: ");
		System.out.println(url);
		System.out.print("- DOI: ");
		System.out.println(doi);
		
		tuple = new Object[] {authors, title, ee, venue, year, type, key, url, doi};
		
		return tuple;
	}
	
	private void displayAuthorsList(ArrayList<Object> authors) {
		for(Object iter : authors) {
			System.out.print("  ");
			System.out.println("> " + iter.toString());
		}
	}
}
