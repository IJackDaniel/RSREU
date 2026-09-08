package ru.IJackDaniel.InfS.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.IJackDaniel.InfS.security.PasswordGenerationSettings;
import ru.IJackDaniel.InfS.security.PasswordSecurityCalculator;
import ru.IJackDaniel.InfS.security.PasswordSecurityResult;

import java.math.BigDecimal;

public class GeneratorSettingsController {

    @FXML
    private TextField probabilityField;

    @FXML
    private TextField attemptsPerMinuteField;

    @FXML
    private TextField validityDaysField;

    @FXML
    private CheckBox lowercaseCheckBox;

    @FXML
    private CheckBox uppercaseCheckBox;

    @FXML
    private CheckBox digitsCheckBox;

    @FXML
    private CheckBox specialSymbolsCheckBox;

    @FXML
    private Label alphabetPowerLabel;

    @FXML
    private Label requiredPasswordsLabel;

    @FXML
    private Label calculatedLengthLabel;

    @FXML
    private Label passwordLengthLabel;

    @FXML
    private Label passwordsCountLabel;

    @FXML
    private Label messageLabel;

    private PasswordGenerationSettings settings;
    private PasswordSecurityCalculator calculator;

    public void setDependencies(
            PasswordGenerationSettings settings,
            PasswordSecurityCalculator calculator
    ) {
        this.settings = settings;
        this.calculator = calculator;

        loadSettings();
        calculateAndShow();
    }

    private void loadSettings() {
        probabilityField.setText(
                settings.getProbability().toPlainString()
        );

        attemptsPerMinuteField.setText(
                settings.getAttemptsPerMinute().toPlainString()
        );

        validityDaysField.setText(
                settings.getValidityDays().toPlainString()
        );

        lowercaseCheckBox.setSelected(
                settings.isUseLowercase()
        );

        uppercaseCheckBox.setSelected(
                settings.isUseUppercase()
        );

        digitsCheckBox.setSelected(
                settings.isUseDigits()
        );

        specialSymbolsCheckBox.setSelected(
                settings.isUseSpecialSymbols()
        );
    }

    @FXML
    private void onCalculateButtonClick() {
        calculateAndShow();
    }

    @FXML
    private void onApplyButtonClick() {
        try {
            PasswordGenerationSettings newSettings =
                    createSettingsFromFields();

            validateSettings(newSettings);

            PasswordSecurityResult result =
                    calculator.calculate(newSettings);

            settings.copyFrom(newSettings);

            showResult(result);

            closeWindow();

        } catch (IllegalArgumentException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onCancelButtonClick() {
        closeWindow();
    }

    private void calculateAndShow() {
        try {
            PasswordGenerationSettings temporarySettings =
                    createSettingsFromFields();

            validateSettings(temporarySettings);

            PasswordSecurityResult result =
                    calculator.calculate(temporarySettings);

            showResult(result);

            messageLabel.setText("");

        } catch (IllegalArgumentException e) {
            clearResult();
            messageLabel.setText(e.getMessage());
        }
    }

    private PasswordGenerationSettings createSettingsFromFields() {
        BigDecimal probability;
        BigDecimal attemptsPerMinute;
        BigDecimal validityDays;

        try {
            probability =
                    new BigDecimal(
                            probabilityField.getText().trim()
                    );

            attemptsPerMinute =
                    new BigDecimal(
                            attemptsPerMinuteField.getText().trim()
                    );

            validityDays =
                    new BigDecimal(
                            validityDaysField.getText().trim()
                    );

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "P, V и T должны быть числами"
            );
        }

        return new PasswordGenerationSettings(
                probability,
                attemptsPerMinute,
                validityDays,
                lowercaseCheckBox.isSelected(),
                uppercaseCheckBox.isSelected(),
                digitsCheckBox.isSelected(),
                specialSymbolsCheckBox.isSelected()
        );
    }

    private void validateSettings(
            PasswordGenerationSettings settings
    ) {
        BigDecimal probability =
                settings.getProbability();

        if (probability.compareTo(BigDecimal.ZERO) <= 0
                || probability.compareTo(BigDecimal.ONE) >= 0) {

            throw new IllegalArgumentException(
                    "Вероятность P должна быть больше 0 и меньше 1"
            );
        }

        if (settings.getAttemptsPerMinute()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Скорость перебора V должна быть больше 0"
            );
        }

        if (settings.getValidityDays()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Срок действия T должен быть больше 0"
            );
        }

        if (settings.getAlphabetPower() == 0) {
            throw new IllegalArgumentException(
                    "Выберите хотя бы одну группу символов"
            );
        }
    }

    private void showResult(
            PasswordSecurityResult result
    ) {
        alphabetPowerLabel.setText(
                String.valueOf(
                        result.getAlphabetPower()
                )
        );

        requiredPasswordsLabel.setText(
                result.getRequiredPasswordsCount()
                        .toString()
        );

        calculatedLengthLabel.setText(
                String.valueOf(
                        result.getCalculatedPasswordLength()
                )
        );

        passwordLengthLabel.setText(
                String.valueOf(
                        result.getPasswordLength()
                )
        );

        passwordsCountLabel.setText(
                result.getPasswordsCount()
                        .toString()
        );
    }

    private void clearResult() {
        alphabetPowerLabel.setText("-");
        requiredPasswordsLabel.setText("-");
        calculatedLengthLabel.setText("-");
        passwordLengthLabel.setText("-");
        passwordsCountLabel.setText("-");
    }

    private void closeWindow() {
        Stage stage =
                (Stage) probabilityField
                        .getScene()
                        .getWindow();

        stage.close();
    }
}