package calculator;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    public static void main(String[] args) {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();

        try {
            int result = add(input);
            System.out.println("결과 : " + result);

        } catch (IllegalArgumentException e) {
            System.out.println("오류: " + e.getMessage());
        }
    }

    public static String[] splitNumber(String text) {
        String delimiter = ",|:";
        String numbersText = text;

        if (text.startsWith("//")) {
            Matcher matcher = Pattern.compile("//(.)\\\\n(.*)").matcher(text);
            if (matcher.matches()) {
                delimiter = Pattern.quote(matcher.group(1));
                numbersText = matcher.group(2);
            }
        }

        return numbersText.split(delimiter);
    }

    public static int add(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] numbers = splitNumber(input);
        return sum(numbers);
    }

    public static int sum(String[] numbers) {
        int sum = 0;
        List<Integer> negativeNumbers = new ArrayList<>();

        for (String numberStr : numbers) {
            if (numberStr.trim().isEmpty()) {
                continue;
            }

            try {
                int number = Integer.parseInt(numberStr.trim());
                if (number < 0) {
                    negativeNumbers.add(number);
                    continue;
                }
                sum += number;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "잘못된 숫자 형식이 입력되었습니다: " + numberStr, e);
            }
        }

        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException(
                    "음수는 입력할 수 없습니다: " + negativeNumbers);
        }

        return sum;
    }
}