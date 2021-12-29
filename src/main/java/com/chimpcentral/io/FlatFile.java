package com.chimpcentral.io;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FlatFile extends NinjaFile {

	public FlatFile(String filepath, FileStatus fileStatus) throws IOException {
		super(filepath, fileStatus);
	}

	@Override
	public FlatFile getCopy(String destFilepath) throws IOException {
		createCopy(destFilepath);
		return new FlatFile(destFilepath, FileStatus.EXISTING);
	}
	
	public String getContent() throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		FileReader fileReader = new FileReader(filepath);
	    LineNumberReader lineReader = new LineNumberReader(fileReader);
	    String line = lineReader.readLine();
	    while (line != null) {
	    	stringBuilder.append(line).append("\n");
	    	line = lineReader.readLine();
	    }
	    lineReader.close();
	    return stringBuilder.toString();
	}

	public FlatFile write(String content, StandardOpenOption standardOpenOption) throws IOException {
		Files.write(Paths.get(absoluteFilepath), content.getBytes(), standardOpenOption);
		return this;
	}
	
	public FlatFile appendContent(String content) throws IOException {
		write(content, StandardOpenOption.APPEND);
		return this;
	}

	public FlatFile overwriteContent(String content) throws IOException {
		write(content, StandardOpenOption.TRUNCATE_EXISTING);
		return this;
	}

	public FlatFile clearContent() throws IOException {
		overwriteContent("");
		return this;
	}
}
