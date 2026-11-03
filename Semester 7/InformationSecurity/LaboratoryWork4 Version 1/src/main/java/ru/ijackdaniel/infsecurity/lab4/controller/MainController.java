package ru.ijackdaniel.infsecurity.lab4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.ijackdaniel.infsecurity.lab4.crypto.GammaCipher;
import ru.ijackdaniel.infsecurity.lab4.crypto.HexCodec;
import ru.ijackdaniel.infsecurity.lab4.crypto.Ascii7Codec;
import ru.ijackdaniel.infsecurity.lab4.model.*;

import java.util.Arrays;

public final class MainController {
    @FXML private ComboBox<CipherMode> modeBox;
    @FXML private TextField aField, cField, seedField;
    @FXML private TextArea inputText, cipherHex, restoredText, detailedText, comparisonText;
    @FXML private CheckBox detailCheck;
    @FXML private TitledPane detailPane;
    @FXML private Label statusLabel, modulusLabel, periodLabel;
    private final GammaCipher cipher = new GammaCipher();
    private String previousHex;
    private String previousSettings;
    private boolean updatingFields;
    private boolean hexEdited;

    @FXML private void initialize() {
        modeBox.getItems().setAll(CipherMode.values());
        modeBox.setValue(CipherMode.FEEDBACK);
        aField.setText("5"); cField.setText("3"); seedField.setText("7");
        inputText.setText("Hello, Java!");
        modeBox.valueProperty().addListener((obs, old, val) -> invalidateFromSettings());
        for (TextField field : new TextField[]{aField, cField, seedField}) {
            field.textProperty().addListener((obs, old, val) -> invalidateFromSettings());
        }
        inputText.textProperty().addListener((obs, old, val) -> invalidateFromInput());
        cipherHex.textProperty().addListener((obs, old, val) -> {
            if (!updatingFields) {
                hexEdited = true;
                restoredText.clear();
                detailedText.clear();
                statusLabel.setText("HEX изменён. Нажмите «Расшифровать HEX».");
            }
        });
        detailCheck.selectedProperty().addListener((obs, old, val) -> {
            detailedText.clear();
            if (val) statusLabel.setText("Подробный разбор появится после следующей операции.");
        });
        detailPane.visibleProperty().bind(detailCheck.selectedProperty());
        detailPane.managedProperty().bind(detailCheck.selectedProperty());
        modulusLabel.setText("b = 7 бит; M = 128 (вариант 2)");
        statusLabel.setText("Готово. Вариант 2: обратная связь, b = 7. Вводите символы ASCII.");
    }

    private void invalidateFromSettings() {
        clearOutputs("Параметры изменены. Выполните шифрование заново.");
        periodLabel.setText("");
    }

    private void invalidateFromInput() {
        clearOutputs("Текст изменён. Выполните шифрование заново.");
    }

    private void clearOutputs(String message) {
        updatingFields = true;
        try {
            cipherHex.clear();
            restoredText.clear();
            detailedText.clear();
            comparisonText.clear();
            hexEdited = false;
        } finally {
            updatingFields = false;
        }
        statusLabel.setText(message);
    }

    private CipherConfig config() {
        try {
            return new CipherConfig(Integer.parseInt(aField.getText().trim()),
                    Integer.parseInt(cField.getText().trim()), Integer.parseInt(seedField.getText().trim()), modeBox.getValue());
        } catch (NumberFormatException ex) { throw new IllegalArgumentException("Параметры должны быть целыми числами"); }
    }

    @FXML private void encrypt() {
        try {
            CipherConfig cfg = config();
            byte[] plain = Ascii7Codec.encode(inputText.getText());
            CipherResult encrypted = cipher.encrypt(plain, cfg, detailCheck.isSelected());
            String hex = HexCodec.encode(encrypted.bytes());
            String settings = "A=" + cfg.a() + ", C=" + cfg.c() + ", T0=" + cfg.seed()
                    + ", b=" + cfg.bits() + ", " + cfg.mode();
            comparisonText.setText(previousHex == null ? "Это первое шифрование. Измените параметры и зашифруйте ещё раз."
                    : "Предыдущие параметры: " + previousSettings + "\nПредыдущий HEX: " + previousHex
                    + "\n\nНовые параметры: " + settings + "\nНовый HEX: " + hex
                    + "\n\nСовпадают: " + (previousHex.equals(hex) ? "ДА" : "НЕТ"));
            previousHex = hex;
            previousSettings = settings;
            updatingFields = true;
            try {
                cipherHex.setText(hex);
                restoredText.clear();
            } finally {
                updatingFields = false;
            }
            hexEdited = false;
            periodLabel.setText(cfg.hasFullPeriod() ? "Условия полного периода выполнены" : "Условия полного периода не выполнены / неприменимы");
            if (detailCheck.isSelected()) {
                detailedText.setText(buildTrace(plain, encrypted, cfg, false));
                detailPane.setExpanded(true);
            } else detailedText.clear();
            statusLabel.setText("Зашифровано " + plain.length + " символов (7 бит каждый)");
        } catch (IllegalArgumentException ex) { showError(ex.getMessage()); }
    }

    @FXML private void decrypt() {
        try {
            CipherConfig cfg = config();
            byte[] encrypted = HexCodec.decode(cipherHex.getText());
            CipherResult decoded = cipher.decrypt(encrypted, cfg, detailCheck.isSelected());
            String restored = Ascii7Codec.decode(decoded.bytes());
            restoredText.setText(restored);
            if (detailCheck.isSelected()) {
                detailedText.setText(buildTrace(encrypted, decoded, cfg, true));
                detailPane.setExpanded(true);
            } else detailedText.clear();
            byte[] expected = Ascii7Codec.encode(inputText.getText());
            boolean matches = Arrays.equals(expected, decoded.bytes());
            statusLabel.setText("Расшифровано. Совпадает с полем исходного текста: " + (matches ? "ДА" : "НЕТ"));
        } catch (IllegalArgumentException ex) { showError(ex.getMessage()); }
    }

    @FXML private void roundTrip() {
        try {
            encrypt();
            if (hexEdited) return;
            CipherConfig cfg = config();
            byte[] original = Ascii7Codec.encode(inputText.getText());
            byte[] encoded = cipher.encrypt(original, cfg, false).bytes();
            byte[] decoded = cipher.decrypt(encoded, cfg, false).bytes();
            restoredText.setText(Ascii7Codec.decode(decoded));
            boolean ok = Arrays.equals(original, decoded);
            statusLabel.setText(ok ? "Проверка пройдена: исходный текст полностью восстановлен"
                    : "ОШИБКА: восстановленный текст отличается");
        } catch (IllegalArgumentException ex) { showError(ex.getMessage()); }
    }

    private String buildTrace(byte[] source, CipherResult result, CipherConfig cfg, boolean decrypt) {
        StringBuilder sb = new StringBuilder();
        sb.append("ОПЕРАЦИЯ: ").append(decrypt ? "ДЕШИФРОВАНИЕ" : "ШИФРОВАНИЕ")
                .append("\nРЕЖИМ: ").append(cfg.mode())
                .append("\nA=").append(cfg.a()).append(", C=").append(cfg.c())
                .append(", T₀=").append(cfg.seed()).append(", b=").append(cfg.bits())
                .append(", M=").append(cfg.modulus())
                .append("\nВход HEX: ").append(HexCodec.encode(source))
                .append("\nВходные биты (по 7 на символ): ").append(bytesAsBits(source))
                .append("\nКоличество исходных битов: ").append(source.length * 7L)
                .append("\nКаждый символ занимает один 7-битный блок.")
                .append("\n\n");
        for (CipherStep step : result.steps()) {
            sb.append("── БЛОК №").append(step.index()).append(" (бит: ").append(step.width()).append(") ──\n");
            if (step.checksum() != null) {
                sb.append("Предыдущий открытый блок: ")
                        .append(Integer.toBinaryString(step.previousPlaintext()))
                        .append("; единиц: ").append(step.checksum()).append("\n");
            } else {
                sb.append(step.index() == 1 ? "Начальное значение T₀" : "Предыдущее значение гаммы T")
                        .append(" = ").append(step.recurrenceInput()).append("\n");
            }
            long fullGamma = ((long) cfg.a() * step.recurrenceInput() + cfg.c()) % cfg.modulus();
            sb.append("T").append(step.index()).append(" = (").append(cfg.a()).append(" * ")
                    .append(step.recurrenceInput()).append(" + ").append(cfg.c())
                    .append(") mod ").append(cfg.modulus()).append(" = ").append(fullGamma).append("\n");
            sb.append("Вход:      ").append(CipherStep.binary(step.input(), step.width()))
                    .append("\nГамма:     ").append(CipherStep.binary(step.gamma(), step.width()))
                    .append("\nXOR:       ").append(CipherStep.binary(step.output(), step.width()))
                    .append("\n\n");
        }
        sb.append("Выход HEX: ").append(HexCodec.encode(result.bytes()))
                .append("\nВыходные биты: ").append(bytesAsBits(result.bytes()));
        return sb.toString();
    }

    private String bytesAsBits(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte value : data) {
            if (!sb.isEmpty()) sb.append(' ');
            sb.append(CipherStep.binary(value & 0x7F, 7));
        }
        return sb.toString();
    }

    private void showError(String msg) {
        statusLabel.setText("Ошибка: " + msg);
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setHeaderText("Проверьте входные данные");
        alert.showAndWait();
    }
}
