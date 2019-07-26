package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.AuthorsPublicationsSearchBranch;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.SearchBranch;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorsPublicationsSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.PublicationSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchException;

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
		String authorName, query, userChoice, getRequest, continueChoice;		
		AuthorJSONParser authorJSONParser;	
		ArrayList<Object[]> chosenAuthors;		
		SearchBranch searchBranch;
		AuthorsPublicationsSearchBranch authorsPublicationsSearchBranch;
		PublicationSearch publicationSearch;
				
		authorJSONParser = new AuthorJSONParser();
		searchBranch = new SearchBranch(this.choice, this.userInput);
		authorsPublicationsSearchBranch = new AuthorsPublicationsSearchBranch(this.userInput);						

		if(!tupleArrayList.isEmpty()) {			
			try {
				//TODO pensar em melhorar as escolhas. Passar a devolver tudo igual a esta e os writes sao feitos la fora
				chosenAuthors = authorsPublicationsSearchBranch.choose(tupleArrayList);
											
				if(chosenAuthors!=null) {
					publicationSearch = new PublicationSearch(this.queryCreator, this.userInput, this.choice);
					for(int i = 0; i<chosenAuthors.size(); i++) {
						authorName = (String) chosenAuthors.get(i)[0];
						if(i>=1) {
							while(true) {
								boolean breakCycle = false;
								System.out.println("");
								System.out.print("(*) Do you want to fetch the publications of the other authors you have fetched? Type [Y|N]: ");											
								continueChoice = this.userInput.nextLine();
								//TODO por isto numa funcao especifica
								switch(continueChoice) {
									case "Y":
										publicationSearch.search(authorName);
										breakCycle = true;
										break;
									case "N":									
										return;
									default:
										System.out.println("You must insert a valid choice...");
								}
								if(breakCycle)
									break;
							}
						} else {
							publicationSearch.search(authorName);
						}
					}
				} 
			} catch (SearchBranchException sbe) {
				throw new AuthorsPublicationsSearchException("search(): could not search for publication.");
			} catch (PublicationSearchException pse) {
				throw new AuthorsPublicationsSearchException("search(): could not search for publication.");
			}			
		}		
	}
}
