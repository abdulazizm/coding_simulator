package context;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class ContextmenuFactory{


	 public static void setContextMenuForDirectory(Label label,String path){
          DirectoryFileContext contextmenu=new DirectoryFileContext(new File(path));

	 	  MenuItem newItem=new MenuItem("New");
		  MenuItem cut = new MenuItem("Cut");
		  MenuItem copy = new MenuItem("Copy");
		  MenuItem copyPath = new MenuItem("Copy Path");
		  MenuItem paste = new MenuItem("Paste");
		  MenuItem rename=new MenuItem("Rename");

		  ContextMenu contextMenu=new ContextMenu();
		  contextMenu.getItems().addAll(newItem,cut,copy,copyPath,paste);
          label.setContextMenu(contextMenu);

          label.setOnMouseClicked(new EventHandler<MouseEvent>() {
			   @Override
			   public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
						 if(mouseEvent.getClickCount() == 2){

						 }
					}
			   }
		  });

          newItem.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					contextmenu.createNew();
			   }
		  });

          cut.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					contextmenu.cut();
			   }
		  });

          copy.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					contextmenu.copy();
			   }
		  });

          copyPath.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					contextmenu.copyPath();
			   }
		  });

          paste.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					contextmenu.paste();
			   }
		  });

          rename.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					contextmenu.rename();
			   }
		  });
	 }

	 public static void setContextMenuForFile(Label label,String path){
		  FileContext fileContext =new FileContext(new File(path));

		  MenuItem newItem=new MenuItem("New");
		  MenuItem cut = new MenuItem("Cut");
		  MenuItem copy = new MenuItem("Copy");
		  MenuItem copyPath = new MenuItem("Copy Path");
		  MenuItem paste = new MenuItem("Paste");
		  MenuItem rename=new MenuItem("Rename");

		  ContextMenu contextMenu=new ContextMenu();
		  contextMenu.getItems().addAll(newItem,cut,copy,copyPath,paste);
		  label.setContextMenu(contextMenu);

		  label.setOnMouseClicked(new EventHandler<MouseEvent>() {
			   @Override
			   public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
						 if(mouseEvent.getClickCount() == 2){
								fileContext.open();
						 }
					}
			   }
		  });


		  cut.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					fileContext.cut();
			   }
		  });

		  copy.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					fileContext.copy();
			   }
		  });

		  copyPath.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					fileContext.copyPath();
			   }
		  });

		  rename.setOnAction(new EventHandler<ActionEvent>(){
			   @Override
			   public void handle(ActionEvent actionEvent){
					fileContext.rename();
			   }
		  });
	 }
}
