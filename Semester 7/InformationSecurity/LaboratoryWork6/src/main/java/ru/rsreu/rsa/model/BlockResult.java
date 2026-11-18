package ru.rsreu.rsa.model;

import java.math.BigInteger;

public record BlockResult(int index, int byteCount, BigInteger input, BigInteger output) { }
