package inesc_id.gsd.bibtrek.app.dblp;

import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.exceptions.DBLPClassException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.DBLPInterfaceException;

public class DBLPInterface {
		
	private static final String SEARCH_AUTHOR_BY_NAME = "a"; 
	private static final String SEARCH_AUTHORS_PUBLICATIONS = "ap";
	private static final String SEARCH_PUBLICATION = "p";
	private final static String EXIT = "e";	
	
	private Scanner userInput;
	
	public DBLPInterface(Scanner userInput) {
		this.userInput = userInput; 		
	}
		
	public void display() throws DBLPInterfaceException {
		String inputCommand;
		          
		while(true) {
			System.out.println("");
			System.out.println("(*)  : DBLP MENU");
			System.out.println("");
		    System.out.println("(a)  : Searches for an author by name ;");
		    System.out.println("(ap) : Searches for an author's publications by name ;");
		    System.out.println("(p)  : Searches for a publications by title ;");
		    System.out.println("(e)  : Exits the DBLP query menu ;");
		    System.out.println("");
		    System.out.print("Type one of the above commands: ");
		    
		    inputCommand = this.userInput.nextLine();
		    
		    System.out.println("");
		    
		    try {
				switch(inputCommand) {
					case SEARCH_AUTHOR_BY_NAME:
						DBLPClass.execute(inputCommand, this.userInput);
						break;
					case SEARCH_AUTHORS_PUBLICATIONS:
						DBLPClass.execute(inputCommand, this.userInput);
						break;
					case SEARCH_PUBLICATION:
						DBLPClass.execute(inputCommand, this.userInput);
						break;
					case EXIT:		                        
			            return;       
					default:
						System.out.println("Error! You must insert a valid command...");
						break;
				}	
		    } catch(DBLPClassException dblpce) {
		    	throw new DBLPInterfaceException("display(): could not execute a DBLP operation...");
		    }
		}         
	}		

}


