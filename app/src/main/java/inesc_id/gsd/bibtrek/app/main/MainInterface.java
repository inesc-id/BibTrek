package inesc_id.gsd.bibtrek.app.main;

import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPInterface;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.DBLPInterfaceException;
import inesc_id.gsd.bibtrek.app.exceptions.MainInterfaceException;

public class MainInterface {
 
    private static MainInterface main_interface = null; 
    
    private final static String DBLP = "dblp";    
    private final static String EXIT = "exit";
   
    private MainInterface() {  
    } 
  
    public static MainInterface getInstance() { 
        if (main_interface == null) 
        	main_interface = new MainInterface(); 
  
        return main_interface; 
    } 
    
    public void displayDBLP(Scanner userInput) throws MainInterfaceException {
    	DBLPInterface dblpInterface;
    	
    	dblpInterface = new DBLPInterface(userInput);
		
    	try {
    		dblpInterface.display();
    	} catch(DBLPInterfaceException dblpie) {
    		throw new MainInterfaceException("displayDBLP(): could not display the DBLP query menu...");
    	}
    	
    }
    
    public void display() throws MainInterfaceException {    	
    	String chosenLibrary;
    	    	
    	System.out.println("BIBTREK");
    	System.out.println("");
    	
    	Scanner userInput = new Scanner(System.in); 
    	
    	while(true) {        	            
            System.out.println("Pick one of the options in the menu.");     
            System.out.println("");
            System.out.println("(dblp) Digital Bibliography & Library Project");
            System.out.println("");
            System.out.println("(exit) Exits the program");
            System.out.println("");
            System.out.print("Type one of the above commands: ");            
            chosenLibrary = userInput.nextLine();                                                 
            System.out.println("");  
            
             
            switch(chosenLibrary) {            
            	case DBLP:    
            		this.displayDBLP(userInput);            		
            		break;
            	case EXIT:
            		return;
            	default:
            		System.out.println("Error! You must insert a valid command...");
            		break;
            }                       
        }
    }
}
