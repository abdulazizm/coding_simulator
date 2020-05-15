package Utills;

import core.WorkSpaceEditor;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.File;
import java.util.List;

public class ClipBoard{
	 public  static Clipboard clipboard;
	 public  static ClipboardContent clipboardContent;

	 static{
		  clipboard = Clipboard.getSystemClipboard();
		  clipboardContent = new ClipboardContent();
	 }

	 public static void copyToClipBoard(List<File> files){
		  clipboardContent.putFiles(files);
		  clipboard.setContent(clipboardContent);
	 }

	 public static List<File> pasteFromClipBoard(){
		  List<File> files=clipboard.getFiles();
		  System.out.println(files);
		  return files;
	 }


}
