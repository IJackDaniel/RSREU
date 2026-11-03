package ru.ijackdaniel.infsecurity.lab4.crypto;

import org.junit.jupiter.api.Test;
import ru.ijackdaniel.infsecurity.lab4.model.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class GammaCipherTest {
    private final GammaCipher cipher = new GammaCipher();

    @Test void roundtripAllBitWidthsAndModes() {
        byte[] text = "Привет, Java! 🌍 UTF-8".getBytes(StandardCharsets.UTF_8);
        for (int b = 1; b <= 16; b++) for (CipherMode mode : CipherMode.values()) {
            CipherConfig config = new CipherConfig(5 % (1 << b), 3 % (1 << b), 7 % (1 << b), b, mode);
            byte[] encrypted = cipher.encrypt(text, config, true).bytes();
            assertArrayEquals(text, cipher.decrypt(encrypted, config, true).bytes(), "b=" + b + " " + mode);
        }
    }

    @Test void referenceFeedbackExample() {
        CipherConfig cfg = new CipherConfig(5, 3, 7, 5, CipherMode.FEEDBACK);
        // Пример методички использует именно 5-битные символы; проверяем их шаги через поток.
        byte[] input = {(byte) 0b00001000, (byte) 0b10000110}; // 00001 00010 00011 плюс 0
        var steps = cipher.encrypt(input, cfg, true).steps();
        assertEquals(6, (5 * 7 + 3) % 32);
        assertEquals(8, (5 * 1 + 3) % 32);
        assertEquals(4, steps.size());
    }

    @Test void invalidSettingsAndHex() {
        assertThrows(IllegalArgumentException.class, () -> new CipherConfig(5, 3, 7, 0, CipherMode.FEEDBACK));
        assertThrows(IllegalArgumentException.class, () -> new CipherConfig(5, 3, 7, 17, CipherMode.FEEDBACK));
        assertThrows(IllegalArgumentException.class, () -> HexCodec.decode("FG"));
        assertThrows(IllegalArgumentException.class, () -> HexCodec.decode("1"));
    }

    @Test void emptyString() {
        var cfg = new CipherConfig(5, 3, 7, 7, CipherMode.FEEDBACK);
        assertTrue(Arrays.equals(new byte[0], cipher.encrypt(new byte[0], cfg, true).bytes()));
    }
}
