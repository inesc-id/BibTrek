package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.AuthorsPublicationSearchBranchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.AuthorsPublicationsSearchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.PublicationSearchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.SearchException;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.AuthorsPublicationsSearchBranch;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.SearchBranch;

public class AuthorsPublicationsSearch {;
	
	private String choice;
	private DBLPQueryCreator queryCreator; 
	private Scanner userInput;	
	
	
	public AuthorsPublicationsSearch(DBLPQueryCreator queryCreator, Scanner userInput, String choice) {
		this.queryCreator = queryCreator;
		this.userInput = userInput;
		this.choice = choice;
	}

	public void search(ArrayList<Object[]> tupleArrayList) throws AuthorsPublicationsSearchException {		
		AuthorsPublicationsSearchBranch authorsPublicationsSearchBranch;										

		if(!tupleArrayList.isEmpty()) {			
			try {											
				authorsPublicationsSearchBranch = new AuthorsPublicationsSearchBranch(this.queryCreator, this.userInput, this.choice);
				authorsPublicationsSearchBranch.choose(tupleArrayList);
			} catch (AuthorsPublicationSearchBranchException apsbe) {
				throw new AuthorsPublicationsSearchException("search(): could not search for publication.");
			}			
		}		
	}
}
