package inesc_id.gsd.bibtrek.app.dblp;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.http.HTTPClient;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorsPublicationsJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
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
	
	public String executeQuery(String query) throws DBLPInterfaceException {
		HTTPClient httpClient = new HTTPClient();
		String getRequest;
		try {
			getRequest = httpClient.getRequest(query);
			return getRequest;
		} catch(HTTPClientException httpce) {
			throw new DBLPInterfaceException("executeQuery(): could not execute a get request for"
					+ " the website with url: \"" + query + "\".");
		}
	}
	
	
	public void display() throws DBLPInterfaceException {
		String query, inputCommand, authorName, publicationTitle, getRequest;
		DBLPQueryCreator queryCreator;		
		
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
		    
			switch(inputCommand) {
				case SEARCH_AUTHOR_BY_NAME:
					searchAuthorByName(queryCreator);
					break;
				case SEARCH_AUTHORS_PUBLICATIONS:
					searchAuthorsPublicationByName(queryCreator);
					break;
				case SEARCH_PUBLICATION: 
					System.out.print("Type the publication's title: ");	
					publicationTitle = this.userInput.nextLine(); 
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
	
	private void searchAuthorByName(DBLPQueryCreator queryCreator) throws DBLPInterfaceException {
		String query, authorName, getRequest;		
		AuthorJSONParser authorJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		
				
		authorJSONParser = new AuthorJSONParser();					
		System.out.print("Type the author's name: ");	            									
		authorName = this.userInput.nextLine(); 
		query = queryCreator.searchAuthorByName(authorName);
		getRequest = this.executeQuery(query);
		authorJSONParser.setString(getRequest);
		tupleArrayList = authorJSONParser.parseString();
		if(!tupleArrayList.isEmpty()) {
			chooseAuthorsToAdd(tupleArrayList);
		}
			
	}
	
	private void searchAuthorsPublicationByName(DBLPQueryCreator queryCreator) throws DBLPInterfaceException {
		String query, authorName, getRequest;		
		AuthorsPublicationsJSONParser authorsPublicationsJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		
				
		authorsPublicationsJSONParser = new AuthorsPublicationsJSONParser();					
		System.out.print("Type the author's name: ");	            									
		authorName = this.userInput.nextLine(); 
		query = queryCreator.searchAuthorsPublications(authorName);
		getRequest = this.executeQuery(query);
		System.out.println(getRequest);		
		authorsPublicationsJSONParser.setString(getRequest);		
		tupleArrayList = authorsPublicationsJSONParser.parseString();
		if(!tupleArrayList.isEmpty()) {
			//chooseAuthorsToAdd(tupleArrayList);
		}
				
	}
	
	private void chooseAuthorsToAdd(ArrayList<Object[]> tupleArrayList) throws DBLPInterfaceException {
		String authorChoice;
		boolean exit = false;
		
		ArrayList<Object[]> addedAuthors = new ArrayList<Object[]>();
		while(true) {
			System.out.println("");
			System.out.println("(a)");
			System.out.println("- Add all of the authors and exits the menu ; ");
			System.out.println("");
			System.out.println("(es)");
			System.out.println("- Exits the choice menu and saves the configuration you have chosen ;");
			System.out.println("");
			System.out.println("(e!)");
			System.out.println("- Exits the choice menu without saving ;");
			System.out.println(""); 
			System.out.print("Pick one of the authors to insert in the database: ");	            									
			authorChoice = this.userInput.nextLine();
			//if the string is an integer use it to choose an author
			if(StringToIntegerUtils.isInteger(authorChoice)) {
				addedAuthors = chooseAuthorsToAddIntegerCondition(authorChoice, tupleArrayList);				
			} else {
				exit = chooseAuthorsToAddCharacterCondition(authorChoice, tupleArrayList, addedAuthors);
				
				if(exit)
					return;
			}			
		}
	}
	
	private ArrayList<Object[]> chooseAuthorsToAddIntegerCondition(String authorChoice, ArrayList<Object[]> tupleArrayList) {
		Object[] tuple;
		ArrayList<Object[]> addedAuthors;
		
		addedAuthors = new ArrayList<Object[]>();
		
		try {
			tuple = tupleArrayList.get(Integer.valueOf(authorChoice)-1);	
			if(addedAuthors.contains(tuple)) {
				System.out.println("");
				System.out.println("You cannot pick the same author twice!");
			} else {				
				addedAuthors.add(tuple);
			}
			
		} catch(IndexOutOfBoundsException ioobe) {
			System.out.println("");
			System.out.println("You must insert a valid option!");
		} catch(NumberFormatException nfe) {
			System.out.println("");
			System.out.println("You must insert a valid option!");
		}
		
		return addedAuthors;
	}
	
	private boolean chooseAuthorsToAddCharacterCondition(String authorChoice, ArrayList<Object[]> tupleArrayList, ArrayList<Object[]> addedAuthors) throws DBLPInterfaceException {				
		DBLPNoSQLWriter dblpNoSQLWriter;
		
		try {
			if(authorChoice.equals("a")) {
				dblpNoSQLWriter = new DBLPNoSQLWriter(tupleArrayList);
				dblpNoSQLWriter.writeToFile();
				return true;
			} else if(authorChoice.equals("es") && !addedAuthors.isEmpty()) {
				dblpNoSQLWriter = new DBLPNoSQLWriter(addedAuthors);
				dblpNoSQLWriter.writeToFile();
				return true;
			} else if(authorChoice.equals("es") && addedAuthors.isEmpty()) {			
				return true;
			} else if(authorChoice.equals("e!")) {			
				return true;
			} else {
				System.out.println("");
				System.out.println("You must insert a valid option!");
				return false;
			}
		} catch(DBLPNoSQLWriterException dblpnosqlwe) {
			throw new DBLPInterfaceException("chooseAuthorsToAddCharacterCondition(): could not execute a valid option!");
		}
	}	
	
}


