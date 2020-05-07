package context;

import core.WorkSpaceEditor;
import menu.FileTab;

import java.io.File;

public class FileContext{
	 File file;
	 FileTab fileAndTab;

	 public FileContext(File file){
		  this.file = file;
		  fileAndTab=new FileTab(file);
	 }
     void open(){
			  WorkSpaceEditor.openNewTab(fileAndTab, false);
	 }
	 void copy(){
		  System.out.println("copied  file");
	 }
	 void cut(){
		  System.out.println("Cutted  from file");
	 }
	 void rename(){
		  System.out.println("renaming file");
	 }
	 void copyPath(){
		  System.out.println("Copied path of file");
	 }
}
