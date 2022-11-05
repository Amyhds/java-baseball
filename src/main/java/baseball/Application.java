package baseball;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static final Integer EXCEPT_OCCUR = -1;
    public static final Integer RE_GAME = 1;
    public static final Integer END_GAME = 2;

    public static boolean isLength(String inputNum) {
        if (inputNum.length() != 3) {
            return false;
        }
        return true;
    }

    public static boolean isDiff(String inputNum) {
        for (int i = 0; i < inputNum.length() - 1; i++) {
            if (inputNum.charAt(i) == inputNum.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRange(String inputNum) {
        for (int i = 0; i < inputNum.length(); i++) {
            int number = Character.getNumericValue(inputNum.charAt(i));
            if (number < 1 || number > 10) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValid(String inputNum) {
        if (isDiff(inputNum) && isLength(inputNum) && isRange(inputNum)) {
            return true;
        }
        return false;
    }

    public static List<Integer> computerPick() {
        List<Integer> computer = new ArrayList<>();
        while (computer.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber);
            }
        }
        return computer;
    }

    public static List<Integer> userPick() {
        System.out.print("숫자를 입력해주세요 : ");
        String inputNum = Console.readLine();
        if (isValid(inputNum)) {
            String[] tempNum = inputNum.split("");
            List<Integer> user = new ArrayList<>();
            for (int i = 0; i < tempNum.length; i++) {
                user.add(Integer.valueOf(tempNum[i]));
            }
            return user;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Integer locate(List<Integer> computerNum, List<Integer> userNum) {
        int sameCount = 0;
        for (int i = 0; i < 3; i++) {
            if (computerNum.get(i) == userNum.get(i)) {
                sameCount++;
            }
        }
        return sameCount;
    }

    public static Integer contain(List<Integer> computerNum, List<Integer> userNum) {
        int containCount = 0;
        for (Integer num : userNum) {
            if (computerNum.contains(num)) {
                containCount++;
            }
        }
        return containCount;
    }

    public static List<Integer> compare(List<Integer> computerNum, List<Integer> userNum) {
        List<Integer> result = new ArrayList<>();
        int strike = locate(computerNum, userNum);
        int ball = contain(computerNum, userNum) - strike;
        result.add(strike);
        result.add(ball);
        return result;
    }

    public static Integer continueGame(List<Integer> result) {

    }

    public static void printResult(List<Integer> result) {
        int strike = result.get(0);
        int ball = result.get(1);
        if (strike == 3) {
            System.out.println("3스트라이크");
            System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
        } else if (strike > 0 && ball > 0) {
            System.out.println(ball+"볼"+" "+strike+"스트라이크");
        } else if (strike > 0 && ball == 0) {
            System.out.println(strike+"스트라이크");
        } else if (strike == 0 && ball > 0) {
            System.out.println(ball+"볼");
        } else if (strike == 0 && ball == 0) {
            System.out.println("낫싱");
        }

    }

    public static Integer playGame() {
        try {
            List<Integer> computerNum = computerPick();
            List<Integer> userNum = userPick();
            List<Integer> result = compare(computerNum, userNum);
            printResult(result);
            return continueGame(result);
        } catch (IllegalArgumentException e) {
            return EXCEPT_OCCUR;
        }
    }

    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다");
        while(true){
            int gameResult = playGame();
            if (gameResult == END_GAME || gameResult == EXCEPT_OCCUR) {
                break;
            }
        }
    }
}
