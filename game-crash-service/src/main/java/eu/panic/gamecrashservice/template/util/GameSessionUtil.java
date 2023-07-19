package eu.panic.gamecrashservice.template.util;

import lombok.Getter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Getter
public class GameSessionUtil {
    private String serverSeed;
    private String clientSeed;
    private String salt;

    public GameSessionUtil(String serverSeed, String clientSeed) {
        this.serverSeed = serverSeed;
        this.clientSeed = clientSeed;
    }

    public void generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        salt = bytesToHex(saltBytes);
    }

    public double generateRandomNumber() {
        try {
            String seed = serverSeed + clientSeed + salt;
            byte[] hash = getSHA256(seed.getBytes());

            BigInteger bigInteger = new BigInteger(1, hash);
            BigInteger maxValue = BigInteger.valueOf(Long.MAX_VALUE);
            BigInteger randomBigInteger = bigInteger.mod(maxValue);

            double randomDouble = randomBigInteger.doubleValue() / maxValue.doubleValue();

            return Math.max(0.0, randomDouble);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private byte[] getSHA256(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02x", b);
            hexString.append(hex);
        }
        return hexString.toString();
    }
}