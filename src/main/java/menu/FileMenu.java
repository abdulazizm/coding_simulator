package menu;

import context.ContextmenuFactory;
import core.ApplicationWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class FileMenu extends Menu{
	 static String FILE_MENU="File";
	 static MenuItem newProject;
	 public static File directory;
	 static ApplicationWindow applicationWindow;

	 public FileMenu(ApplicationWindow applicationWindow){
	 	 super(FILE_MENU);
	 	 this.applicationWindow=applicationWindow;

	 	 newProject=new MenuItem("New Project");
	 	 newProject.setOnAction(new EventHandler<ActionEvent>(){
			  @Override
			  public void handle(ActionEvent actionEvent){
				   DirectoryChooser dc = new DirectoryChooser();
				   dc.setInitialDirectory(new File(System.getProperty("user.home")));
				   File choice = dc.showDialog(applicationWindow.mainStage);
                   if(choice!=null){
                   	   directory=choice;
                       setNodesForDirectory(directory);
				   }
			  }
		 });

		 getItems().add(newProject);
	 }

	 public static void setNodesForDirectory(File directory){
		  TreeView<Label> mainDirectoryView=new TreeView<>();
		  mainDirectoryView.setRoot(getNodesForDirectory(directory));
		  applicationWindow.projectStructurePane.setCenter(mainDirectoryView);
	 }


	 public  static TreeItem<Label> getNodesForDirectory(File directory) {
		  Label label=new Label(directory.getName());
		  TreeItem<Label> root = new TreeItem<Label>(label);
		  ContextmenuFactory.setContextMenuForDirectory(label,directory.getAbsolutePath());
		  for(File f : directory.listFiles()) {
			   if(f.isDirectory()) {
					root.getChildren().add(getNodesForDirectory(f));
			   } else {
					label=new Label(f.getName());
					root.getChildren().add(new TreeItem<Label>(label));
					ContextmenuFactory.setContextMenuForFile(label,f.getAbsolutePath());
			   }
		  }
		  return root;
	 }

}





