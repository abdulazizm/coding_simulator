package menu;

import core.ApplicationWindow;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class ApplicationMenu extends MenuBar{
	 static Menu FileMenu;

	 public ApplicationMenu(ApplicationWindow applicationWindow){
	 	 FileMenu=new FileMenu(applicationWindow);
	 	 getMenus().add(FileMenu);
	 }

}
