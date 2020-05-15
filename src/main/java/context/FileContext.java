package context;

import Utills.ClipBoard;
import core.WorkSpaceEditor;
import menu.FileMenu;
import menu.FileTab;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		  List<File> files=new ArrayList<>();
		  files.add(file);
		  ClipBoard.copyToClipBoard(files);
	 }
	 void cut(){
	 }
	 void rename(){
		  System.out.println("renaming file");
	 }
	 void copyPath(){
		  System.out.println("Copied path of file");
	 }

}
