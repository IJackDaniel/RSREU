package ru.rsreu.rsa.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.rsreu.rsa.crypto.ModularExponentiation;
import ru.rsreu.rsa.model.*;
import java.math.BigInteger;

public final class CalculationController {
    @FXML private TableView<BlockResult> blocksTable;
    @FXML private TableColumn<BlockResult, String> blockIndexColumn;
    @FXML private TableColumn<BlockResult, String> sourceColumn;
    @FXML private TableColumn<BlockResult, String> resultColumn;
    @FXML private TableView<ExponentiationStep> stepsTable;
    @FXML private TableColumn<ExponentiationStep, String> iterationColumn;
    @FXML private TableColumn<ExponentiationStep, String> exponentColumn;
    @FXML private TableColumn<ExponentiationStep, String> baseColumn;
    @FXML private TableColumn<ExponentiationStep, String> previousColumn;
    @FXML private TableColumn<ExponentiationStep, String> multiplyColumn;
    @FXML private TableColumn<ExponentiationStep, String> resultColumnStep;
    @FXML private TableColumn<ExponentiationStep, String> squaredColumn;
    @FXML private Label formulaLabel;
    @FXML private Label calculationLabel;
    @FXML private Label stepDescriptionLabel;
    private TextOperationResult lastOperation;
    private RsaKeyPair currentKey;
    private boolean lastWasEncryption = true;
    private final ModularExponentiation exponentiation = new ModularExponentiation();

    @FXML private void initialize() {
        blockIndexColumn.setCellValueFactory(c -> new SimpleStringProperty(Integer.toString(c.getValue().index())));
        sourceColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().input().toString()));
        resultColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().output().toString()));
        iterationColumn.setCellValueFactory(c -> new SimpleStringProperty(Integer.toString(c.getValue().iteration())));
        exponentColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().exponentBefore().toString()));
        baseColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().baseBefore().toString()));
        previousColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().resultBefore().toString()));
        multiplyColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().multiplied() ? "Да" : "Нет"));
        resultColumnStep.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().resultAfter().toString()));
        squaredColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().baseSquared().toString()));
        blocksTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> showBlockSteps(selected));
        stepsTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> showStepExplanation(selected));
        blocksTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void setKey(RsaKeyPair key) {
        currentKey = key;
        lastOperation = null;
        blocksTable.getItems().clear();
        stepsTable.getItems().clear();
        formulaLabel.setText("Выберите блок после выполнения операции.");
        calculationLabel.setText("");
        stepDescriptionLabel.setText("");
    }

    public void showOperation(TextOperationResult operation, boolean encryption) {
        lastOperation = operation;
        lastWasEncryption = encryption;
        blocksTable.setItems(FXCollections.observableArrayList(operation.blocks()));
        if (!operation.blocks().isEmpty()) blocksTable.getSelectionModel().selectFirst();
        else { stepsTable.getItems().clear(); formulaLabel.setText("Нет блоков."); calculationLabel.setText(""); }
    }

    private void showBlockSteps(BlockResult block) {
        stepsTable.getItems().clear();
        stepDescriptionLabel.setText("");
        if (block == null || currentKey == null || lastOperation == null) return;
        BigInteger exponent = lastWasEncryption ? currentKey.publicExponent() : currentKey.privateExponent();
        BigInteger modulus = currentKey.modulus();
        ExponentiationTrace trace = exponentiation.calculateWithSteps(block.input(), exponent, modulus);
        formulaLabel.setText((lastWasEncryption ? "Шифрование: C = M^e mod n" : "Дешифрование: M = C^d mod n") +
                "   |   Блок №" + block.index() + " (" + block.byteCount() + " байт)");
        calculationLabel.setText(block.input() + " ^ " + exponent + " mod " + modulus + " = " + trace.output());
        stepsTable.setItems(FXCollections.observableArrayList(trace.steps()));
        if (!trace.steps().isEmpty()) stepsTable.getSelectionModel().selectFirst();
    }

    private void showStepExplanation(ExponentiationStep step) {
        if (step == null || currentKey == null) return;
        String calculation = step.multiplied()
            ? step.resultBefore() + " × " + step.baseBefore() + " mod " + currentKey.modulus() + " = " + step.resultAfter()
            : "Бит степени = 0, результат остаётся " + step.resultBefore();
        stepDescriptionLabel.setText("Шаг " + step.iteration() + ": " + calculation +
                "   |   Квадрат основания: " + step.baseBefore() + "² mod " + currentKey.modulus() + " = " + step.baseSquared() +
                "; затем степень делится на 2 (целочисленно).");
    }
}
