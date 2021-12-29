package com.chimpcentral.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

abstract class NinjaFile {
	
	CreatedCheck fileCreatedCheck = null;
	String filepath;
	String filename;
	String absoluteFilepath;
	
	FileStatus fileStatus;
	File file;
	
	NinjaFile(String filepath, FileStatus fileStatus) throws IOException {
		this.filepath = filepath;
		this.fileStatus = fileStatus;
		getFile();
	}
	
	private void getFile() throws IOException {
		switch (fileStatus) {
		case EXISTING:
			fileCreatedCheck = new Existing();
			break;
		case NEW:
			fileCreatedCheck = new New();
			break;
		case NA:
			fileCreatedCheck = new Skip();
			break;
		}
		file = fileCreatedCheck.getFile(filepath);
		absoluteFilepath = file.getAbsolutePath();
	}
	
	public void createCopy(String destFilepath) throws IOException {
		File destFile = new File(destFilepath);
		FileUtils.copyFile(this.file, destFile);
	}	
	
	public abstract NinjaFile getCopy(String destFilepath) throws IOException, InvalidFormatException;
	
	public void delete() throws IOException {
		Files.delete(Paths.get(absoluteFilepath));
	}
	
	public void deleteIfExist() throws IOException {
		Files.deleteIfExists(Paths.get(absoluteFilepath));
	}
	
	public boolean exists() {
		return file.exists();
	}
}
