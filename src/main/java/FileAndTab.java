import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

public class FileAndTab {
    public Tab tab;
    public File file;
    String tabName = "";
    public CodeArea area;

    public FileAndTab(String filePath, CodeArea l_area) {
        area = l_area;

        if (!filePath.isEmpty()) {
            file = new File(filePath);
        } else {
            file = new File(System.getProperty("user.dir")+"/HelloWorld"+new Random(System.currentTimeMillis()).nextInt(255)+".c");
        }

        tabName = file.getName();
        tab = new Tab(tabName, area);

        tab.setOnCloseRequest(new EventHandler<javafx.event.Event>() {
            @Override
            public void handle(Event t) {
                Alert areYouSureAlert = new Alert(Alert.AlertType.CONFIRMATION, "Project Modified? Save?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = areYouSureAlert.showAndWait();
                if (result.isEmpty() || result.get() != ButtonType.YES) {
                    t.consume();
                } else {
                    result.get();
                    save();
                }
            }
        });

    }

    private void save() {

        try {
            FileOutputStream fos = new FileOutputStream(file);
            String text = area.getText();
            byte[] mybytes = text.getBytes();
            fos.write(mybytes);
            fos.flush();
            fos.close();
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }
    };
}