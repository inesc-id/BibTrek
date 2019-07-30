package inesc_id.gsd.bibtrek.app.utils;

import java.util.ArrayList;
import org.json.JSONArray;

public class JSONUtils {
	
	public static ArrayList<Object> convertJSONArrayToArrayList(JSONArray jasonArray) {
		ArrayList<Object> arrayList = new ArrayList<Object>();     
		 
		if (jasonArray != null) { 
			for (int i=0; i<jasonArray.length(); i++) { 
				arrayList.add(jasonArray.get(i));
			} 
		}
		
		return arrayList;
	}
	
}
