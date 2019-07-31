package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.PublicationSearchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.SearchException;
import inesc_id.gsd.bibtrek.app.dblp.parsing.PublicationJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.SearchBranch;

public class PublicationSearch extends Search {
	
	private String choice;
	private DBLPQueryCreator queryCreator; 
	private Scanner userInput;	
	
	public PublicationSearch(DBLPQueryCreator queryCreator, Scanner userInput, String choice) {		
		this.queryCreator = queryCreator;
		this.userInput = userInput;
		this.choice = choice;
	}

	@Override
	public ArrayList<Object[]> search() throws PublicationSearchException {
		String query, userChoice, getRequest;		
		PublicationJSONParser publicationJSONParser;	
		ArrayList<Object[]> tupleArrayList = null;
		SearchBranch searchBranch;
				
		publicationJSONParser = new PublicationJSONParser();
		searchBranch = new SearchBranch(this.choice, this.userInput);
		
		System.out.print("Type the publication's title: ");
				
		userChoice = this.userInput.nextLine(); 
				
		query = queryCreator.searchForPublication(userChoice);		
		
		try {
			getRequest = this.executeQuery(query);
		} catch (SearchException se) {
			throw new PublicationSearchException("search(): could not execute the query: \"" + query + "\".");
		}
		publicationJSONParser.setString(getRequest);
		System.out.println("");
		System.out.println("(*) Publications Found: ");
		tupleArrayList = publicationJSONParser.parseString();
		if(!tupleArrayList.isEmpty()) {					
			try {
				searchBranch.choose(tupleArrayList);
			} catch (SearchBranchException sbe) {
				throw new PublicationSearchException("search(): could not search for publication.");
			}			
		}
		
		return tupleArrayList;
	}
	
	public ArrayList<Object[]> search(String title) throws PublicationSearchException {
		String query, userChoice, getRequest;		
		PublicationJSONParser publicationJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		SearchBranch searchBranch;
		ArrayList<Object[]> publicationsList = null;
		
		publicationJSONParser = new PublicationJSONParser();
		searchBranch = new SearchBranch(this.choice, this.userInput);
			 			
		query = queryCreator.searchForPublication(title);		
		
		try {
			getRequest = this.executeQuery(query);
		} catch (SearchException se) {
			throw new PublicationSearchException("search(): could not execute the query: \"" + query + "\".");
		}
		publicationJSONParser.setString(getRequest);
		System.out.println("");
		System.out.println("(*) Publications Found: ");
		tupleArrayList = publicationJSONParser.parseString();
		if(!tupleArrayList.isEmpty()) {					
			try {
				publicationsList = searchBranch.choose(tupleArrayList);
			} catch (SearchBranchException sbe) {
				throw new PublicationSearchException("search(): could not search for publication.");
			}			
		}
		return publicationsList;
	}

}
