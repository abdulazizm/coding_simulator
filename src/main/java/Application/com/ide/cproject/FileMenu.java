package Application.com.ide.cproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class FileMenu extends Menu{
	 static String FILE_MENU="File";
	 static MenuItem newProject;
	 ApplicationWindow applicationWindow;

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
                   	   TreeView<String> mainDirectoryView=new TreeView<>();
                       mainDirectoryView.setRoot(getNodesForDirectory(choice));
                       applicationWindow.projectStructurePane.setCenter(mainDirectoryView);
				   }
			  }
		 });
		  getItems().add(newProject);
	 }


	 public TreeItem<String> getNodesForDirectory(File directory) {
	 	 //Returns a TreeItem representation of the specified directory
		  TreeItem<String> root = new TreeItem<String>(directory.getName());
		  for(File f : directory.listFiles()) {
			   if(f.isDirectory()) { //Then we call the function recursively
					root.getChildren().add(getNodesForDirectory(f));
			   } else {
					root.getChildren().add(new TreeItem<String>(f.getName()));
			   }
		  }
		  return root;
	 }

}





