package org.usfirst.frc.team2791.robot.util;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;

/**
 * Read a text file into a string.
 *
 * @author unbun
 */
public class TextFileReader {

	private String file;  
	
	public TextFileReader(String uri) {
		file=uri;
	}
	
	public String readWholeFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader (file));
		String line = null;
		StringBuilder  stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			while((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			return stringBuilder.toString();
		} finally {
			reader.close();

		}
	}
}