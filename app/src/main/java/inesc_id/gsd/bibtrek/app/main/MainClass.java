package inesc_id.gsd.bibtrek.app.main;

import java.io.OutputStream;
import java.io.PrintStream;

import inesc_id.gsd.bibtrek.app.exceptions.MainClassException;
import inesc_id.gsd.bibtrek.app.exceptions.MainInterfaceException;
import inesc_id.gsd.bibtrek.app.neo4j.WriteNeo4JThread;

public class MainClass {		
	
    public static void main(String[] args ) throws MainClassException {
    	WriteNeo4JThread neo4JThread;
		
    	MainInterface mainInterface = MainInterface.getInstance();    	
    	try {
    		neo4JThread = new WriteNeo4JThread();
    		new Thread(neo4JThread).start();
			mainInterface.display();
		} catch (MainInterfaceException mie) {
			throw new MainClassException("main(): an error occurred while displaying the menu! Aborting...");
		}
    }
    

}
