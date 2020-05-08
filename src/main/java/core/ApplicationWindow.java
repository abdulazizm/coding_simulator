package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import language.Language;
import menu.ApplicationMenu;

import java.io.IOException;

public class ApplicationWindow extends Application{
	 private static Rectangle2D screenProperties;
	 private static String APPLICATION_TITLE = "Application";
	 private static SplitPane horizontalParentPane,verticalParentPane;
	 private static ScrollPane sp;
	 private static VBox mainLayout;
	 private static Scene scene;
	 public  static BorderPane  projectStructurePane;
	 private static TabPane workingAreaPane,outputPane;
	 private static double horizontalDividerRatio = 0.22,verticalDividerRatio=0.75;
	 public static Stage mainStage;
     public static ApplicationMenu menu;
     public static WorkSpaceEditor editor;

	@Override
	 public void start(Stage primaryStage){
		  mainStage=primaryStage;
	 	  mainLayout =new VBox();
	 	  menu=new ApplicationMenu(this);

		  screenProperties = Screen.getPrimary().getVisualBounds();

		  horizontalParentPane = new SplitPane();
		  horizontalParentPane.setOrientation(Orientation.HORIZONTAL);
		  horizontalParentPane.setDividerPosition(0, horizontalDividerRatio);
		  SplitPane.setResizableWithParent(horizontalParentPane,false);

		  verticalParentPane = new SplitPane();
		  verticalParentPane.setOrientation(Orientation.VERTICAL);
		  verticalParentPane.setDividerPosition(0, verticalDividerRatio);
		  SplitPane.setResizableWithParent(verticalParentPane,false);


		  projectStructurePane = new BorderPane();
		  SplitPane.setResizableWithParent(projectStructurePane, false);

		  workingAreaPane = new TabPane();
		  SplitPane.setResizableWithParent(workingAreaPane, false);

		  editor = new WorkSpaceEditor(workingAreaPane);

		  outputPane = new TabPane();
		  SplitPane.setResizableWithParent(outputPane, false);

		  horizontalParentPane.getItems().addAll(projectStructurePane, workingAreaPane);
		  verticalParentPane.getItems().addAll(horizontalParentPane, outputPane);

		  mainLayout.getChildren().add(menu);
		  mainLayout.getChildren().add(verticalParentPane);

		  verticalParentPane.setMinHeight(screenProperties.getHeight());
		  horizontalParentPane.setMinWidth(screenProperties.getWidth());


		  scene = new Scene(mainLayout);
		  primaryStage.setScene(scene);
		  primaryStage.setTitle(APPLICATION_TITLE);
		  primaryStage.setMaximized(true);
		  primaryStage.show();
	 }

	 public static void main(String[] args){
		  launch(args);
	 }
}

