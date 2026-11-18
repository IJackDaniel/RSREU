package ru.rsreu.pr5.controller;

import ru.rsreu.pr5.model.GramRow;
import ru.rsreu.pr5.model.SymbolRow;
import ru.rsreu.pr5.service.CryptoAnalyzer;
import ru.rsreu.pr5.service.MappingService;
import ru.rsreu.pr5.view.CharacterPicker;
import ru.rsreu.pr5.view.CodeHighlighter;
import ru.rsreu.pr5.view.ReferenceWindow;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/** Обрабатывает действия пользователя и связывает FXML с алгоритмами. */
public class MainController {
    @FXML private SplitPane bodySplit;
    @FXML private SplitPane leftSplit;
    @FXML private SplitPane rightSplit;
    @FXML private TextArea ciphertext;
    @FXML private TextArea plaintext;
    @FXML private TableView<SymbolRow> table;
    @FXML private TableView<GramRow> grams;
    @FXML private TableColumn<SymbolRow, String> codeColumn;
    @FXML private TableColumn<SymbolRow, Number> countColumn;
    @FXML private TableColumn<SymbolRow, String> frequencyColumn;
    @FXML private TableColumn<SymbolRow, String> letterColumn;
    @FXML private TableColumn<GramRow, String> gramColumn;
    @FXML private TableColumn<GramRow, String> decodedColumn;
    @FXML private TableColumn<GramRow, Number> timesColumn;
    @FXML private ComboBox<String> analysisMode;
    @FXML private ComboBox<Integer> nSelect;
    @FXML private Label status;
    @FXML private TextFlow cipherHighlighted;
    @FXML private TextFlow plainHighlighted;
    @FXML private ScrollPane cipherScroll;
    @FXML private ScrollPane plainScroll;
    @FXML private TabPane cipherTabs;
    @FXML private TabPane plainTabs;

    private Stage stage;
    private String selectedCode;
    private int currentN = 2;
    private final MappingService mappingService = new MappingService();
    private final Map<String, Character> mappings = mappingService.view();
    private final ObservableList<SymbolRow> rows = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        analysisMode.setItems(FXCollections.observableArrayList("Без пробела", "С пробелом"));
        analysisMode.setValue("Без пробела");
        analysisMode.setTooltip(new Tooltip("Определяет, разрешено ли назначать пробел при автоподборе. Ручные замены не ограничиваются."));

        codeColumn.setCellValueFactory(v -> v.getValue().codeProperty());
        countColumn.setCellValueFactory(v -> v.getValue().countProperty());
        frequencyColumn.setCellValueFactory(v -> v.getValue().frequencyProperty());
        letterColumn.setCellValueFactory(v -> v.getValue().letterProperty());

        // В колонке замен показываем кнопку; выбор буквы обрабатывает PickerController.
        letterColumn.setCellFactory(c -> new TableCell<>() {
            private final Button selector = new Button();
            {
                selector.setMaxWidth(Double.MAX_VALUE);
                selector.setOnAction(e -> {
                    SymbolRow row = getTableRow() == null ? null : getTableRow().getItem();
                    if (row != null) CharacterPicker.show(selector, row.getCode(), mappings, MainController.this::setMapping);
                });
            }
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }
                selector.setText(value == null || value.isEmpty() ? "—" : value.equals(" ") ? "␣" : value);
                setGraphic(selector);
                setText(null);
            }
        });
        table.setItems(rows);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getSelectionModel().getSelectedItems().addListener((ListChangeListener<SymbolRow>) change -> {
            SymbolRow focused = table.getSelectionModel().getSelectedItem();
            selectedCode = focused == null ? null : focused.getCode();
            updateHighlight();
        });
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Нажмите «Анализировать»"));

        gramColumn.setCellValueFactory(v -> v.getValue().gramProperty());
        decodedColumn.setCellValueFactory(v -> v.getValue().decodedProperty());
        timesColumn.setCellValueFactory(v -> v.getValue().countProperty());
        grams.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nSelect.setItems(FXCollections.observableArrayList(2, 3));
        nSelect.setValue(2);
        nSelect.valueProperty().addListener((observable, oldN, newN) -> {
            if (newN != null) updateGrams(newN);
        });
        Platform.runLater(() -> {
            bodySplit.setDividerPositions(0.64);
            leftSplit.setDividerPositions(0.48);
            rightSplit.setDividerPositions(0.64);
        });
        fitHighlightWidth(cipherScroll, cipherHighlighted);
        fitHighlightWidth(plainScroll, plainHighlighted);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        Scene scene = stage.getScene();
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN), this::undoChange);
    }

    // Загрузка стандартного текста (вариант 2)
    @FXML
    public void loadDefault() {
        try (InputStream in = getClass().getResourceAsStream("/variant2.txt")) {
            if (in == null) throw new IOException("Ресурс variant2.txt отсутствует");
            ciphertext.setText(new String(in.readAllBytes(), StandardCharsets.UTF_8));
            mappingService.resetHistory();
            mappingService.replace(Map.of());
            mappingService.resetHistory();
            analyze();
        } catch (IOException e) {
            error("Не удалось загрузить вариант 2", e.getMessage());
        }
    }

    // Подсчёт частот
    @FXML
    private void analyze() {
        Map<String, Long> count = CryptoAnalyzer.counts(ciphertext.getText());
        long total = count.values().stream().mapToLong(Long::longValue).sum();
        mappingService.retainCodes(count.keySet());
        rows.clear();
        selectedCode = null;
        for (var entry : count.entrySet()) {
            String percentage = String.format(Locale.forLanguageTag("ru"), "%.2f%%", 100.0 * entry.getValue() / total);
            rows.add(new SymbolRow(entry.getKey(), entry.getValue(), percentage, mappings.get(entry.getKey())));
        }
        refresh();
        updateGrams(currentN);
    }

    private void updateGrams(int n) {
        currentN = n;
        grams.getItems().clear();
        CryptoAnalyzer.ngrams(ciphertext.getText(), n)
                .forEach((codes, times) -> grams.getItems().add(new GramRow(codes, times)));
        refreshGramDecodings();
    }

    private void refreshGramDecodings() {
        for (GramRow row : grams.getItems()) {
            StringBuilder decoded = new StringBuilder();
            for (String code : row.getGram().split(" ")) {
                Character letter = mappings.get(code);
                decoded.append(letter == null ? '·' : letter == ' ' ? '␣' : letter);
            }
            row.setDecoded(decoded.toString());
        }
    }

    // Строим первый вариант замен.
    @FXML
    private void applySuggestions() {
        boolean includeSpace = "С пробелом".equals(analysisMode.getValue());
        mappingService.replace(CryptoAnalyzer.suggestedMappings(ciphertext.getText(), includeSpace));
        refresh();
    }

    @FXML
    private void clearMappings() {
        if (!mappings.isEmpty()) {
            mappingService.clear();
            refresh();
        }
    }

    // Обновляем текст по текущей таблице замен.
    @FXML
    private void refresh() {
        for (SymbolRow row : rows) {
            Character letter = mappings.get(row.getCode());
            row.setLetter(letter == null ? "" : String.valueOf(letter));
        }
        table.refresh();
        refreshGramDecodings();
        plaintext.setText(CryptoAnalyzer.decrypt(ciphertext.getText(), mappings));
        updateHighlight();
        status.setText("Символов: " + CryptoAnalyzer.symbols(ciphertext.getText()).size()
                + "     Различных кодов: " + rows.size()
                + "     Установлено замен: " + mappings.size()
                + "     Режим: " + analysisMode.getValue()
                + "     Пробел: " + mappings.entrySet().stream()
                        .filter(e -> e.getValue() == ' ')
                        .map(Map.Entry::getKey).findFirst().orElse("не найден"));
    }

    // Сохраняем замену с возможностью отмены.
    private void setMapping(String code, Character letter) {
        mappingService.set(code, letter);
        refresh();
    }

    @FXML
    private void swapSelected() {
        List<SymbolRow> selected = new ArrayList<>(table.getSelectionModel().getSelectedItems());
        if (selected.size() != 2) {
            new Alert(Alert.AlertType.INFORMATION, "Выберите ровно две строки с Ctrl.", ButtonType.OK).showAndWait();
            return;
        }
        mappingService.swap(selected.get(0).getCode(), selected.get(1).getCode());
        refresh();
    }

    @FXML
    private void undoChange() {
        if (mappingService.undo()) refresh();
    }

    @FXML
    private void showReference() {
        ReferenceWindow.show(stage);
    }

    private void fitHighlightWidth(ScrollPane scroll, TextFlow flow) {
        scroll.viewportBoundsProperty().addListener((observable, oldBounds, bounds) ->
                flow.setPrefWidth(Math.max(0, bounds.getWidth() - 18)));
    }

    private void updateHighlight() {
        CodeHighlighter.update(ciphertext.getText(), plaintext.getText(), selectedCode,
                cipherHighlighted, plainHighlighted);
        if (selectedCode != null) {
            cipherTabs.getSelectionModel().select(1);
            plainTabs.getSelectionModel().select(1);
        }
    }

    private void error(String title, String message) {
        new Alert(Alert.AlertType.ERROR, title + "\n" + message, ButtonType.OK).showAndWait();
    }
}
