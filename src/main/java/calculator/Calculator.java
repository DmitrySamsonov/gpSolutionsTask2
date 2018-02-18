package calculator;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(100);

    public static void main(String[] args) throws IOException {
        new Calculator().startCalculator();
    }

    private void startCalculator() {
        System.out.println("Enter \'exit\' for program exit.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Enter expression: ");
                String input = scanner.nextLine();
                if (input.equals("exit"))
                    break;

                if (input.length() != 0) {
                    BigDecimal result = calculate(input);
                    System.out.println(result);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }


    public BigDecimal calculate(String inputDataString) {
        String reversePolishNotation = computeReversePolishNotation(inputDataString);
        return counting(reversePolishNotation);
    }


    private String computeReversePolishNotation(String input) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operations = new Stack<>();

        for (int i = 0; i < input.length(); i++) {

            if (isDelimeter(input.charAt(i)))
                continue;


            if (isNumber(input, i)) {
                if (input.charAt(i) == '-') {
                    output.append(input.charAt(i));
                    i++;
                }

                while (i < input.length() && !isDelimeter(input.charAt(i)) && !isOperator(input.charAt(i))) {

                    if (!Character.isDigit(input.charAt(i)) && input.charAt(i) != '.' && input.charAt(i) != ',') {
                        throw new IllegalArgumentException("Not valid input data. invalid number");
                    }

                    output.append(input.charAt(i));
                    i++;
                }

                output.append(" ");
                i--;

                checkWhitespacesInsideNumber(input, i);
            }


            if (isOperator(input.charAt(i))) {
                char operator = input.charAt(i);
                if (operator == '(')
                    operations.push(operator);
                else if (operator == ')') {

                    char s = operations.pop();

                    while (s != '(') {
                        output.append(String.valueOf(s)).append(' ');
                        s = operations.pop();
                    }
                } else {
                    if (operations.size() > 0)
                        if (getPriority(operator) <= getPriority(operations.peek()))
                            output.append(operations.pop().toString()).append(" ");


                    operations.push(operator);
                }
            }
        }


        while (operations.size() > 0)
            output.append(operations.pop()).append(" ");

        return output.toString();
    }

    private void checkWhitespacesInsideNumber(String input, int i) {
        i++;
        while (i < input.length() && isDelimeter(input.charAt(i))) {
            i++;
        }

        if (i < input.length() && !isOperator(input.charAt(i))) {
            throw new IllegalArgumentException("Not valid input data. Whitespaces inside number");
        }
    }

    private boolean isNumber(String input, int i) {
        if ('-' == input.charAt(i)) {
            if (i > 0)
                i--;
            while (isDelimeter(input.charAt(i))) {
                if (i > 0) {
                    i--;
                } else {
                    return false;
                }
            }
            if (isOperator(input.charAt(i))) {
                return true;
            }
        } else {
            if (Character.isDigit(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isDelimeter(char c) {
        return "= ".indexOf(c) != -1;
    }

    private boolean isOperator(char с) {
        return "+-/*^()".indexOf(с) != -1;
    }

    private byte getPriority(char s) {
        switch (s) {
            case '(':
                return 0;
            case ')':
                return 1;
            case '+':
                return 2;
            case '-':
                return 3;
            case '*':
                return 4;
            case '/':
                return 4;
            case '^':
                return 5;
            default:
                return 6;
        }
    }


    private BigDecimal counting(String input) {
        BigDecimal result = BigDecimal.ZERO;
        Stack<BigDecimal> temp = new Stack<>();

        for (int i = 0; i < input.length(); i++) {

            if (isNumberInReversePolishNotation(input, i)) {
                StringBuilder numberBuilder = new StringBuilder();

                if (input.charAt(i) == '-') {
                    numberBuilder.append(input.charAt(i));
                    i++;
                }

                while (i < input.length() && !isDelimeter(input.charAt(i)) && !isOperator(input.charAt(i))) {
                    numberBuilder.append(input.charAt(i));
                    i++;

                }
                String numberString = numberBuilder.toString().replace(",", ".");
                temp.push(new BigDecimal(numberString));
                i--;
            } else if (isOperator(input.charAt(i))) {

                BigDecimal a;
                BigDecimal b;
                try {
                    a = temp.pop();
                    b = temp.pop();
                } catch (EmptyStackException e) {
                    throw new IllegalArgumentException("Not valid input data");
                }

                switch (input.charAt(i)) {
                    case '+':
                        result = b.add(a);
                        break;
                    case '-':
                        result = b.subtract(a);
                        break;
                    case '*':
                        result = b.multiply(a);
                        break;
                    case '/':
                        if (a.intValue() == 0) {
                            throw new IllegalArgumentException("Error: Cannot divide by zero.");
                        }
                        result = b.divide(a);
                        break;
                    case '^':
                        result = BigDecimalMath.pow(b, a, DEFAULT_MATH_CONTEXT);
                        break;
                }
                temp.push(result);
            }
        }
        return temp.peek();
    }

    private boolean isNumberInReversePolishNotation(String input, int i) {
        if ('-' == input.charAt(i)) {
            if (i < input.length() - 1) {
                i++;
                if (Character.isDigit(input.charAt(i))) {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            if (Character.isDigit(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
