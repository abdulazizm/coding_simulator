import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WorkSpaceEditor {

    private static Tab newTabButton;
    private static TabPane workingAreaPane;

    public WorkSpaceEditor(TabPane l_workingAreaPane) {
        workingAreaPane = l_workingAreaPane;
        try {
            openNewTab("HelloWorld-1.c","",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        newTabButton = new Tab("+");
        workingAreaPane.getTabs().add(newTabButton);
        workingAreaPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if(newTab == newTabButton) {
                    try {
                        openNewTab("HelloWorld-"+workingAreaPane.getTabs().size()+".c","",true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public boolean openNewTab(String tabName, String filePath, boolean intializeDefaultText) throws IOException  {
        CodeArea area = new CodeArea();

        if (intializeDefaultText){
            area.appendText("###########################################################\n" +
                    "### Filename:\n" +
                    "### Author:\n" +
                    "### Description:\n" +
                    "### Last Release Notes:\n" +
                    "###########################################################\n\n\n\n" + "#include <stdio.h>\n" +
                    "\n" +
                    "int main()\n" +
                    "{\n" +
                    "    printf(\"Hello World\");\n" +
                    "\n" +
                    "    return 0;\n" +
                    "}");
        } else {
            if(!filePath.isEmpty()) {
                area.appendText(new String(
                        Files.readAllBytes((new File(filePath)).toPath()),
                        "UTF-8"
                ));
            }
        }
        area.setParagraphGraphicFactory(LineNumberFactory.get(area));
        FileAndTab newFileTab = new FileAndTab(filePath,area);

        if(workingAreaPane.getTabs().size() != 0) {
            workingAreaPane.getTabs().add((workingAreaPane.getTabs().size() - 1) ,newFileTab.tab);
            workingAreaPane.getSelectionModel().select(workingAreaPane.getTabs().size() - 2);
        }
        else {
            workingAreaPane.getTabs().add((workingAreaPane.getTabs().size()) ,newFileTab.tab);
            workingAreaPane.getSelectionModel().select(workingAreaPane.getTabs().size() - 1);
        }

        SplitPane.setResizableWithParent(workingAreaPane, false);

        return true;
    }
}
