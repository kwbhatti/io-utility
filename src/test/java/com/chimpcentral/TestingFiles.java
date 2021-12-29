package com.chimpcentral;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.chimpcentral.ExcelFile;
import com.chimpcentral.FileStatus;
import com.chimpcentral.FlatFile;
import com.chimpcentral.Folder;
//import com.chimpcentral.NinjaFile;
//import com.chimpcentral.Folder.FolderStatus;
//import com.chimpcentral.NinjaFile.FileStatus;
import com.chimpcentral.FolderStatus;

public class TestingFiles {
	
	public void testExcelFile() throws InvalidFormatException, IOException {
		String filepath = System.getProperty("user.dir") + "/src/test/resources/com/chimpcentral/testfile.xlsx";

		ExcelFile file3 = new ExcelFile(filepath, FileStatus.EXISTING);
		filepath = System.getProperty("user.dir") + "/target/peoplefilecopy1.xlsx";

		file3.createCopy(filepath);
		ExcelFile file4 = new ExcelFile(filepath, FileStatus.EXISTING);
		
		file4.createSheet("people2");
		file4.createSheet("people3");

		
		file4.flush();
		ExcelFile file5 = file4.getCopy(filepath.replace("1", "2"));
		file4.delete();
		System.out.println("done");
		file5.createSheet("people4");
		file5.flush();
	}
	
	public void testFlatFile() throws IOException {
		String filepath = System.getProperty("user.dir") + "/src/test/resources/com/chimpcentral/testfile.txt";
		FlatFile flatFile = new FlatFile(filepath, FileStatus.EXISTING);
		flatFile.createCopy(System.getProperty("user.dir") + "testfile");
		String content = flatFile.getContent();
		System.out.println(content);
		System.out.println("********************************");
		flatFile.appendContent("\n\nThis is the updated line 1");
		System.out.println(flatFile.getContent());
		System.out.println("********************************");
		flatFile.overwriteContent("this is overwrite");
		System.out.println(flatFile.getContent());
		System.out.println("********************************");
		flatFile.clearContent();
		System.out.println(flatFile.getContent());
		System.out.println("********************************");
		flatFile.delete();
		flatFile.deleteIfExist();
		FlatFile flatFile2 = new FlatFile(System.getProperty("user.dir") + "testfilecreate", FileStatus.NEW);
		System.out.println(flatFile2.getContent());
		System.out.println("********************************");
		flatFile2.appendContent("something");
		String filepathff1 = System.getProperty("user.dir") + "testfileff1";
		String filepathff2 = System.getProperty("user.dir") + "testfileff2";
		
		new FlatFile(filepathff1, FileStatus.NEW)
				.appendContent("This is from file 1").appendContent("\n")
				.getCopy(filepathff2).appendContent("This is from file 2");
	}
	
	public static void main(String[] args) throws IOException, InvalidFormatException {
		new TestingFiles().testExcelFile();
		new TestingFiles().testFlatFile();
		new Folder(System.getProperty("user.dir") + "/target/folder", FolderStatus.NEW);
		new FlatFile(System.getProperty("user.dir") + "/target/folder/new", FileStatus.NEW);
	}
	
}
