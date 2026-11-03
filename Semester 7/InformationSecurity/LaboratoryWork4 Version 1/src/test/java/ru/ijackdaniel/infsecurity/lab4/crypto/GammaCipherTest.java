package ru.ijackdaniel.infsecurity.lab4.crypto;

import org.junit.jupiter.api.Test;
import ru.ijackdaniel.infsecurity.lab4.model.*;

import static org.junit.jupiter.api.Assertions.*;

class GammaCipherTest {
    private final GammaCipher cipher = new GammaCipher();

    @Test void roundtripForBothModes() {
        String[] texts = {"", "A", "Hello, Java!", "123456789", "Line 1\nLine 2"};
        for (CipherMode mode : CipherMode.values()) {
            CipherConfig config = new CipherConfig(5, 3, 7, mode);
            for (String text : texts) {
                byte[] source = Ascii7Codec.encode(text);
                byte[] ciphertext = cipher.encrypt(source, config, true).bytes();
                assertArrayEquals(source, cipher.decrypt(ciphertext, config, true).bytes());
            }
        }
    }

    @Test void sevenBitFeedbackExample() {
        var config = new CipherConfig(5, 3, 7, CipherMode.FEEDBACK);
        var result = cipher.encrypt(new byte[] {1, 2, 3}, config, true);
        assertEquals(38, result.steps().get(0).gamma());
        assertEquals(8, result.steps().get(1).gamma());
        assertEquals(8, result.steps().get(2).gamma());
        assertArrayEquals(new byte[] {39, 10, 11}, result.bytes());
    }

    @Test void validatesSevenBitInput() {
        var config = new CipherConfig(5, 3, 7, CipherMode.FEEDBACK);
        assertThrows(IllegalArgumentException.class, () -> Ascii7Codec.encode("Привет"));
        assertThrows(IllegalArgumentException.class, () -> cipher.encrypt(new byte[]{(byte) 200}, config, false));
        assertThrows(IllegalArgumentException.class, () -> new CipherConfig(128, 3, 7, CipherMode.FEEDBACK));
        assertThrows(IllegalArgumentException.class, () -> HexCodec.decode("FG"));
    }
}
