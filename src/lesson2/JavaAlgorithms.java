package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;
import lesson1.JavaTasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     *
     * @return
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        // Трудоемкость: O(n^2)
        // ?
        int maxSell = -1;
        int minBuy = -1;
        int maxDiff = -1;
        int curDiff;
        try {
            List<String> list = JavaTasks.readInputFile(inputName);

            List<Integer> intList = new ArrayList<>();
            for (String elem : list) {
                intList.add(Integer.parseInt(elem));
            }
            for (int i = 0; i < intList.size(); i++) {
                for (int j = i; j < intList.size(); j++) {
                    curDiff = intList.get(j) - intList.get(i);
                    if (curDiff > maxDiff) {
                        maxDiff = curDiff;
                        maxSell = j;
                        minBuy = i;
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Wrong data format");
        }
        long end = System.currentTimeMillis();
        return new Pair<>(minBuy + 1, maxSell + 1);
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    // Трудоемкость: О(nm)
    // Ресурсоемкость: O(nm)
    static public String longestCommonSubstring(String first, String second) {
        boolean sub = false;
        int commonLength = 0;
        int indexMax = 0;
        int f_length = first.length();
        int s_length = second.length();
        if (f_length == 0 || s_length == 0) {
            return "";
        }
        if (first.equals(second)) {
            return second;
        }
        int[][] commonTable = new int[f_length][s_length];
        for (int i = 0; i < f_length; i++) {
            for (int j = 0; j < s_length; j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    if (i != 0 && j != 0) {
                        sub = true;
                        commonTable[i][j] = commonTable[i - 1][j - 1] + 1;
                    } else {
                        commonTable[i][j] = 1;
                    }
                    if (commonTable[i][j] > commonLength) {
                        commonLength = commonTable[i][j];
                        indexMax = i;
                    }
                } else {
                    commonTable[i][j] = 0;
                }
            }
        }
        if (!sub) {
            return "";
        }

        return first.substring(indexMax - commonLength + 1, indexMax + 1);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    //Трудоемкость: O(n log(log(n)))
    //Ресурсоемкоть: O(n)
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) {
            return 0;
        }
        //List<Integer> list = new ArrayList<>();
        int[] temp = new int[limit + 1];
        int count = 0;
        for (int i = 0; i < limit + 1; i++) {
            temp[i] = i;
        }
        for (int i = 2; i < limit + 1; i++) {
            if (temp[i] != 0) {
                count++;
            }
            int j = i;
            while (j < limit + 1) {
                temp[j] = 0;
                j = j + i;
            }
        }
        return count;
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
