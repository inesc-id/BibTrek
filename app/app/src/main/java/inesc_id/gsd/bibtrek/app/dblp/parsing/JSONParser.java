package inesc_id.gsd.bibtrek.app.dblp.parsing;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import inesc_id.gsd.bibtrek.app.utils.JSONUtils;

public abstract class JSONParser {
		
	private static final String COMPLETIONS = "completions";
	private static final String HIT = "hit";
	private static final String HITS = "hits";
	private static final String INFO = "info";
	private static final String RESULT = "result";	
	private static final String TOTAL = "@total";
	
	private String rawString;
	
	public JSONParser() {		
	}
	
	public void setString(String rawString) {
		this.rawString = rawString;
	}
	
	private String getString() {
		return this.rawString;
	}
	
	public ArrayList<Object[]> parseString() {
		int completion;
		JSONObject result, hit, hits;
		ArrayList<Object[]> tupleArrayList; 				
		
		result = new JSONObject(this.rawString);
		hits = (JSONObject) result.get(RESULT);
		
		completion = checkCompletions(hits);
		if(completion>0) {
			hit = (JSONObject) hits.get(HITS);
			tupleArrayList = displayHits(hit);
		} else {
			//TODO display message
			tupleArrayList = new ArrayList();
		}		
		
		return tupleArrayList;
	}
	
	private ArrayList<Object[]> displayHits(JSONObject hit) {
		Object[] tuple;
		JSONArray hitArray;
		JSONObject data, info;
		ArrayList<Object[]> tupleArrayList; 
		
		tupleArrayList = new ArrayList();
		tuple = new Object[0];
		hitArray = (JSONArray) hit.get(HIT);
		for(int i = 0; i<hitArray.length(); i++) {
			data = (JSONObject) hitArray.get(i);
			info = (JSONObject) data.get(INFO);
			System.out.println("");
			tuple = displayInfo(info, i);
			tupleArrayList.add(tuple);
		}		

		return tupleArrayList;
	}
	
	abstract Object[] displayInfo(JSONObject info, int counter);
	
	private int checkCompletions(JSONObject hits) {
		int value;
		JSONObject completions;
				
		completions = (JSONObject) hits.get(COMPLETIONS);
		value = Integer.parseInt((String) completions.get(TOTAL));
		return value;
	}
}
