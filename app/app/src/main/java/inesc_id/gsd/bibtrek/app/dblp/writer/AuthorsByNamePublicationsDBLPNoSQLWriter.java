package inesc_id.gsd.bibtrek.app.dblp.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.utils.TimeUtils;

public class AuthorsByNamePublicationsDBLPNoSQLWriter extends DBLPNoSQLWriter{
	
	public AuthorsByNamePublicationsDBLPNoSQLWriter(ArrayList<Object[]> listToWrite) {
		super(listToWrite);
	}
	
	@SuppressWarnings("unchecked")
	public void writeToFile() throws DBLPNoSQLWriterException {
		BufferedWriter bufferedWriter;
		Iterator<Object[]> iter;
		Object[] tuple;
		ArrayList<String> authorsArrayList;
		String author = "no_author", title, url, year, venue, type, ee, key, doi;
		
		iter = listToWrite.iterator();
		authorsArrayList = new ArrayList<String>();
		
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(DBLP_FILE, true));
			
			while(iter.hasNext()) {
				tuple = iter.next();
				
				bufferedWriter.append("\n" + TimeUtils.getCurrentTimeString());
				
				authorsArrayList = (ArrayList<String>) tuple[0];
				for(String authorIter : authorsArrayList) {
					author = authorIter;
					bufferedWriter.append("\n" + author);
				}
				
				title = (String) tuple[1];
				url = (String) tuple[2];
				year = (String) tuple[3];
				venue = (String) tuple[4];
				type = (String) tuple[5];
				ee = (String) tuple[6];
				key = (String) tuple[7];
				doi = (String) tuple[8];
				
				bufferedWriter.append("\n" + title);
				bufferedWriter.append("\n" + url);
				bufferedWriter.append("\n" + year);
				bufferedWriter.append("\n" + venue);
				bufferedWriter.append("\n" + type);
				bufferedWriter.append("\n" + ee);
				bufferedWriter.append("\n" + key);
				bufferedWriter.append("\n" + doi);
				bufferedWriter.close();
			}
		} catch (IOException ioe) {
			throw new DBLPNoSQLWriterException("writeToFile(): could not write to the: \"" + DBLP_FILE + "\".");
		}		
	}
}
