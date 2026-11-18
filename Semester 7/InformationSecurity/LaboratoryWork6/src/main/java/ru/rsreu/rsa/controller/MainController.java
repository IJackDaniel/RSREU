package ru.rsreu.rsa.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import ru.rsreu.rsa.model.RsaKeyPair;
import ru.rsreu.rsa.util.TextBlockCodec;

public final class MainController {
    @FXML private KeyGenerationController keysViewController;
    @FXML private EncryptionController encryptionViewController;
    @FXML private DecryptionController decryptionViewController;
    @FXML private CalculationController calculationsViewController;
    @FXML private TabPane mainTabs;

    @FXML private void initialize() {
        keysViewController.setOnKeysChanged(this::updateKeyPair);
        encryptionViewController.setOnBlockSizeChanged(decryptionViewController::selectBlockSize);
        decryptionViewController.setOnBlockSizeChanged(encryptionViewController::selectBlockSize);
        encryptionViewController.setOnResult(result -> {
            calculationsViewController.showOperation(result, true);
            decryptionViewController.setCiphertext(result.text());
        });
        decryptionViewController.setOnResult(result -> calculationsViewController.showOperation(result, false));
        keysViewController.generateDefaultKeys();
    }

    private void updateKeyPair(RsaKeyPair key) {
        encryptionViewController.setKey(key);
        decryptionViewController.setKey(key);
        calculationsViewController.setKey(key);
        int maxSize = key == null ? 0 : TextBlockCodec.maximumBlockSize(key.modulus());
        int defaultSize = Math.min(2, maxSize);
        encryptionViewController.setBlockSizes(maxSize, defaultSize);
        decryptionViewController.setBlockSizes(maxSize, defaultSize);
    }
}
