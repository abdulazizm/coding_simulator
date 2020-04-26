package Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import menu.FileMenu;

import java.io.File;

public class ApplicationMenu extends MenuBar{
	 static Menu FileMenu;

	 ApplicationMenu(ApplicationWindow applicationWindow){
	 	 FileMenu=new FileMenu(applicationWindow);
	 	 getMenus().add(FileMenu);
	 }

}
