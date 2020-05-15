package Utills;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class FileOps{

	 static public void copyTo(File from,File to) throws IOException{
		  if(from.isDirectory()){
			   FileUtils.copyDirectoryToDirectory(from,to);
		  }
		  else if(from.isFile()){
		  	   FileUtils.copyFileToDirectory(from,to);
		  }
	 }
}
