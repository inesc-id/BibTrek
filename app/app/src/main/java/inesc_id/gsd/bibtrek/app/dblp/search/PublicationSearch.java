package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.parsing.PublicationJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.SearchBranch;
import inesc_id.gsd.bibtrek.app.dblp.writer.AuthorsByNamePublicationsDBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.exceptions.PublicationSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchException;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;


public class PublicationSearch extends Search {
	
	private final static String AUTHORS_PUBLICATIONS = "authors-publications";
	private final static String PUBLICATION = "publication";
	
	private String choice;
	private DBLPQueryCreator queryCreator; 
	private Scanner userInput;	
	
	public PublicationSearch(DBLPQueryCreator queryCreator, Scanner userInput, String choice) {		
		this.queryCreator = queryCreator;
		this.userInput = userInput;
		this.choice = choice;
	}

	@Override
	public void search() throws PublicationSearchException {
		String query, authorName, getRequest;		
		PublicationJSONParser publicationJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		SearchBranch searchBranch;
				
		publicationJSONParser = new PublicationJSONParser();
		searchBranch = new SearchBranch(this.choice, this.userInput);
		if(this.choice.equals(AUTHORS_PUBLICATIONS)) {
			System.out.print("Type the author's name: ");
		} else if(this.choice.equals(PUBLICATION)) {
			System.out.print("Type the publication's title: ");
		}
		authorName = this.userInput.nextLine(); 
		query = queryCreator.searchAuthorsPublications(authorName);
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
			searchBranch.choose(tupleArrayList);
		}
	}

}
