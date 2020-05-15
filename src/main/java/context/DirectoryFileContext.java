package context;


import Utills.ClipBoard;
import Utills.FileOps;
import menu.FileMenu;

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
//			   Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//			   Object data=clipboard.getData(DataFlavor.javaFileListFlavor);
//			   System.out.println(data);
//			   List<File> files=(List<File>) data;
			   List<File> files= ClipBoard.pasteFromClipBoard();
			   if(files==null){return;}
			   for(File e:files){
					FileOps.copyTo(e,file);
			   }
			   FileMenu.setNodesForDirectory(FileMenu.directory);

		  }
		  catch (ClassCastException | IOException ex) {
		  	 		System.out.println("file exception  .."+ex);
		  }

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
