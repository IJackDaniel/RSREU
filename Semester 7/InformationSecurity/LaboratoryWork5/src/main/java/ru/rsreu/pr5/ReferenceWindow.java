package ru.rsreu.pr5;

import javafx.beans.property.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/** Displays reference letter frequencies and common n-grams from the assignment. */
public final class ReferenceWindow {
    private ReferenceWindow() {}
    public static void show(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.NONE);
        dialog.setTitle("Справочник частот русского языка — ПР5");
        TabPane tabs = new TabPane();
        String[][] letters = {
                {"Пробел", "17,5"},{"О","8,9"},{"Е (включая Ё)","7,2"},
                {"А","6,2"},{"И","6,2"},{"Т","5,3"},{"Н","5,3"},
                {"С","4,5"},{"Р","4,0"},{"В","3,8"},{"Л","3,5"},
                {"К","2,8"},{"М","2,6"},{"Д","2,5"},{"П","2,3"},
                {"У","2,1"},{"Я","1,8"},{"Ы","1,6"},{"Ь (включая Ъ)","1,4"},
                {"Б","1,4"},{"Г","1,3"},{"Ч","1,2"},{"Й","1,0"},
                {"Х","0,9"},{"Ж","0,7"},{"Ю","0,6"},{"Ш","0,6"},
                {"Ц","0,4"},{"Щ","0,3"},{"Э","0,3"},{"Ф","0,2"}};
        TableView<ReferenceRow> letterTable = referenceTable(letters, "Символ", "Частота, %");
        VBox first = new VBox(letterTable);
        first.setPadding(new Insets(10));
        VBox.setVgrow(letterTable, Priority.ALWAYS);
        String[] bigrams = {"СТ","НО","НЕ","ЕН","ТО","НА","ОВ","НИ","РА","ВО","КО","ПО"};
        String[] trigrams = {"СТО","ЕНО","НОВ","ТОВ","ОВО","ОВА","ОГО","ПРО","АТЬ","ЕНИ"};
        tabs.getTabs().addAll(
                referenceTab("Буквы", first),
                referenceTab("Биграммы", gramReferencePane(bigrams)),
                referenceTab("Триграммы", gramReferencePane(trigrams)));
        BorderPane root = new BorderPane(tabs);
        root.setPadding(new Insets(8));
        dialog.setScene(new Scene(root, 570, 600));
        dialog.show();
    }

    private static Tab referenceTab(String title, javafx.scene.Node content) {
        Tab tab = new Tab(title, content);
        tab.setClosable(false);
        return tab;
    }

    private static VBox gramReferencePane(String[] gramsList) {
        FlowPane items = new FlowPane(9, 9);
        for (String gram : gramsList) {
            Label label = new Label(gram);
            label.setMinWidth(95);
            label.setAlignment(Pos.CENTER);
            label.setPadding(new Insets(12));
            label.setStyle("-fx-background-color: #eaf1f8; -fx-border-color: #cfdae4; -fx-background-radius: 5;");
            items.getChildren().add(label);
        }
        VBox pane = new VBox(items);
        pane.setPadding(new Insets(15));
        return pane;
    }

    private static TableView<ReferenceRow> referenceTable(String[][] data, String title1, String title2) {
        TableView<ReferenceRow> result = new TableView<>();
        TableColumn<ReferenceRow,String> col1 = new TableColumn<>(title1);
        TableColumn<ReferenceRow,String> col2 = new TableColumn<>(title2);
        col1.setCellValueFactory(v -> v.getValue().nameProperty());
        col2.setCellValueFactory(v -> v.getValue().rateProperty());
        result.getColumns().addAll(col1,col2);
        result.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        for(String[] pair : data) result.getItems().add(new ReferenceRow(pair[0], pair[1]));
        return result;
    }

    private static final class ReferenceRow {
        private final StringProperty name;
        private final StringProperty rate;
        ReferenceRow(String n, String r) { name = new SimpleStringProperty(n); rate = new SimpleStringProperty(r); }
        StringProperty nameProperty() {return name;}
        StringProperty rateProperty() {return rate;}
    }

}
