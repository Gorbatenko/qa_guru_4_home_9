package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    private Randomizer() {
    }

    /**
     * Генерация случайной строки с заданным количеством символов
     *
     * @param symbolsCount количество символов
     * @return сгенерированная строка
     */
    public static String randomAlphabetic(final int symbolsCount) {
        return RandomStringUtils.randomAlphabetic(symbolsCount);
    }

    /**
     * Генерация строки со случайными числами до заданного диапазона
     *
     * @param range диапазон чисел
     * @return сгенерированная строка
     */
    public static String randomNumeric(final int range) {
        return RandomStringUtils.randomNumeric(range);
    }

    /**
     * Генерация случайного числа от 0 до заданной границы
     *
     * @param to граничное значение
     * @return случайное целое число
     */
    public static int randomInt(final int to) {
        return ThreadLocalRandom.current().nextInt(to);
    }

    /**
     * Генерация случайного числа от начальной границы до конечной границы
     *
     * @param from начальная граница
     * @param to   конечная граница
     * @return случайное целое число
     */
    public static int randomInt(final int from, final int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }

    /**
     * Генерация случайного числа от 0 до заданной границы
     *
     * @param to граничное значение
     * @return случайное целое число
     */
    public static long randomLong(final long to) {
        return ThreadLocalRandom.current().nextLong(to);
    }

    /**
     * Генерация случайного числа от начальной границы до конечной границы
     *
     * @param from начальная граница
     * @param to   конечная граница
     * @return случайное целое число
     */
    public static long randomLong(final long from, final long to) {
        return ThreadLocalRandom.current().nextLong(from, to);
    }

    /**
     * Генерация уникального идентификатора
     *
     * @return строка с UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Генерация уникального идентификатора
     *
     * @param start время начала отсчёта даты
     * @return полная дата / год / месяц / день
     */
    public static Calendar randomDate(Long start) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Helsinki"));
        cal.setTime(new Date(randomLong(start, LocalDate.now().toEpochDay())));
        return cal;
    }

    /**
     * Генерация случайного числа от 0 до заданной границы
     *
     * @param to граничное значение
     * @return случайное число с плавающей точкой
     */
    public static double randomDouble(final double to) {
        String val = convertDoubleToString(ThreadLocalRandom.current().nextDouble(to));
        return parseStringToDouble(val);
    }

    /**
     * Генерация случайного числа от начальной границы до конечной границы
     *
     * @param from начальная граница
     * @param to   конечная граница
     * @return случайное число с плавающей точкой
     */
    public static double randomDouble(final double from, final double to) {
        String val = convertDoubleToString(ThreadLocalRandom.current().nextDouble(from, to));
        return parseStringToDouble(val);
    }

    /**
     * Генерация случайного количества текстовых значений из предложенных вариантов.
     * Значения не дублируются.
     *
     * @param args начальная граница
     * @return случайный список текстовых значений из предложенных вариантов
     */
    public static List<String> randomValuesFromVariant(String ...args){
        List<String> response = new ArrayList<>();
        int responseLength = randomInt(args.length);

        int lengthCounter = 0;
        List<Integer> activeIndexes = new ArrayList<>();
        while (lengthCounter <= responseLength) {
            int index = randomInt(args.length);

            boolean isIndexActive = false;
            for (Integer activeIndex : activeIndexes) {
                if (activeIndex == index) {
                    isIndexActive = true;
                    break;
                }
            }

            if (!isIndexActive) {
                response.add(args[index]);
                activeIndexes.add(index);
                lengthCounter++;
            }
        }

        return response;
    }

    /**
     * Выбор случайного значения из предложенных вариантов.
     *
     * @param args начальная граница
     * @return случайное значение из предложенных вариантов
     */
    public static String randomValueFromVariant(String ...args){
        return args[randomInt(args.length)];
    }

    /**
     * Приводит число с плавующей точкой к строке с форматом ##.##
     *
     * @param value число с плавающей точкой
     * @return форматированная строка
     */
    private static String convertDoubleToString(final double value) {
        DecimalFormat df = new DecimalFormat("##.##");
        return df.format(value);
    }

    /**
     * Парсит строку в формате ##.## в число
     *
     * @param value строка в формате ##.##
     * @return число с плавающей точкой в заданном формате
     */
    private static Double parseStringToDouble(final String value) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        try {
            return numberFormat.parse(value).doubleValue();
        } catch (ParseException e) {
            return 0.0;
        }
    }


}
