package ru.rsreu.feistel.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.rsreu.feistel.model.CipherDirection;
import ru.rsreu.feistel.model.OperationResult;
import ru.rsreu.feistel.model.RoundTrace;
import ru.rsreu.feistel.util.HexCodec;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public final class RoundsController {
    @FXML private ComboBox<Integer> blockSelector;
    @FXML private Label traceSummaryLabel;
    @FXML private TableView<RoundTrace> roundTable;
    @FXML private TableColumn<RoundTrace, String> roundColumn;
    @FXML private TableColumn<RoundTrace, String> parameterColumn;
    @FXML private TableColumn<RoundTrace, String> functionColumn;
    @FXML private TableColumn<RoundTrace, String> beforeColumn;
    @FXML private TableColumn<RoundTrace, String> afterColumn;
    @FXML private TextArea roundDetailsArea;
    private OperationResult lastOperation;

    void setMainController(MainController mainController) { /* Reserved for navigation extensions. */ }

    @FXML private void initialize() {
        roundTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        roundColumn.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().roundNumber())));
        parameterColumn.setCellValueFactory(data -> new SimpleStringProperty(HexCodec.formatWord(data.getValue().roundParameter())));
        functionColumn.setCellValueFactory(data -> new SimpleStringProperty(HexCodec.formatWord(data.getValue().functionResult())));
        beforeColumn.setCellValueFactory(data -> new SimpleStringProperty(formatBranches(data.getValue().beforeBranches())));
        afterColumn.setCellValueFactory(data -> new SimpleStringProperty(formatBranches(data.getValue().afterBranches())));
        roundTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selected) ->
                roundDetailsArea.setText(selected == null ? "Выберите раунд в таблице." : explainRound(selected)));
        blockSelector.valueProperty().addListener((obs, oldValue, selected) -> showBlock(selected));
    }

    public void showOperation(OperationResult operation) {
        lastOperation = operation;
        blockSelector.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(1, operation.blockCount()).boxed().toList()));
        traceSummaryLabel.setText(operation.rounds().getFirst().direction().getDisplayName()
                + " · Блоков: " + operation.blockCount()
                + " · Раундов на блок: " + operation.rounds().size() / operation.blockCount());
        blockSelector.getSelectionModel().selectFirst();
    }

    private void showBlock(Integer number) {
        if (number == null || lastOperation == null) return;
        List<RoundTrace> traces = lastOperation.rounds().stream()
                .filter(round -> round.blockNumber() == number).toList();
        roundTable.setItems(FXCollections.observableArrayList(traces));
        if (!traces.isEmpty()) roundTable.getSelectionModel().selectFirst();
    }

    private static String formatBranches(int[] branches) {
        return String.join(" / ", Arrays.stream(branches).mapToObj(HexCodec::formatWord).toList());
    }

    private static String explainRound(RoundTrace round) {
        int[] before = round.beforeBranches();
        int[] after = round.afterBranches();
        StringBuilder s = new StringBuilder();
        s.append("БЛОК ").append(round.blockNumber()).append(" · РАУНД ").append(round.roundNumber())
                .append(" · ").append(round.direction().getDisplayName()).append("\n\n");
        s.append("1. КЛЮЧ (64 бита = две части по 32 бита)\n")
                .append("   K1 = ").append(HexCodec.formatWord(round.leftKeyPart())).append("\n")
                .append("   K2 = ").append(HexCodec.formatWord(round.rightKeyPart())).append("\n\n");
        s.append("   K1 в битах: ").append(HexCodec.formatBits(round.leftKeyPart())).append("\n")
                .append("   K2 в битах: ").append(HexCodec.formatBits(round.rightKeyPart())).append("\n\n");
        s.append("2. ПАРАМЕТР РАУНДА Vi\n")
                .append("   ROL(K1, ").append(round.roundNumber()).append(") = ")
                .append(HexCodec.formatWord(round.rotatedLeftPart())).append("\n")
                .append("   ROR(K2, ").append(round.roundNumber()).append(") = ")
                .append(HexCodec.formatWord(round.rotatedRightPart())).append("\n")
                .append("   ROL в битах: ").append(HexCodec.formatBits(round.rotatedLeftPart())).append("\n")
                .append("   ROR в битах: ").append(HexCodec.formatBits(round.rotatedRightPart())).append("\n")
                .append("   Vi = (ROL + ROR) mod 2^32 = ")
                .append(HexCodec.formatWord(round.roundParameter())).append("\n")
                .append("   Vi в битах: ").append(HexCodec.formatBits(round.roundParameter())).append("\n\n");
        s.append("3. ОБРАЗУЮЩАЯ ФУНКЦИЯ Fi\n")
                .append("   Вход F = ").append(HexCodec.formatWord(round.functionInput()))
                .append(round.direction() == CipherDirection.ENCRYPT ? " (X1 до раунда)\n" : " (X4' до обратного раунда)\n")
                .append("   Vi     = ").append(HexCodec.formatWord(round.roundParameter())).append("\n")
                .append("   Вход F в битах: ").append(HexCodec.formatBits(round.functionInput())).append("\n")
                .append("   Fi = вход F XOR Vi = ").append(HexCodec.formatWord(round.functionResult())).append("\n")
                .append("   Fi в битах: ").append(HexCodec.formatBits(round.functionResult())).append("\n\n");
        s.append("4. ЧЕТЫРЕ ВЕТВИ ДО ПРЕОБРАЗОВАНИЯ\n");
        for (int i = 0; i < 4; i++) s.append("   X").append(i+1).append(" = ").append(HexCodec.formatWord(before[i])).append("\n");
        s.append("\n5. ФОРМУЛЫ ПРЕОБРАЗОВАНИЯ\n");
        if (round.direction() == CipherDirection.ENCRYPT) {
            s.append("   X1' = X2 XOR Fi = ").append(HexCodec.formatWord(before[1]))
                    .append(" XOR ").append(HexCodec.formatWord(round.functionResult()))
                    .append(" = ").append(HexCodec.formatWord(after[0])).append("\n")
                    .append("   X2' = X3\n   X3' = X4\n   X4' = X1\n");
        } else {
            s.append("   Обратное преобразование для раунда ").append(round.roundNumber()).append("\n")
                    .append("   X1 = X4'\n")
                    .append("   X2 = X1' XOR Fi = ").append(HexCodec.formatWord(before[0]))
                    .append(" XOR ").append(HexCodec.formatWord(round.functionResult()))
                    .append(" = ").append(HexCodec.formatWord(after[1])).append("\n")
                    .append("   X3 = X2'\n   X4 = X3'\n");
        }
        s.append("\n6. ЧЕТЫРЕ ВЕТВИ ПОСЛЕ ПРЕОБРАЗОВАНИЯ\n");
        for (int i = 0; i < 4; i++) s.append("   X").append(i+1).append(" = ").append(HexCodec.formatWord(after[i])).append("\n");
        s.append("\n   Итоговый блок (128 бит, HEX): ");
        for (int word : after) s.append(HexCodec.formatWord(word));
        return s.toString();
    }
}
