package eu.panic.authservice.util;

import java.security.SecureRandom;

public class SeedGeneratorUtil {
    private static final int SEED_LENGTH = 16;

    public static String generateSeed() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] seedBytes = new byte[SEED_LENGTH];
        secureRandom.nextBytes(seedBytes);
        return bytesToHex(seedBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}