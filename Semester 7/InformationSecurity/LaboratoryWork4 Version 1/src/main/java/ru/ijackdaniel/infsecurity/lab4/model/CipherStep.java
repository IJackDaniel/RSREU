package ru.ijackdaniel.infsecurity.lab4.model;

public record CipherStep(int index, int width, int input, int gamma, int output,
                         int recurrenceInput, Integer previousPlaintext, Integer checksum) {
    public static String binary(int number, int width) {
        String s = Integer.toBinaryString(number);
        return "0".repeat(Math.max(0, width - s.length())) + s;
    }
}
