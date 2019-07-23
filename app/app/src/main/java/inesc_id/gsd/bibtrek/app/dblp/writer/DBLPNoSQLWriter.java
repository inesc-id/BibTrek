package inesc_id.gsd.bibtrek.app.dblp.writer;

import java.util.ArrayList;

import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.utils.TimeUtils;

public abstract class DBLPNoSQLWriter {
	
	final static String REPLACE_REGEX = "[\\W\\s]";
	
	private final static String DBLP_NOSQL_PATH = "src/main/java/inesc_id/gsd/bibtrek/app/nosql/dblp/";

	ArrayList<Object[]> listToWrite;
	
	public DBLPNoSQLWriter(ArrayList<Object[]> listToWrite) {
		this.listToWrite = listToWrite;
	}
	
	public abstract void writeToFile() throws DBLPNoSQLWriterException;
	
	protected String getCurrentFile() {
		String fileName;
		
		fileName = DBLP_NOSQL_PATH + "dblp" 
				+ "_" + TimeUtils.getCurrentTimeString() + ".nosql";
		
		return fileName;
	}
}
