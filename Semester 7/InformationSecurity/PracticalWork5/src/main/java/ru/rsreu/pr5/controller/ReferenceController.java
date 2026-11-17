package ru.rsreu.pr5.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;

/** Заполняет справочные частоты, биграммы и триграммы. */
public class ReferenceController {
    @FXML private TableView<ReferenceRow> letterTable;
    @FXML private TableColumn<ReferenceRow, String> letterNameColumn;
    @FXML private TableColumn<ReferenceRow, String> letterRateColumn;
    @FXML private FlowPane bigramsPane;
    @FXML private FlowPane trigramsPane;

    @FXML
    private void initialize() {
        letterNameColumn.setCellValueFactory(row -> row.getValue().name());
        letterRateColumn.setCellValueFactory(row -> row.getValue().rate());
        letterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        String[][] letters = {
                {"Пробел", "17,5"}, {"О", "8,9"}, {"Е (включая Ё)", "7,2"},
                {"А", "6,2"}, {"И", "6,2"}, {"Т", "5,3"}, {"Н", "5,3"},
                {"С", "4,5"}, {"Р", "4,0"}, {"В", "3,8"}, {"Л", "3,5"},
                {"К", "2,8"}, {"М", "2,6"}, {"Д", "2,5"}, {"П", "2,3"},
                {"У", "2,1"}, {"Я", "1,8"}, {"Ы", "1,6"}, {"Ь (включая Ъ)", "1,4"},
                {"Б", "1,4"}, {"Г", "1,3"}, {"Ч", "1,2"}, {"Й", "1,0"},
                {"Х", "0,9"}, {"Ж", "0,7"}, {"Ю", "0,6"}, {"Ш", "0,6"},
                {"Ц", "0,4"}, {"Щ", "0,3"}, {"Э", "0,3"}, {"Ф", "0,2"}
        };
        for (String[] entry : letters) {
            letterTable.getItems().add(new ReferenceRow(new SimpleStringProperty(entry[0]), new SimpleStringProperty(entry[1])));
        }
        addGrams(bigramsPane, new String[] {"СТ", "НО", "НЕ", "ЕН", "ТО", "НА", "ОВ", "НИ", "РА", "ВО", "КО", "ПО"});
        addGrams(trigramsPane, new String[] {"СТО", "ЕНО", "НОВ", "ТОВ", "ОВО", "ОВА", "ОГО", "ПРО", "АТЬ", "ЕНИ"});
    }

    private void addGrams(FlowPane pane, String[] grams) {
        for (String gram : grams) {
            Label label = new Label(gram);
            label.setMinWidth(95);
            label.setAlignment(Pos.CENTER);
            label.setPadding(new Insets(12));
            label.setStyle("-fx-background-color: #eaf1f8; -fx-border-color: #cfdae4; -fx-background-radius: 5;");
            pane.getChildren().add(label);
        }
    }

    private record ReferenceRow(SimpleStringProperty name, SimpleStringProperty rate) {}
}
