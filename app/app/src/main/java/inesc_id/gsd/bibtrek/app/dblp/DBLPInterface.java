package inesc_id.gsd.bibtrek.app.dblp;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.http.HTTPClient;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.search.AuthorSearch;
import inesc_id.gsd.bibtrek.app.dblp.search.AuthorsPublicationsSearch;
import inesc_id.gsd.bibtrek.app.dblp.search.PublicationSearch;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorsPublicationsSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPInterfaceException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.exceptions.HTTPClientException;
import inesc_id.gsd.bibtrek.app.exceptions.PublicationSearchException;

public class DBLPInterface {
	
	private final static String AUTHOR = "author";
	private final static String AUTHORS_PUBLICATIONS = "authors-publications";
	private final static String PUBLICATION = "publication";
	
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
		AuthorSearch authorSearch;
		AuthorsPublicationsSearch authorsPublicationsSearch;
		PublicationSearch publicationSearch;
		
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
						authorSearch = new AuthorSearch(queryCreator, this.userInput, AUTHOR);
						authorSearch.search();					
						break;
					case SEARCH_AUTHORS_PUBLICATIONS:
						authorsPublicationsSearch = new AuthorsPublicationsSearch(queryCreator, this.userInput, AUTHORS_PUBLICATIONS);
						authorsPublicationsSearch.search();					
						break;
					case SEARCH_PUBLICATION:
						publicationSearch = new PublicationSearch(queryCreator, this.userInput, PUBLICATION);
						publicationSearch.search();
						break;
					case EXIT:		                        
			            return;       
					default:
						System.out.println("Error! You must insert a valid command...");
						break;
				}	
		    } catch(AuthorSearchException ase) {
		    	throw new DBLPInterfaceException("display(): could not execute search for author by name.");
		    } catch(AuthorsPublicationsSearchException apse) {
				throw new DBLPInterfaceException("display(): could not execute search for authors' publications.");
			} catch (PublicationSearchException pse) {
		    	throw new DBLPInterfaceException("display(): could not execute search for publications.");
			}
			
		}         
	}		

}


