package inesc_id.gsd.bibtrek.app.dblp.exceptions;

import inesc_id.gsd.bibtrek.app.sanitizer.QuerySanitizer;

public class DBLPSanitizer extends QuerySanitizer {
	
	private String name;
		
	public DBLPSanitizer(String name) {
		this.name = name;
	}
	
	@Override
	public String sanitize() {
		String sanitizedName;
		StringBuilder stringBuilder = new StringBuilder();	
		boolean first = true;	
		boolean space = false;
		char name_character;
		
		for(int i = 0; i<name.length(); i++) {
			name_character = name.charAt(i);			
			
			if(name_character==' ') {	
				space = true;								
			} else if(!(name_character==' ') && (space==true && first == false)) {									
				stringBuilder.append("+");
				stringBuilder.append(name_character);				
				space = false;
			} else if(!(name_character==' ') && (space==false && first == false)) {
				stringBuilder.append(name_character);
				space = false;
			} else if(!(name_character==' ') && (space==false && first == true)) {
				stringBuilder.append(name_character);
				first = false; 
				space = false;				
			}
		}
		
		sanitizedName = stringBuilder.toString().toLowerCase();

		return sanitizedName;
	}	
	
}
