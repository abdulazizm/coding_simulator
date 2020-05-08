package context;

import java.io.File;
import java.nio.file.Files;

public class DirectoryFileContext extends FileContext{

	 public DirectoryFileContext(File file){
		  super(file);
	 }

	 void createNew(){
	 	 System.out.println("Created new from directory");
	 }

	 void paste(){
		  System.out.println("pasted in directory");
	 }

	 @Override
	 void copy(){
		  System.out.println("Copied from directory");
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
