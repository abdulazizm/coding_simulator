package menu;

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

import static javafx.scene.control.ButtonType.*;

public class FileTab{
    public Tab tab;
    public File file;
    public boolean isClosed;
    String tabName = "";
    public CodeArea area;

    public FileTab(File file){
        this.file = file;
        isClosed=true;
    }

    public void openTab(CodeArea codeArea){
        if(isClosed){
            isClosed=false;
            area = codeArea;
            String filePath = file.getPath();
            if (!filePath.isEmpty()){
                file = new File(filePath);
            } else{
                file = new File(System.getProperty("user.dir") + "/HelloWorld" + new Random(System.currentTimeMillis()).nextInt(255) + ".c");
            }
            tabName = file.getName();
            tab = new Tab(tabName, area);
        }

        tab.setOnCloseRequest(new EventHandler<javafx.event.Event>(){
            @Override
            public void handle(Event t){
                isClosed=true;
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
    }

    public String getFilePath(){
        return file.getPath();
    }

    public Tab getTab(){
        return tab;
    }

    public boolean isAlreadyOpened(){
        return isClosed==false;
    }
}
