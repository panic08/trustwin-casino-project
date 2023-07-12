package eu.panic.gameminerservice.template.util;

import java.util.ArrayList;
import java.util.List;

public class GameMinerUtil {
    public static List<Integer> minesShuffling(double[] numbers, int minesCount) {
        List<Integer> range = new ArrayList<>();

        List<Integer> permutation = new ArrayList<>();

        for (int i = 0; i <= 24; i++) {
            range.add(i);
        }

        for (int i = range.size() - 1; i >= 1; i--) {
            int j = (int) (numbers[i % numbers.length] * (i + 1));
            int temp = range.get(j);
            range.set(j, range.get(i));
            range.set(i, temp);
        }

        List<Integer> minePositions = range.subList(0, minesCount);

        return minePositions;
    }
    public static List<Integer> findMissingNumbers(List<Integer> numbers) {
        List<Integer> missingNumbers = new ArrayList<>();

        for (int i = 0; i <= 24; i++) {
            if (!numbers.contains(i)) {
                missingNumbers.add(i);
            }
        }

        return missingNumbers;
    }
}
