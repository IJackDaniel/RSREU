package ru.rsreu.rsa.crypto;

import ru.rsreu.rsa.model.RsaKeyPair;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class RsaKeyGenerator {
    public static final BigInteger DEFAULT_P = BigInteger.valueOf(211);
    public static final BigInteger DEFAULT_Q = BigInteger.valueOf(317);
    private static final BigInteger MINIMUM_MODULUS = BigInteger.valueOf(256);
    private static final int PRIME_CERTAINTY = 80;

    public RsaKeyPair generate(BigInteger p, BigInteger q) {
        if (p == null || q == null || !p.isProbablePrime(PRIME_CERTAINTY)
                || !q.isProbablePrime(PRIME_CERTAINTY) || p.equals(q)) {
            throw new IllegalArgumentException("p и q должны быть различными простыми числами больше 1.");
        }
        BigInteger modulus = p.multiply(q);
        if (modulus.compareTo(MINIMUM_MODULUS) <= 0) {
            throw new IllegalArgumentException("Произведение p × q должно быть больше 256 для шифрования байтов UTF-8.");
        }
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.TWO;
        while (!e.gcd(phi).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        BigInteger d = e.modInverse(phi);
        return new RsaKeyPair(p, q, modulus, phi, e, d);
    }

    public List<ExponentCandidate> findExponentCandidates(BigInteger phi) {
        List<ExponentCandidate> attempts = new ArrayList<>();
        BigInteger candidate = BigInteger.TWO;
        while (true) {
            BigInteger gcd = candidate.gcd(phi);
            attempts.add(new ExponentCandidate(candidate, gcd));
            if (gcd.equals(BigInteger.ONE)) return List.copyOf(attempts);
            candidate = candidate.add(BigInteger.ONE);
        }
    }

    public record ExponentCandidate(BigInteger candidate, BigInteger gcd) {
        public boolean isValid() { return gcd.equals(BigInteger.ONE); }
    }
}
