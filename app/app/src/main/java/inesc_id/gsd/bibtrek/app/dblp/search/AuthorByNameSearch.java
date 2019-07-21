package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorByNameSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPInterfaceException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchException;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;

public class AuthorByNameSearch extends Search {
	
	private DBLPQueryCreator queryCreator; 
	private Scanner userInput;
	
	public AuthorByNameSearch(DBLPQueryCreator queryCreator, Scanner userInput) {
		this.queryCreator = queryCreator;
		this.userInput = userInput;
	}

	@Override
	public void search() throws AuthorByNameSearchException {
		String query, authorName, getRequest;		
		AuthorJSONParser authorJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		
				
		authorJSONParser = new AuthorJSONParser();					
		System.out.print("Type the author's name: ");	            									
		authorName = this.userInput.nextLine(); 
		query = this.queryCreator.searchAuthorByName(authorName);		
		try {
			getRequest = this.executeQuery(query);
		} catch (SearchException se) {
			throw new AuthorByNameSearchException("search(): could not execute the query: \"" + query + "\".");
		}
		authorJSONParser.setString(getRequest);
		System.out.println("");
		System.out.println("(*) Authors Found: ");
		tupleArrayList = authorJSONParser.parseString();
		if(!tupleArrayList.isEmpty()) {		
			chooseAuthorsToAdd(tupleArrayList);
		}		
	}
	
	private void chooseAuthorsToAdd(ArrayList<Object[]> tupleArrayList) throws AuthorByNameSearchException {
		String authorChoice;
		boolean exit = false;
		
		ArrayList<Object[]> addedAuthors = new ArrayList<Object[]>();
		while(true) {
			System.out.println("");
			System.out.println("Author's Menu");
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
	
	private boolean chooseAuthorsToAddCharacterCondition(String authorChoice, ArrayList<Object[]> tupleArrayList, ArrayList<Object[]> addedAuthors) throws AuthorByNameSearchException  {				
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
			throw new AuthorByNameSearchException("chooseAuthorsToAddCharacterCondition(): could not execute a valid option!");
		}
	}	
	
	
	
}
