package inesc_id.gsd.bibtrek.app.utils;

public class StringToIntegerUtils {
	
	public static boolean isInteger(String string) {
	    return isInteger(string,10);
	}

	public static boolean isInteger(String string, int radix) {
	    if(string.isEmpty()) return false;
	    for(int i = 0; i < string.length(); i++) {
	        if(i == 0 && string.charAt(i) == '-') {
	            if(string.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(string.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}
