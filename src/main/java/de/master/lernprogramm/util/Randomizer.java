package de.master.lernprogramm.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.BitsStreamGenerator;
import org.apache.commons.math3.random.MersenneTwister;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author tiedet (PiAL Consult GmbH) 2021.
 */
@Slf4j
public class Randomizer {

    private final static BitsStreamGenerator random = new MersenneTwister();

    public static int getRandomIntByMinMax(int min, int max) {
        if (max > min) {
            return getRandomIntByRange(min, max-min);
        }
        return max;
    }

    public static Instant getRandomDateRangeAgo(int beginnYearsAgo, int rangeYears) {
        long beginn = ZonedDateTime.now().minusYears(beginnYearsAgo).toEpochSecond();
        long ende = ZonedDateTime.now().minusYears(beginnYearsAgo - rangeYears).toEpochSecond();
        long random = getRandomLongByRange(beginn, ende - beginn);
        return Instant.ofEpochSecond(random);
    }

    public static boolean getRandomBooleanProzentWahrscheinlichkeitTrue(int prozentWahrscheinlichkeitTrue) {
        return getRandomIntByMinMax(1, 100) <= prozentWahrscheinlichkeitTrue;
    }

    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    private static long getRandomLongByRange(long min, long range) {
        return random.nextLong(range + 1) + min;
    }

    private static int getRandomIntByRange(int min, int range) {
        return  random.nextInt(range + 1) + min;
    }

    public static double getRandomDouble() {
        return random.nextDouble();
    }

    public static <T> T getRandomItemOfList(List<T> itemList) {
        int randomInt = getRandomIntByMinMax(0, itemList.size() - 1);
        return itemList.get(randomInt);
    }
}
