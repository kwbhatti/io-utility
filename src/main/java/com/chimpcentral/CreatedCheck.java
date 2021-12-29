package com.chimpcentral;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

interface CreatedCheck {
	
	File getFile(File file) throws IOException;
	File getFile(String filepath) throws IOException;
	File getDir(String filepath) throws IOException;
	
}

class Existing implements CreatedCheck {

	@Override
	public File getFile(File file) throws FileNotFoundException {
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		return file;	
	}
	
	@Override
	public File getFile(String filepath) throws FileNotFoundException {
		File file = new File(filepath);
		return getFile(file);
	}

	@Override
	public File getDir(String filepath) throws FileNotFoundException {
		return getFile(filepath);
	}	
}

class New implements CreatedCheck {

	@Override
	public File getFile(File file) throws FileAlreadyExistsException, IOException {
		if (file.exists()) {
			throw new FileAlreadyExistsException(file.getAbsolutePath());
		}
		file.createNewFile();
		return file;
	}
	
	@Override
	public File getFile(String filepath) throws IOException {
		File file = new File(filepath);
		return getFile(file);
	}

	@Override
	public File getDir(String filepath) throws FileAlreadyExistsException {
		File file = new File(filepath);
		if (file.exists()) {
			throw new FileAlreadyExistsException(file.getAbsolutePath());
		}
		file.mkdirs();
		return file;
	}
}

class Skip implements CreatedCheck {

	@Override
	public File getFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	@Override
	public File getFile(String filepath) throws IOException {
		File file = new File(filepath);
		return getFile(file);
	}

	@Override
	public File getDir(String filepath) throws IOException {
		File file = new File(filepath);
		file.createNewFile();
		return file;
	}
	
}

