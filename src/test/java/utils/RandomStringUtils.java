package utils;

public class RandomStringUtils {
    private RandomStringUtils() {}

    /**
     * Генерация случайной строки с заданным количеством символов
     * @param symbolsCount количество символов
     * @return сгенерированная строка
     */
    public static String randomAlphabetic(final int symbolsCount) {
        return RandomStringUtils.randomAlphabetic(symbolsCount);
    }

    /**
     * Генерация строки со случайными числами до заданного диапазона
     * @param range диапазон чисел
     * @return сгенерированная строка
     */
    public static String randomNumeric(final int range) {
        return RandomStringUtils.randomNumeric(range);
    }
}
