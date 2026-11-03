package ru.rsreu.pr5;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import java.util.regex.*;
import javafx.stage.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class CryptoApp extends Application {
    private final TextArea ciphertext = new TextArea();
    private final TextArea plaintext = new TextArea();
    private final TableView<SymbolRow> table = new TableView<>();
    private final TableView<GramRow> grams = new TableView<>();
    private final Label status = new Label();
    private final TextFlow cipherHighlighted = new TextFlow();
    private final TextFlow plainHighlighted = new TextFlow();
    private final TabPane cipherTabs = new TabPane();
    private final TabPane plainTabs = new TabPane();
    private String selectedCode = null;
    private final MappingService mappingService = new MappingService();
    private final Map<String,Character> mappings = mappingService.view();
    private final ObservableList<SymbolRow> rows = FXCollections.observableArrayList();
    private final ComboBox<String> analysisMode = new ComboBox<>(FXCollections.observableArrayList("Без пробела", "С пробелом"));


    @Override public void start(Stage stage) {
        stage.setTitle("ПР5 - Криптоанализ простой замены | Вариант 2");
        ciphertext.setWrapText(true);
        ciphertext.setPromptText("Загрузите криптограмму или вставьте двухзначные коды...");
        plaintext.setWrapText(true);
        plaintext.setEditable(false);
        plaintext.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");
        ciphertext.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 13px;");

        Button loadVariant = new Button("Вариант №2");
        loadVariant.setOnAction(e -> loadDefault());
        Button analyze = new Button("Анализировать");
        analyze.setOnAction(e -> analyze());
        analysisMode.getSelectionModel().select("Без пробела");
        analysisMode.setTooltip(new Tooltip("Определяет, разрешено ли назначать пробел при автоподборе. Ручные замены не ограничиваются."));
        analysisMode.setOnAction(e -> refresh());
        Button suggest = new Button("Автоподбор по частотам");
        suggest.setOnAction(e -> {applySuggestions(); refresh();});
        Button clear = new Button("Сбросить замены");
        clear.setOnAction(e -> {if(!mappings.isEmpty()){mappingService.clear();refresh();}});
        Button reference = new Button("Частоты русского языка...");
        reference.setOnAction(e -> ReferenceWindow.show(stage));
        Button swap = new Button("Поменять местами (2 строки)");
        swap.setOnAction(e -> swapSelected());
        Button undo = new Button("Отменить (Ctrl+Z)");
        undo.setOnAction(e -> undoChange());
        HBox tools=new HBox(8,loadVariant,analyze,new Label("Режим:"),analysisMode,suggest,clear,swap,undo,reference);
        tools.setSpacing(6);

        tools.setAlignment(Pos.CENTER_LEFT);
        tools.setPadding(new Insets(12));
        tools.setStyle("-fx-background-color: #eef3f8;");

        TableColumn<SymbolRow,String> code = new TableColumn<>("Код");
        code.setCellValueFactory(v->v.getValue().codeProperty()); code.setPrefWidth(65);
        TableColumn<SymbolRow,Number> count = new TableColumn<>("Количество");
        count.setCellValueFactory(v->v.getValue().countProperty());count.setPrefWidth(105);
        TableColumn<SymbolRow,String> freq = new TableColumn<>("Частота");
        freq.setCellValueFactory(v->v.getValue().frequencyProperty());freq.setPrefWidth(90);
        TableColumn<SymbolRow,String> letter = new TableColumn<>("Замена ↴");
        letter.setCellValueFactory(v->v.getValue().letterProperty());letter.setPrefWidth(105);
        letter.setCellFactory(c -> new TableCell<>() {
            private final Button selector = new Button();
            {
                selector.setMaxWidth(Double.MAX_VALUE);
                selector.setOnAction(e -> {
                    SymbolRow row = getTableRow() == null ? null : getTableRow().getItem();
                    if(row != null) CharacterPicker.show(selector, row.getCode(), mappings, CryptoApp.this::setMapping);
                });
            }
            @Override protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                if(empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null); setText(null); return;
                }
                selector.setText(value == null || value.isEmpty() ? "—" : value.equals(" ") ? "␣" : value);
                setGraphic(selector); setText(null);
            }
        });
        table.getColumns().addAll(code,count,freq,letter);
        table.setItems(rows);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        table.getSelectionModel().getSelectedItems().addListener((ListChangeListener<SymbolRow>) change -> {
            SymbolRow focused = table.getSelectionModel().getSelectedItem();
            selectedCode = focused == null ? null : focused.getCode();
            updateHighlight();
        });
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Нажмите «Анализировать»"));

        TableColumn<GramRow,String> gram = new TableColumn<>("Сочетание кодов");
        gram.setCellValueFactory(v->v.getValue().gramProperty());
        TableColumn<GramRow,String> gramDecoded = new TableColumn<>("Расшифровка");
        gramDecoded.setCellValueFactory(v->v.getValue().decodedProperty());
        gramDecoded.setPrefWidth(120);
        TableColumn<GramRow,Number> times = new TableColumn<>("Раз");
        times.setCellValueFactory(v->v.getValue().countProperty());times.setPrefWidth(55);
        grams.getColumns().addAll(gram,gramDecoded,times);
        grams.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ComboBox<Integer> nSelect=new ComboBox<>(FXCollections.observableArrayList(2,3));
        nSelect.setValue(2);
        nSelect.valueProperty().addListener((o,a,b)->updateGrams(b));
        VBox gramsBox=new VBox(6,new Label("Частые сочетания кодов"),new HBox(7,new Label("Длина:"),nSelect),grams);
        VBox.setVgrow(grams,Priority.ALWAYS);
        VBox codesBox=new VBox(7,new Label("Частоты и замены (выберите букву в последнем столбце)"),table);
        VBox.setVgrow(table,Priority.ALWAYS);
        SplitPane right=new SplitPane(codesBox,gramsBox);
        right.setOrientation(javafx.geometry.Orientation.VERTICAL);
        right.setDividerPositions(0.64);
        VBox.setVgrow(right,Priority.ALWAYS);

        Tab cipherEdit = new Tab("Исходный текст (редактирование)", ciphertext);
        Tab cipherView = new Tab("Подсветка кодов", highlightedScroll(cipherHighlighted));
        cipherEdit.setClosable(false); cipherView.setClosable(false);
        cipherTabs.getTabs().addAll(cipherEdit, cipherView);
        Tab plainText = new Tab("Текст", plaintext);
        Tab plainView = new Tab("Подсветка", highlightedScroll(plainHighlighted));
        plainText.setClosable(false); plainView.setClosable(false);
        plainTabs.getTabs().addAll(plainText,plainView);
        Label highlightHint = new Label("Выделите код в правой таблице: все его вхождения подсветятся в обеих вкладках «Подсветка».");
        highlightHint.setStyle("-fx-text-fill: #52677b; -fx-font-size: 11px;");
        VBox inputBox=new VBox(6,new Label("Криптограмма"),cipherTabs);
        VBox outputBox=new VBox(6,new Label("Результат расшифровки (· — неизвестная буква)"),plainTabs,highlightHint);
        VBox.setVgrow(cipherTabs,Priority.ALWAYS);
        VBox.setVgrow(plainTabs,Priority.ALWAYS);
        SplitPane left=new SplitPane(inputBox,outputBox);
        left.setOrientation(javafx.geometry.Orientation.VERTICAL);
        left.setDividerPositions(0.48);
        SplitPane body=new SplitPane(left,right);
        body.setDividerPositions(0.64);
        VBox.setVgrow(body,Priority.ALWAYS);
        status.setPadding(new Insets(7,14,10,14));
        status.setStyle("-fx-text-fill: #3d5064;");
        VBox root=new VBox(tools,body,status);
        root.setPadding(new Insets(0,8,0,8));
        Scene scene=new Scene(root,1370,755);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN), this::undoChange);

        stage.setScene(scene);stage.show();
        loadDefault();
    }

    private void loadDefault(){
        try (InputStream in=getClass().getResourceAsStream("/variant2.txt")) {
            if(in==null)throw new IOException("Ресурс variant2.txt отсутствует");
            ciphertext.setText(new String(in.readAllBytes(),StandardCharsets.UTF_8));
            mappingService.resetHistory();mappingService.replace(Map.of());mappingService.resetHistory();analyze();
        }catch(IOException e){error("Не удалось загрузить вариант 2",e.getMessage());}
    }
    private void analyze(){
        Map<String,Long> count=CryptoAnalyzer.counts(ciphertext.getText());
        long total=count.values().stream().mapToLong(Long::longValue).sum();
        mappingService.retainCodes(count.keySet());
        rows.clear();
        selectedCode = null;
        for(var e:count.entrySet()) rows.add(new SymbolRow(e.getKey(),e.getValue(),
                String.format(Locale.forLanguageTag("ru"),"%.2f%%",100.0*e.getValue()/total),
                mappings.get(e.getKey())));
        refresh();
        updateGrams(grams.getItems().isEmpty()?2:currentN);
    }
    private int currentN=2;
    private void updateGrams(int n){
        currentN=n;
        grams.getItems().clear();
        CryptoAnalyzer.ngrams(ciphertext.getText(),n).forEach((s,v)->grams.getItems().add(new GramRow(s,v)));
        refreshGramDecodings();
    }
    private void refreshGramDecodings() {
        for (GramRow row : grams.getItems()) {
            StringBuilder decoded = new StringBuilder();
            for (String code : row.getGram().split(" ")) {
                Character ch = mappings.get(code);
                decoded.append(ch == null ? '·' : ch == ' ' ? '␣' : ch);
            }
            row.setDecoded(decoded.toString());
        }
    }
    private void applySuggestions(){
        mappingService.replace(CryptoAnalyzer.suggestedMappings(ciphertext.getText(), "С пробелом".equals(analysisMode.getValue())));
    }
    private void refresh(){
        for(SymbolRow r:rows){
            Character ch=mappings.get(r.getCode());
            r.setLetter(ch==null?"":String.valueOf(ch));
        }
        table.refresh();
        refreshGramDecodings();
        plaintext.setText(CryptoAnalyzer.decrypt(ciphertext.getText(),mappings));
        updateHighlight();
        status.setText("Символов: "+CryptoAnalyzer.symbols(ciphertext.getText()).size()+
                "     Различных кодов: "+rows.size()+"     Установлено замен: "+mappings.size()+
                "     Режим: "+analysisMode.getValue()+"     Пробел: "+mappings.entrySet().stream().filter(e->e.getValue()==' ').map(Map.Entry::getKey).findFirst().orElse("не найден"));
    }
    private void setMapping(String code, Character letter) {
        mappingService.set(code,letter);
        refresh();
    }
    private void swapSelected() {
        List<SymbolRow> selected = new ArrayList<>(table.getSelectionModel().getSelectedItems());
        if(selected.size()!=2) {
            new Alert(Alert.AlertType.INFORMATION,"Выберите ровно две строки с Ctrl.",ButtonType.OK).showAndWait();
            return;
        }
        mappingService.swap(selected.get(0).getCode(),selected.get(1).getCode());
        refresh();
    }
    private void undoChange() { if(mappingService.undo())refresh(); }

    private ScrollPane highlightedScroll(TextFlow flow) {
        flow.setPadding(new Insets(10));
        flow.setLineSpacing(3);
        ScrollPane pane = new ScrollPane(flow);
        pane.setFitToWidth(true);
        pane.setStyle("-fx-background: white; -fx-background-color: white;");
        pane.viewportBoundsProperty().addListener((obs, old, bounds) ->
                flow.setPrefWidth(Math.max(0, bounds.getWidth() - 18)));
        return pane;
    }

    private void updateHighlight() {
        CodeHighlighter.update(ciphertext.getText(),plaintext.getText(),selectedCode,cipherHighlighted,plainHighlighted);
        if(selectedCode!=null){
            cipherTabs.getSelectionModel().select(1);
            plainTabs.getSelectionModel().select(1);
        }
    }
    private void error(String title,String message){new Alert(Alert.AlertType.ERROR,title+"\n"+message,ButtonType.OK).showAndWait();}
    public static void main(String[] args){launch(args);}


}
