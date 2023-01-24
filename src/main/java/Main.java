import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static AtomicInteger counter3 = new AtomicInteger();
    private static AtomicInteger counter4 = new AtomicInteger();
    private static AtomicInteger counter5 = new AtomicInteger();

    public static void main(String[] args)  {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isInAscendingOrder(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + counter3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter5.get() + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void incrementCounter(int textLength) {
        switch (textLength) {
            case 3:
                counter3.incrementAndGet();
                break;
            case 4:
                counter4.incrementAndGet();
                break;
            case 5:
                counter5.incrementAndGet();
                break;
        }
    }  // Инкремент счётчика. Увеличивает один из трёх счётчиков в зависимости от входных параметров типа int


    private static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }
    // Самый простой способ реверса строки — использовать StringBuilder.reverse().
    // .toString() - преобразует StringBuilder в строку.


    private static boolean isSameLetter(String text) {
        return text.chars().allMatch(c -> c == text.charAt(0));
    }
    // Простое решение для получения IntStream символов с помощью String.chars() метод.
    // allMatch метод проверяет, что все элементы в потоке соответствуют правилу.
    // Правило: все символы равны(одинаковы) с первым символом.


    private static boolean isInAscendingOrder(String text) {
        char[] chars = text.toCharArray();
        Arrays.sort(chars);
        return text.equals(String.valueOf(chars));
    }
    // Для проверки расположения символов по возрастанию используем сравнение строки
    // с отсортированным массивом символов этой же строки.


}

