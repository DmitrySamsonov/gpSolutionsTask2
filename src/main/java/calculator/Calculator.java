package calculator;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    public static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(100);

    public static void main(String[] args) throws IOException {
        System.out.println("Enter \'exit\' for quite:");
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


    static public BigDecimal calculate(String input) {
        String output = getExpression(input);
        return counting(output);
    }


    static private String getExpression(String input) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operStack = new Stack<>();

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
                        throw new IllegalArgumentException("Invalid data");
                    }

                    output.append(input.charAt(i));
                    i++;
                }

                output.append(" ");
                i--;

                int j = i + 1;
                while (j < input.length() && isDelimeter(input.charAt(j))) {
                    j++;
                }

                if (j < input.length() && !isOperator(input.charAt(j))) {
                    throw new IllegalArgumentException("Invalid data");
                }
            }


            if (isOperator(input.charAt(i))) {
                if (input.charAt(i) == '(')
                    operStack.push(input.charAt(i));
                else if (input.charAt(i) == ')') {

                    char s = operStack.pop();

                    while (s != '(') {
                        output.append(String.valueOf(s)).append(' ');
                        s = operStack.pop();
                    }
                } else {
                    if (operStack.size() > 0)
                        if (getPriority(input.charAt(i)) <= getPriority(operStack.peek()))
                            output.append(operStack.pop().toString()).append(" ");


                    operStack.push(input.charAt(i));
                }
            }
        }


        while (operStack.size() > 0)
            output.append(operStack.pop()).append(" ");

        return output.toString();
    }

    private static boolean isNumber(String input, int i) {
        if ('-' == input.charAt(i)) {
            i++;
            while (isDelimeter(input.charAt(i))) {
                if (i >= input.length()) return false;
                i++;
            }
            if (Character.isDigit(input.charAt(i))) {
                return true;
            }
        } else {
            if (Character.isDigit(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    static private boolean isDelimeter(char c) {
        if ((" =".indexOf(c) != -1))
            return true;
        return false;
    }


    static private boolean isOperator(char с) {
        if (("+-/*^()".indexOf(с) != -1))
            return true;
        return false;
    }


    static private byte getPriority(char s) {
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


    static private BigDecimal counting(String input) {
        BigDecimal result = BigDecimal.ZERO;
        Stack<BigDecimal> temp = new Stack<>();

        for (int i = 0; i < input.length(); i++) {

            if (isNumber(input, i)) {
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

                BigDecimal a = temp.pop();
                BigDecimal b = temp.pop();

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

}
