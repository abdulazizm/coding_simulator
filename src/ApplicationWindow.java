import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

//Some Reference : https://stackoverflow.com/questions/17393691/how-to-resize-components-when-tab-pane-is-resized-with-mouse-drag


public class ApplicationWindow extends Application{
	 private static String APPLICATION_TITLE = "Application";
	 private static SplitPane horizontalParentPane,verticalParentPane;
	 private static Scene scene;
	 private static TabPane workingAreaPane, projectStructurePane,outputPane;
	 private static double horizontalDividerRatio = 0.22,verticalDividerRatio=0.75;


	 @Override
	 public void start(Stage primaryStage){
		  horizontalParentPane = new SplitPane();
		  horizontalParentPane.setOrientation(Orientation.HORIZONTAL);
		  horizontalParentPane.setDividerPosition(0, horizontalDividerRatio);
		  SplitPane.setResizableWithParent(horizontalParentPane,false);

		  verticalParentPane = new SplitPane();
		  verticalParentPane.setOrientation(Orientation.VERTICAL);
		  verticalParentPane.setDividerPosition(0, verticalDividerRatio);
		  SplitPane.setResizableWithParent(verticalParentPane,false);


		  projectStructurePane = new TabPane();
		  SplitPane.setResizableWithParent(projectStructurePane, false);

		  workingAreaPane = new TabPane();
		  SplitPane.setResizableWithParent(workingAreaPane, false);

		  outputPane = new TabPane();
		  SplitPane.setResizableWithParent(outputPane, false);

		  horizontalParentPane.getItems().addAll(projectStructurePane, workingAreaPane);
		  verticalParentPane.getItems().addAll(horizontalParentPane, outputPane);

		  scene = new Scene(verticalParentPane);
		  primaryStage.setScene(scene);
		  primaryStage.setTitle(APPLICATION_TITLE);
		  primaryStage.setMaximized(true);
		  primaryStage.show();
	 }


	 public static void main(String[] args){
		  launch(args);
	 }
}

