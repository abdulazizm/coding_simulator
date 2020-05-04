import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        area.setStyle("-fx-font-size: 1em");
        area.getProperties().put("fontSize", 1);
        area.setParagraphGraphicFactory(LineNumberFactory.get(area, (digits) -> "%1$" + digits + "d"));
        area.getStylesheets().add(getClass().getResource("java-keywords.css").toExternalForm());
        area.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(() -> computeHighlightingAsync(area))
                .awaitLatest(area.richChanges())
                .filterMap(t -> {
                    if (t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(f -> applyHighlighting(area, f));


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

    private void applyHighlighting(CodeArea codeArea, StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync(CodeArea codeArea) {
        String text = codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        Executors.newSingleThreadExecutor().execute(task);
        return task;
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("PREPROCESSORS") != null ? "preprocessors" :
                                                                        matcher.group("COMMENT") != null ? "comment" :
                                                                                null; /* never happens */
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }


    private static final String[] KEYWORDS = new String[]{
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while",

            "auto","signed","unsigned","extern","register","sizeof","struct","typedef","union"
};

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String PREPROCESSORS_PATTERN = "#[^\n]*";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<PREPROCESSORS>" + PREPROCESSORS_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );
}
