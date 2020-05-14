package context;

import core.WorkSpaceEditor;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import menu.FileTab;

import java.awt.*;
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
		  WorkSpaceEditor.clipboardContent.putFiles(files);
		  WorkSpaceEditor.clipboard.setContent(WorkSpaceEditor.clipboardContent);
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
