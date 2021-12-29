package com.chimpcentral;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class Folder {
	
	CreatedCheck folderCreatedCheck = null;
	String folderpath;
	String folderName;
	String absouluteFolderpath;
	
	FolderStatus folderStatus;
	File folder;
	
	public Folder(String folderpath, FolderStatus folderStatus) throws IOException {
		this.folderpath = folderpath;
		this.folderStatus = folderStatus;
		getFolder();
	}
	
	private void getFolder() throws IOException {
		switch (folderStatus) {
		case EXISTING:
			folderCreatedCheck = new Existing();
			break;
		case NEW:
			folderCreatedCheck = new New();
			break;
		case NA:
			folderCreatedCheck = new Skip();
			break;
		}
		folder = folderCreatedCheck.getDir(folderpath);
		absouluteFolderpath = folder.getAbsolutePath();
	}
	
	public void createCopy(String destFolderpath) throws IOException {
		File destFolder = new File(destFolderpath);
		FileUtils.copyDirectory(this.folder, destFolder);
	}
	
	public void delete() throws IOException {
		Files.delete(Paths.get(absouluteFolderpath));
	}
	
	public void deleteIfExist() throws IOException {
		Files.deleteIfExists(Paths.get(absouluteFolderpath));
	}
	
	public boolean exists() {
		return folder.exists();
	}
}
