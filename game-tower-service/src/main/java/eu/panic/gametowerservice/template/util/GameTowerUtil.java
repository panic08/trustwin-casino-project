package eu.panic.gametowerservice.template.util;

import java.util.Arrays;

public class GameTowerUtil {
    public static int[][] minesShuffling(double[] numbers, int minesCount) {
        int[][] field = new int[10][minesCount];
        for (int i = 0; i < 10; i++) {
            // Initial array
            int[] range = new int[5];
            // Resulting array
            int[] permutation = new int[minesCount];
            for (int j = 0; j <= 4; j++) range[j] = j;
            for (int j = 0; j < minesCount; j++) {
                int index = (int) (numbers[i * minesCount + j] * range.length);
                permutation[j] = range[index];
                range[index] = range[range.length - 1];
                range = Arrays.copyOf(range, range.length - 1);
            }
            field[i] = permutation;
        }
        return field;
    }
}
