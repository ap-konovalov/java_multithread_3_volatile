package ru.netology;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

//https://github.com/netology-code/jd-homeworks/blob/video/volatile/README.md
public class Main {

    private static Random random = new Random();
    private static AtomicInteger threeCharsWords = new AtomicInteger(0);
    private static AtomicInteger fourCharsWords = new AtomicInteger(0);
    private static AtomicInteger fiveCharsWords = new AtomicInteger(0);

    public static void main(String[] args) {
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String name = texts[i];
                if (isPalindrome(name) && !isOneChar(name)) {
                    updateCounter(name);
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String name = texts[i];
                if (isOneChar(name)) {
                    updateCounter(name);
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String name = texts[i];
                if (isLettersIncreasing(name) && !isOneChar(name)) {
                    updateCounter(name);
                }
            }
        }).start();

        System.out.println("Красивых слов с длиной 3: " + threeCharsWords + " шт");
        System.out.println("Красивых слов с длиной 4: " + fourCharsWords + " шт");
        System.out.println("Красивых слов с длиной 5: " + fiveCharsWords + " шт");
    }

    private static boolean isLettersIncreasing(String name) {
        char[] chars = name.toCharArray();
        Arrays.sort(chars);
        String sortedName = new String(chars);
        return name.equals(sortedName);
    }

    private static boolean isOneChar(String name) {
        return name.chars().allMatch(c -> c == name.charAt(0));
    }

    private static boolean isPalindrome(String name) {
        StringBuilder reverseName = new StringBuilder();
        for (int i = name.length() - 1; i >= 0; i--) {
            reverseName.append(name.charAt(i));
        }
        return name.contentEquals(reverseName);
    }

    private static void updateCounter(String name) {
        if (name.length() == 3) {
            threeCharsWords.incrementAndGet();
        }
        if (name.length() == 4) {
            fourCharsWords.incrementAndGet();
        }
        if (name.length() == 5) {
            fiveCharsWords.incrementAndGet();
        }
    }

    public static String generateText(String letters, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}