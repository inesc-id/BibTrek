package inesc_id.gsd.bibtrek.app.sanitizer;

public class NameSanitizer extends QuerySanitizer {
	
	private String name;
		
	public NameSanitizer(String name) {
		this.name = name;
	}
	
	@Override
	public String sanitize() {
		String sanitizedName;
		StringBuilder stringBuilder = new StringBuilder();
		boolean non_whitespace = false;		
		char name_character;
		
		for(int i = 0; i<name.length(); i++) {
			name_character = name.charAt(i);			
			
			if(name_character==' ') {
				non_whitespace = false;				
			}
			else {
				if(non_whitespace) {
					stringBuilder.append(name_character);
				} else {
					stringBuilder.append('%');
					stringBuilder.append(name_character);
					non_whitespace = true;
				}
			}
		}
		
		stringBuilder.append('%');
		sanitizedName = stringBuilder.toString().toLowerCase();
		
		return sanitizedName;
	}	
	
}
