package eu.panic.authservice.util;

import java.util.Random;

public class RandomCharacterGenerator {
    public static String generateRandomCharacters(int length){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVW-XYZ012345678=_9";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        String randomString = sb.toString();

        return randomString;
    }
}