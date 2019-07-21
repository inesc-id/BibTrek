package inesc_id.gsd.bibtrek.app.dblp.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.utils.TimeUtils;

public class DBLPNoSQLWriter {
	
	private final static String DBLP_FILE = "dblp.nosql";
	private ArrayList<Object[]> listToWrite;
	
	public DBLPNoSQLWriter(ArrayList<Object[]> listToWrite) {
		this.listToWrite = listToWrite;
	}
	
	public void writeToFile() throws DBLPNoSQLWriterException {
		BufferedWriter bufferedWriter;
		Iterator<Object[]> iter;
		Object[] tuple;
		String author, url;
		
		iter = listToWrite.iterator();
		
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(DBLP_FILE, true));
			
			while(iter.hasNext()) {
				tuple = iter.next();
				author = (String) tuple[0];
				url = (String) tuple[1];
				bufferedWriter.append("\n" + TimeUtils.getCurrentTimeString());
				bufferedWriter.append("\n" + author);
				bufferedWriter.append("\n" + url);
				bufferedWriter.close();
			}
		} catch (IOException ioe) {
			throw new DBLPNoSQLWriterException("writeToFile(): could not write to the: \"" + DBLP_FILE + "\".");
		}		
	}
}
