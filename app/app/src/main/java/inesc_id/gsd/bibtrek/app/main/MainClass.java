package inesc_id.gsd.bibtrek.app.main;

import inesc_id.gsd.bibtrek.app.exceptions.MainClassException;
import inesc_id.gsd.bibtrek.app.exceptions.MainInterfaceException;

public class MainClass {		
	
    public static void main(String[] args ) throws MainClassException {	
    	MainInterface mainInterface = MainInterface.getInstance();
    	
    	try {
			mainInterface.display();
		} catch (MainInterfaceException mie) {
			throw new MainClassException("main(): an error occurred while displaying the menu! Aborting...");
		}
    }
    

}
