package inesc_id.gsd.bibtrek.app.dblp.parsing;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {
	
	private static final String AUTHOR = "author";
	private static final String COMPLETIONS = "completions";
	private static final String HIT = "hit";
	private static final String HITS = "hits";
	private static final String INFO = "info";
	private static final String RESULT = "result";	
	private static final String TOTAL = "@total";
	private static final String URL = "url";
	
	private String rawString;
	
	public JSONParser() {		
	}
	
	public void setString(String rawString) {
		this.rawString = rawString;
	}
	
	private String getString() {
		return this.rawString;
	}
	
	public void parseString() {
		int completion;
		JSONObject result, hit, hits;
		
		result = new JSONObject(this.rawString);
		hits = (JSONObject) result.get(RESULT);
		
		completion = checkCompletions(hits);
		if(completion>0) {
			hit = (JSONObject) hits.get(HITS);
			displayHits(hit);
		} else {
			//TODO display message
		}		
	}
	
	private void displayHits(JSONObject hit) {
		JSONArray hitArray;
		JSONObject data, info; 
		
		hitArray = (JSONArray) hit.get(HIT);
		for(int i = 0; i<hitArray.length(); i++) {
			data = (JSONObject) hitArray.get(i);
			info = (JSONObject) data.get(INFO);
			//TODO Comportamento Individualizado
			displayAuthorInfo(info);
		}
	}
	
	private void displayAuthorInfo(JSONObject info) {
		System.out.print("Author: ");
		System.out.println(info.get(AUTHOR));
		System.out.print("URL: ");
		System.out.println(info.get(URL));
	}
	
	private int checkCompletions(JSONObject hits) {
		int value;
		JSONObject completions;
				
		completions = (JSONObject) hits.get(COMPLETIONS);
		value = Integer.parseInt((String) completions.get(TOTAL));
		return value;
	}
}
