package inesc_id.gsd.bibtrek.app.dblp.parsing;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import inesc_id.gsd.bibtrek.app.utils.JSONUtils;

public class PublicationJSONParser extends JSONParser {
	
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
	
	public PublicationJSONParser() {
		
	}

	@Override
	Object[] displayInfo(JSONObject info, int counter) {
		Object object;
		Object[] tuple = null;
		String authorString, title, venue, year, type, key, url;
		ArrayList<Object> authorsArray;
		
		object = (Object) ((JSONObject) info.get(AUTHORS)).get(AUTHOR);
		
		key = (String) info.get(KEY);
		title = (String) info.get(TITLE);
		type = (String) info.get(TYPE);
		url = (String) info.get(URL);
		venue = (String) info.get(VENUE);
		year = (String) info.get(YEAR);		
		
		/*String keyString;
		Object obtainedObj;
		System.out.println("test");
		System.out.println("");
		Iterator<String> iter = info.keys();		
		while(iter.hasNext()) {
			keyString = iter.next();
			obtainedObj = info.get(keyString);
			if(obtainedObj instanceof JSONObject) {
				
			} else if(obtainedObj instanceof JSONArray) {
				
			} else if(obtainedObj instanceof String) {
				System.out.println((String) obtainedObj);
			}
		}
		System.out.println("");
		System.out.println("test"); */
		
		System.out.println("(" + (counter+1) + ")");
		System.out.print("- Title: ");
		System.out.println(title);
		System.out.print("- Authors: ");
		System.out.println("");
		
		if(object instanceof JSONArray) {
			authorsArray = JSONUtils.convertJSONArrayToArrayList((JSONArray) ((JSONObject) info.get(AUTHORS)).get(AUTHOR));
			this.displayAuthorsList(authorsArray);
			tuple = new Object[] {authorsArray, title, url, year, venue, type, key};
		} else if(object instanceof String) {
			authorString = (String) ((JSONObject) info.get(AUTHORS)).get(AUTHOR);
			System.out.println("- Author: ");
			System.out.println(authorString);
			authorsArray = new ArrayList<Object>();
			authorsArray.add(authorString);
			tuple = new Object[] {authorsArray, title, url, year, venue, type, key};
		}
		
		System.out.print("- Venue: ");
		System.out.println(venue);
		System.out.print("- Year: ");
		System.out.println(year);
		System.out.print("- Type: ");
		System.out.println(type);
		System.out.print("- URL: ");
		System.out.println(url);
		
		
		
		return tuple;
	}
	
	private void displayAuthorsList(ArrayList<Object> authors) {
		for(Object iter : authors) {
			System.out.print("  ");
			System.out.println("> " + iter.toString());
		}
	}
}
