package inesc_id.gsd.bibtrek.app.dblp;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.http.HTTPClient;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorsPublicationsJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.search.AuthorByNameSearch;
import inesc_id.gsd.bibtrek.app.dblp.search.AuthorsPublicationByNameSearch;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorByNameSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorsPublicationByNameSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPInterfaceException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
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
		
	public void display() throws DBLPInterfaceException {
		String query, inputCommand, authorName, publicationTitle, getRequest;
		DBLPQueryCreator queryCreator;		
		AuthorByNameSearch authorByNameSearch;
		AuthorsPublicationByNameSearch authorsPublicationByNameSearch;
		
        queryCreator = new DBLPQueryCreator();                        		   
        
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
						authorByNameSearch = new AuthorByNameSearch(queryCreator, this.userInput);
						authorByNameSearch.search();					
						break;
					case SEARCH_AUTHORS_PUBLICATIONS:
						authorsPublicationByNameSearch = new AuthorsPublicationByNameSearch(queryCreator, this.userInput);
						authorsPublicationByNameSearch.search();					
						break;
					case SEARCH_PUBLICATION: 
						System.out.print("Type the publication's title: ");	
						/*publicationTitle = this.userInput.nextLine(); 
						query = queryCreator.searchForPublication(publicationTitle);
						this.executeQuery(query);*/
						break;
					case EXIT:		                        
			            return;       
					default:
						System.out.println("Error! You must insert a valid command...");
						break;
				}	
		    } catch(AuthorByNameSearchException abnse) {
		    	throw new DBLPInterfaceException("display(): could not execute search for author by name.");
		    } catch(AuthorsPublicationByNameSearchException apbnse) {
		    	throw new DBLPInterfaceException("display(): could not execute search for author's publication by name.");
		    }
			
		}         
	}		

}


