package inesc_id.gsd.bibtrek.app.sanitizer;

public class TitleSanitizer extends QuerySanitizer {
	
	private String title;
		
	public TitleSanitizer(String title) {
		this.title = title;
	}
	
	@Override
	public String sanitize() {
		String sanitizedTitle;
		StringBuilder stringBuilder = new StringBuilder();
		boolean non_whitespace = false;		
		char title_character;
		
		for(int i = 0; i<title.length(); i++) {
			title_character = title.charAt(i);			
			
			if(title_character==' ') {
				non_whitespace = false;				
			}
			else {
				if(non_whitespace) {
					stringBuilder.append(title_character);
				} else {
					stringBuilder.append('%');
					stringBuilder.append(title_character);
					non_whitespace = true;
				}
			}
		}
		
		stringBuilder.append('%');
		sanitizedTitle = stringBuilder.toString().toLowerCase();
		
		return sanitizedTitle;
	}	
	
}
