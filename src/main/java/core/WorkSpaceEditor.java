package core;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import language.Language;
import menu.FileTab;
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
    private static TabPane workingAreaPane;
    private static String[] KEYWORDS;
    static{
        Language.importLanguageSpecs();
        KEYWORDS = Language.keywords;
    }


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




    public WorkSpaceEditor(TabPane l_workingAreaPane){
        workingAreaPane = l_workingAreaPane;
        workingAreaPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {

            }
        });
    }

    public static boolean openNewTab(FileTab fileTab, boolean intializeDefaultText) {
        if(!fileTab.isAlreadyOpened()){
            CodeArea area = getCodeArea();
            if (intializeDefaultText){
                area.appendText(Language.baseCodeString);
            } else{
                String filePath = fileTab.getFilePath();
                if (!filePath.isEmpty()){
                    try{
                        area.appendText(new String(Files.readAllBytes((new File(filePath)).toPath()),
                                "UTF-8"
                        ));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            area.setParagraphGraphicFactory(LineNumberFactory.get(area));
            fileTab.openTab(area);
        }


        if(workingAreaPane.getTabs().size() != 0) {
            workingAreaPane.getTabs().add((workingAreaPane.getTabs().size() - 1) ,fileTab.getTab());
            workingAreaPane.getSelectionModel().select(workingAreaPane.getTabs().size() - 2);
        }
        else {
            workingAreaPane.getTabs().add((workingAreaPane.getTabs().size()) ,fileTab.getTab());
            workingAreaPane.getSelectionModel().select(workingAreaPane.getTabs().size() - 1);
        }

        SplitPane.setResizableWithParent(workingAreaPane, false);

        return true;
    }



    private static CodeArea getCodeArea(){
        CodeArea area = new CodeArea();
        area.setStyle("-fx-font-size: 1em");
        area.getProperties().put("fontSize", 1);
        area.setParagraphGraphicFactory(LineNumberFactory.get(area, (digits) -> "%1$" + digits + "d"));
        area.getStylesheets().add(WorkSpaceEditor.class.getResource("/java-keywords.css").toExternalForm());
        area.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(() -> computeHighlightingAsync(area))
                .awaitLatest(area.richChanges())
                .filterMap(t -> {
                    if (t.isSuccess()){
                        return Optional.of(t.get());
                    } else{
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(f -> applyHighlighting(area, f));
        return area;
    }

    private static void applyHighlighting(CodeArea codeArea, StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

    private static Task<StyleSpans<Collection<String>>> computeHighlightingAsync(CodeArea codeArea) {
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





}
