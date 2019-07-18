package inesc_id.gsd.bibtrek.app.dblp;

import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.http.HTTPClient;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPInterfaceException;
import inesc_id.gsd.bibtrek.app.exceptions.HTTPClientException;

public class DBLPInterface {
	
	private static final String SEARCH_AUTHOR_BY_NAME = "a"; 
	private static final String SEARCH_AUTHORS_PUBLICATIONS = "ap";
	private static final String SEARCH_PUBLICATION = "p";
	private final static String EXIT = "e";
	
	private Scanner userInput;
	
	public DBLPInterface(Scanner userInput) {
		this.userInput = userInput; 
	}
	
	public void executeQuery(String query) throws DBLPInterfaceException {
		HTTPClient httpClient = new HTTPClient();
		try {
			httpClient.getRequest(query);
		} catch(HTTPClientException httpce) {
			throw new DBLPInterfaceException("executeQuery(): could not execute a get request for"
					+ " the website with url: \"" + query + "\".");
		}
	}
	
	public void display() throws DBLPInterfaceException {
		String query, inputCommand, authorName, publicationTitle;
		
        DBLPQueryCreator queryCreator = new DBLPQueryCreator();                        		   
		
		while(true) {			
			System.out.println("");
		    System.out.println("(a)  : Searches for an author by name ;");
		    System.out.println("(ap) : Searches for an author's publications by name ;");
		    System.out.println("(ap) : Searches for an author's publications by name ;");
		    System.out.println("(p)  : Searches for a publications by title ;");
		    System.out.println("(e)  : Exits the DBLP query menu ;");
		    System.out.println("");
		    System.out.print("Type one of the above commands: ");
		    
		    inputCommand = userInput.nextLine();
		    
		    System.out.println("");
		    
			switch(inputCommand) {
				case SEARCH_AUTHOR_BY_NAME:
					System.out.print("Type the author's name: ");	            									
					authorName = userInput.nextLine(); 
					query = queryCreator.searchAuthorByName(authorName);
					this.executeQuery(query);
					break;
				case SEARCH_AUTHORS_PUBLICATIONS:
					System.out.print("Type the author's name: ");	
					authorName = userInput.nextLine(); 
					query = queryCreator.searchAuthorsPublications(authorName);
					this.executeQuery(query);
					break;
				case SEARCH_PUBLICATION: 
					System.out.print("Type the publication's title: ");	
					publicationTitle = userInput.nextLine(); 
					query = queryCreator.searchForPublication(publicationTitle);
					this.executeQuery(query);
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


