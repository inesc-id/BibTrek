package inesc_id.gsd.bibtrek.app.dblp;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.search.AuthorSearch;
import inesc_id.gsd.bibtrek.app.dblp.search.AuthorsPublicationsSearch;
import inesc_id.gsd.bibtrek.app.dblp.search.PublicationSearch;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.WriteConditionFactory;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorsPublicationsSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPClassException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPInterfaceException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.exceptions.PublicationSearchException;

public class DBLPClass {
	
	private final static String AUTHOR = "author";
	private final static String AUTHORS_PUBLICATIONS = "authors-publications";
	private final static String PUBLICATION = "publication";
	
	
	public static void execute(String inputCommand, Scanner userInput) throws DBLPClassException {
		DBLPQueryCreator queryCreator;		
		String query, authorName, publicationTitle, getRequest;		
		AuthorSearch authorSearch;
		AuthorsPublicationsSearch authorsPublicationsSearch;
		PublicationSearch publicationSearch;
		DBLPNoSQLWriter dblpNoSQLWriter;
		ArrayList<Object[]> tupleArrayList; 
		
		queryCreator = new DBLPQueryCreator();                        		 
		
		try {
			switch(inputCommand) {
				case AUTHOR:
					authorSearch = new AuthorSearch(queryCreator, userInput, AUTHOR);
					tupleArrayList = authorSearch.search();		
					dblpNoSQLWriter = WriteConditionFactory.getWriter(tupleArrayList, inputCommand);
					dblpNoSQLWriter.writeToFile();
					break;
				case AUTHORS_PUBLICATIONS:
					authorsPublicationsSearch = new AuthorsPublicationsSearch(queryCreator, userInput, AUTHORS_PUBLICATIONS);
					authorsPublicationsSearch.search();					
					break;
				case PUBLICATION:
					publicationSearch = new PublicationSearch(queryCreator, userInput, PUBLICATION);
					tupleArrayList = publicationSearch.search();
					dblpNoSQLWriter = WriteConditionFactory.getWriter(tupleArrayList, inputCommand);
					dblpNoSQLWriter.writeToFile();
					break;
			}
		} catch(AuthorSearchException ase) {
	    	throw new DBLPClassException("display(): could not execute search for author by name.");
	    } catch(AuthorsPublicationsSearchException apse) {
			throw new DBLPClassException("display(): could not execute search for authors' publications.");
		} catch (PublicationSearchException pse) {
	    	throw new DBLPClassException("display(): could not execute search for publications.");
		} catch (DBLPNoSQLWriterException dblpnosqlwe) {
			throw new DBLPClassException("display(): could not invoke the write operation.");
		}
	}
}
