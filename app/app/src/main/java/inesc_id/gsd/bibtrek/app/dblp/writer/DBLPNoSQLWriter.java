package inesc_id.gsd.bibtrek.app.dblp.writer;

import java.util.ArrayList;

import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;

public abstract class DBLPNoSQLWriter {
	
	final static String DBLP_FILE = "dblp.nosql";
	ArrayList<Object[]> listToWrite;
	
	public DBLPNoSQLWriter(ArrayList<Object[]> listToWrite) {
		this.listToWrite = listToWrite;
	}
	
	public abstract void writeToFile() throws DBLPNoSQLWriterException;
}
