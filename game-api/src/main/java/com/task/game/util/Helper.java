package com.task.game.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class Helper {

    private static final Random random = new Random();

    public static int getRandom(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static boolean isFirst() {
        return random.nextBoolean();
    }

    public static void print(String... strings) {
        int maxBoxWidth = getMaxLength(strings);
        String line = "+" + fill('-', maxBoxWidth + 2) + "+";
        log.info(line);
        for (String str : strings) {
            log.info("| {} |", padString(str, maxBoxWidth));
        }
        log.info(line);
    }

    private static int getMaxLength(String... strings) {
        int len = 0;
        for (String str : strings) {
            len = Math.max(str.length(), len);
        }
        return len;
    }

    private static String padString(String str, int len) {
        return str + fill(' ', len - str.length());
    }

    private static String fill(char ch, int len) {
        return String.valueOf(ch).repeat(Math.max(0, len));
    }
}
