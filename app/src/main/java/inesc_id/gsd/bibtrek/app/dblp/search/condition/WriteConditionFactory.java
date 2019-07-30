package inesc_id.gsd.bibtrek.app.dblp.search.condition;

import java.util.ArrayList;

import inesc_id.gsd.bibtrek.app.dblp.writer.AuthorDBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.dblp.writer.PublicationsDBLPNoSQLWriter;

public class WriteConditionFactory {
	
	private final static String AUTHOR = "author";
	private final static String AUTHORS_PUBLICATIONS = "authors-publications";
	private final static String PUBLICATION = "publication";
	
	public static DBLPNoSQLWriter getWriter(ArrayList<Object[]> tupleArrayList, String condition) {
		DBLPNoSQLWriter writer = null; 		
		switch(condition) {
			case AUTHOR:
				writer = new AuthorDBLPNoSQLWriter(tupleArrayList);
				break;
			case AUTHORS_PUBLICATIONS:
			case PUBLICATION:
				writer = new PublicationsDBLPNoSQLWriter(tupleArrayList);
				break;					
		}
		return writer;	
	}
}
