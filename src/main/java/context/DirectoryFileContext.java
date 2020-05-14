package context;


import FileUtills.FileOps;
import menu.FileMenu;
import org.apache.commons.io.FileUtils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DirectoryFileContext extends FileContext{

	 public DirectoryFileContext(File file){
		  super(file);
	 }

	 void createNew(){
	 	 System.out.println("Created new from directory");
	 }

	 void paste(){
		  try {
			   Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			   Object data=clipboard.getData(DataFlavor.javaFileListFlavor);
			   List<File> files=(List<File>) data;
			   for(File e:files){
					FileOps.copyTo(e,file);
			   }
			   FileMenu.setNodesForDirectory(FileMenu.directory);

		  }
		  catch (UnsupportedFlavorException| ClassCastException | IOException ex) {
		  	 		System.out.println("file exception  .."+ex);
		  }

	 }


	 @Override
	 void cut(){
		  System.out.println("Cutted  from directory");
	 }

	 @Override
	 void rename(){
		  System.out.println("Renaming directory");
	 }

	 @Override
	 void copyPath(){
		  System.out.println("Copied path of directory");
	 }
}
