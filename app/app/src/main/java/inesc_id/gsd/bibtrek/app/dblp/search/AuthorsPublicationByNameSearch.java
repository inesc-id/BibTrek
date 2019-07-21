package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorsPublicationsJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.writer.AuthorsByNamePublicationsDBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorsPublicationByNameSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchException;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;


public class AuthorsPublicationByNameSearch extends Search {
	
	private DBLPQueryCreator queryCreator; 
	private Scanner userInput;
	
	public AuthorsPublicationByNameSearch(DBLPQueryCreator queryCreator, Scanner userInput) {		
		this.queryCreator = queryCreator;
		this.userInput = userInput;
	}

	@Override
	public void search() throws AuthorsPublicationByNameSearchException {
		String query, authorName, getRequest;		
		AuthorsPublicationsJSONParser authorsPublicationsJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		
				
		authorsPublicationsJSONParser = new AuthorsPublicationsJSONParser();					
		System.out.print("Type the author's name: ");	            									
		authorName = this.userInput.nextLine(); 
		query = queryCreator.searchAuthorsPublications(authorName);
		try {
			getRequest = this.executeQuery(query);
		} catch (SearchException se) {
			throw new AuthorsPublicationByNameSearchException("search(): could not execute the query: \"" + query + "\".");
		}
		authorsPublicationsJSONParser.setString(getRequest);
		System.out.println("");
		System.out.println("(*) Publications Found: ");
		tupleArrayList = authorsPublicationsJSONParser.parseString();
		if(!tupleArrayList.isEmpty()) {			
			choosePublicationsToAdd(tupleArrayList);
		}
	}
	
	private void choosePublicationsToAdd(ArrayList<Object[]> tupleArrayList) throws AuthorsPublicationByNameSearchException {
		String publicationChoice;
		boolean exit = false;
		
		ArrayList<Object[]> addedPublications = new ArrayList<Object[]>();
		while(true) {
			System.out.println("");
			System.out.println("(*) Author's Publication Menu");
			System.out.println("");
			System.out.println("(a)");
			System.out.println("- Add all of the publications and exits the menu ; ");
			System.out.println("");
			System.out.println("(es)");
			System.out.println("- Exits the choice menu and saves the configuration you have chosen ;");
			System.out.println("");
			System.out.println("(e!)");
			System.out.println("- Exits the choice menu without saving ;");
			System.out.println(""); 
			System.out.print("Pick one of the publications to insert in the database: ");	            									
			publicationChoice = this.userInput.nextLine();

			if(StringToIntegerUtils.isInteger(publicationChoice)) {
				addedPublications = choosePublicationsToAddIntegerCondition(publicationChoice, tupleArrayList);				
			} else {
				exit = choosePublicationsToAddCharacterCondition(publicationChoice, tupleArrayList, addedPublications);
				
				if(exit)
					return;
			}			
		}
	}
	
	private ArrayList<Object[]> choosePublicationsToAddIntegerCondition(String publicationChoice, ArrayList<Object[]> tupleArrayList) {
		Object[] tuple;
		ArrayList<Object[]> addedPublications;
		
		addedPublications = new ArrayList<Object[]>();
		
		try {
			tuple = tupleArrayList.get(Integer.valueOf(publicationChoice)-1);	
			if(addedPublications.contains(tuple)) {
				System.out.println("");
				System.out.println("You cannot pick the same publication twice!");
			} else {				
				addedPublications.add(tuple);
			}
			
		} catch(IndexOutOfBoundsException ioobe) {
			System.out.println("");
			System.out.println("You must insert a valid option!");
		} catch(NumberFormatException nfe) {
			System.out.println("");
			System.out.println("You must insert a valid option!");
		}
		
		return addedPublications;
	}
	
	private boolean choosePublicationsToAddCharacterCondition(String publicationChoice, ArrayList<Object[]> tupleArrayList, ArrayList<Object[]> addedPublications) throws AuthorsPublicationByNameSearchException  {				
		DBLPNoSQLWriter dblpNoSQLWriter;
		
		try {
			if(publicationChoice.equals("a")) {
				dblpNoSQLWriter = new AuthorsByNamePublicationsDBLPNoSQLWriter(tupleArrayList);
				dblpNoSQLWriter.writeToFile();
				return true;
			} else if(publicationChoice.equals("es") && !addedPublications.isEmpty()) {
				dblpNoSQLWriter = new AuthorsByNamePublicationsDBLPNoSQLWriter(addedPublications);
				dblpNoSQLWriter.writeToFile();
				return true;
			} else if(publicationChoice.equals("es") && addedPublications.isEmpty()) {			
				return true;
			} else if(publicationChoice.equals("e!")) {			
				return true;
			} else {
				System.out.println("");
				System.out.println("You must insert a valid option!");
				return false;
			}
		} catch(DBLPNoSQLWriterException dblpnosqlwe) {
			throw new AuthorsPublicationByNameSearchException("choosePublicationsToAddCharacterCondition(): could not execute a valid option!");
		}
	}	
	
}
