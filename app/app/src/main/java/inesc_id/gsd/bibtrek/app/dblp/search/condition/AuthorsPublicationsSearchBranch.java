package inesc_id.gsd.bibtrek.app.dblp.search.condition;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.PublicationSearchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.dblp.search.PublicationSearch;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;

public class AuthorsPublicationsSearchBranch {
	
	private String choice;
	private DBLPQueryCreator queryCreator;
	private Scanner userInput;
	
	public AuthorsPublicationsSearchBranch(DBLPQueryCreator queryCreator, Scanner userInput, String choice) {
		this.choice = choice;
		this.queryCreator = queryCreator;
		this.userInput = userInput;
	}
	
	public void choose(ArrayList<Object[]> tupleArrayList) throws PublicationSearchException {
		String authorName, continueChoice;
		PublicationSearch publicationSearch;
		
		publicationSearch = new PublicationSearch(this.queryCreator, this.userInput, this.choice);
		
		for(int i = 0; i<tupleArrayList.size(); i++) {
			authorName = (String) tupleArrayList.get(i)[0];
			if(i>=1) {
				while(true) {
					boolean breakCycle = false;
					System.out.println("");
					System.out.print("(*) Do you want to fetch the publications of the other authors you have fetched? Type [Y|N]: ");											
					continueChoice = this.userInput.nextLine();
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
	
}
