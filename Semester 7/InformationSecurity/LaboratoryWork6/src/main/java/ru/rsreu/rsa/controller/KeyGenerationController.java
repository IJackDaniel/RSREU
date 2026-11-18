package ru.rsreu.rsa.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.rsreu.rsa.crypto.RsaKeyGenerator;
import ru.rsreu.rsa.model.RsaKeyPair;
import java.math.BigInteger;
import java.util.function.Consumer;

public final class KeyGenerationController {
    @FXML private TextField pField;
    @FXML private TextField qField;
    @FXML private TextField nField;
    @FXML private TextField phiField;
    @FXML private TextField eField;
    @FXML private TextField dField;
    @FXML private TextArea candidatesArea;
    @FXML private TextField firstGcdField;
    @FXML private TextField secondGcdField;
    @FXML private Label gcdResultLabel;
    @FXML private Label statusLabel;
    private final RsaKeyGenerator generator = new RsaKeyGenerator();
    private Consumer<RsaKeyPair> onKeysChanged = unused -> { };

    @FXML private void initialize() {
        pField.setText(RsaKeyGenerator.DEFAULT_P.toString());
        qField.setText(RsaKeyGenerator.DEFAULT_Q.toString());
        firstGcdField.setText("11");
        secondGcdField.setText("66360");
    }

    public void setOnKeysChanged(Consumer<RsaKeyPair> listener) { onKeysChanged = listener; }

    @FXML private void generateKeys() {
        try {
            BigInteger p = new BigInteger(pField.getText().trim());
            BigInteger q = new BigInteger(qField.getText().trim());
            RsaKeyPair keys = generator.generate(p, q);
            nField.setText(keys.modulus().toString());
            phiField.setText(keys.phi().toString());
            eField.setText(keys.publicExponent().toString());
            dField.setText(keys.privateExponent().toString());
            StringBuilder sb = new StringBuilder("e    НОД(e, φ(n))    Подходит\n");
            for (var candidate : generator.findExponentCandidates(keys.phi())) {
                sb.append(candidate.candidate()).append("    ").append(candidate.gcd())
                    .append("    ").append(candidate.isValid() ? "Да" : "Нет").append('\n');
            }
            candidatesArea.setText(sb.toString());
            firstGcdField.setText(keys.publicExponent().toString());
            secondGcdField.setText(keys.phi().toString());
            statusLabel.setText("Ключи сформированы.");
            onKeysChanged.accept(keys);
        } catch (NumberFormatException ex) {
            statusLabel.setText("p и q должны быть целыми числами.");
            onKeysChanged.accept(null);
            clearResults();
        } catch (IllegalArgumentException ex) {
            statusLabel.setText(ex.getMessage());
            onKeysChanged.accept(null);
            clearResults();
        }
    }

    private void clearResults() {
        nField.clear(); phiField.clear(); eField.clear(); dField.clear(); candidatesArea.clear();
    }

    @FXML private void checkCoprimality() {
        try {
            BigInteger a = new BigInteger(firstGcdField.getText().trim());
            BigInteger b = new BigInteger(secondGcdField.getText().trim());
            BigInteger gcd = a.gcd(b);
            gcdResultLabel.setText("НОД = " + gcd + "; " +
                    (gcd.equals(BigInteger.ONE) ? "взаимно простые" : "не взаимно простые"));
        } catch (NumberFormatException ex) {
            gcdResultLabel.setText("Введите два целых числа.");
        }
    }

    public void generateDefaultKeys() { generateKeys(); }
}
