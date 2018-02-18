package calculatorTest;

import calculator.Calculator;
import ch.obermuhlner.math.big.BigDecimalMath;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalculatorTest {

    private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(100);

    private Calculator calculator;
    private String input;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void createCalculator() {
        calculator = new Calculator();
    }

    @Test
    public void calculate_Add() {
        input = "3+3";
        BigDecimal expected = new BigDecimal(6);
        BigDecimal actual = calculator.calculate(input);
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_Subtract() {
        input = "3-3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = BigDecimal.ZERO;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_Multiply() {
        input = "3*3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = new BigDecimal(9);
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_Divide() {
        input = "3/3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = BigDecimal.ONE;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_Exponentiation() {
        input = "3^3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = new BigDecimal(27);
        assertEquals(expected, actual);
    }


    @Test
    public void calculate_AddWithSpaces() {
        input = "3  +3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = new BigDecimal(6);
        assertEquals(expected, actual);

        input = "3+   3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(6);
        assertEquals(expected, actual);

        input = "   3   +3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(6);
        assertEquals(expected, actual);

        input = " 3+   3    ";
        actual = calculator.calculate(input);
        expected = new BigDecimal(6);
        assertEquals(expected, actual);

        input = " 3     +   3    ";
        actual = calculator.calculate(input);
        expected = new BigDecimal(6);
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_SubtractWithSpaces() {
        input = "3  -3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = BigDecimal.ZERO;
        assertEquals(expected, actual);

        input = "3-   3";
        actual = calculator.calculate(input);
        expected = BigDecimal.ZERO;
        assertEquals(expected, actual);

        input = "   3   -3";
        actual = calculator.calculate(input);
        expected = BigDecimal.ZERO;
        assertEquals(expected, actual);

        input = " 3-   3    ";
        actual = calculator.calculate(input);
        expected = BigDecimal.ZERO;
        assertEquals(expected, actual);

        input = " 3     -   3    ";
        actual = calculator.calculate(input);
        expected = BigDecimal.ZERO;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_MultiplyWithSpaces() {
        input = "3  *3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = new BigDecimal(9);
        assertEquals(expected, actual);

        input = "3*   3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(9);
        assertEquals(expected, actual);

        input = "   3   *3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(9);
        assertEquals(expected, actual);

        input = " 3*   3    ";
        actual = calculator.calculate(input);
        expected = new BigDecimal(9);
        assertEquals(expected, actual);

        input = " 3     *   3    ";
        actual = calculator.calculate(input);
        expected = new BigDecimal(9);
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_DivideWithSpaces() {
        input = "3  /3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = new BigDecimal(1);
        assertEquals(expected, actual);

        input = "3/   3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(1);
        assertEquals(expected, actual);

        input = "   3   /3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(1);
        assertEquals(expected, actual);

        input = " 3/   3    ";
        actual = calculator.calculate(input);
        expected = new BigDecimal(1);
        assertEquals(expected, actual);

        input = " 3     /   3    ";
        actual = calculator.calculate(input);
        expected = new BigDecimal(1);
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_ExponentiationWithSpaces() {
        input = "3  ^3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = new BigDecimal(27);
        assertEquals(expected, actual);

        input = "3^   3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(27);
        assertEquals(expected, actual);

        input = "   3   ^3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(27);
        assertEquals(expected, actual);

        input = " 3^   3    ";
        actual = calculator.calculate(input);
        expected = new BigDecimal(27);
        assertEquals(expected, actual);

        input = " 3     ^   3    ";
        actual = calculator.calculate(input);
        expected = new BigDecimal(27);
        assertEquals(expected, actual);
    }


    @Test
    public void calculate_AddWithNegativeNumbers() {
        input = "3  + -3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = BigDecimal.ZERO;
        assertEquals(expected, actual);

        input = "-3  + 4";
        actual = calculator.calculate(input);
        expected = BigDecimal.ONE;
        assertEquals(expected, actual);

        input = "-3  + -3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(-6);
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_SubtractWithNegativeNumbers() {
        input = "3  - -3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = new BigDecimal(6);
        assertEquals(expected, actual);

        input = "-3  - 4";
        actual = calculator.calculate(input);
        expected = new BigDecimal(-7);
        assertEquals(expected, actual);

        input = "-3  - -3";
        actual = calculator.calculate(input);
        expected = BigDecimal.ZERO;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_MultiplyWithNegativeNumbers() {
        input = "3  * -3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = new BigDecimal(-9);
        assertEquals(expected, actual);

        input = "-3  * 4";
        actual = calculator.calculate(input);
        expected = new BigDecimal(-12);
        assertEquals(expected, actual);

        input = "-3  * -3";
        actual = calculator.calculate(input);
        expected = new BigDecimal(9);
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_DivideWithNegativeNumbers() {
        input = "3  / -3";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = new BigDecimal(-1);
        assertEquals(expected, actual);

        input = "-3  / 4";
        actual = calculator.calculate(input);
        expected = new BigDecimal(-3).divide(new BigDecimal(4));
        assertEquals(expected, actual);

        input = "-3  / -3";
        actual = calculator.calculate(input);
        expected = BigDecimal.ONE;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_ExponentiationWithNegativeNumbers() {
        input = "9  ^ -2";
        BigDecimal actual = calculator.calculate(input);
        BigDecimal expected = BigDecimalMath.pow(new BigDecimal(9), new BigDecimal(-2), DEFAULT_MATH_CONTEXT);
        assertEquals(expected, actual);

        input = "-3  ^ 2";
        actual = calculator.calculate(input);
        expected = BigDecimalMath.pow(new BigDecimal(-3), new BigDecimal(2), DEFAULT_MATH_CONTEXT);
        assertEquals(expected, actual);

        input = "-8  ^ -3";
        actual = calculator.calculate(input);
        expected = BigDecimalMath.pow(new BigDecimal(-8), new BigDecimal(-3), DEFAULT_MATH_CONTEXT);
        assertEquals(expected, actual);
    }


    @Test
    public void calculate_CheckSpacePositiveNumber() {
        input = "3 + 33 5";
        boolean actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);


        input = "3 2 +    3";
        actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);


        input = " 3 ,7 + 3.2";
        actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);


        input = "3. 4 +    3,6";
        actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    public void calculate_CheckSpaceInNegativeNumber() {
        input = "- 3 + 3";
        boolean actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);

        input = "- 3 6+ 3";
        actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);


        input = "-3 +  -  3";
        actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);


        input = "- 3,7 + 3.2";
        actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);


        input = "-3.4 +  -  3,6";
        actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    public void calculate_DivideByZero() {
        input = "3 /0";
        thrown.expect(IllegalArgumentException.class);
        calculator.calculate(input);
    }

    @Test
    public void calculate_NoArgs() {
        input = " +";
        thrown.expect(IllegalArgumentException.class);
        calculator.calculate(input);
    }

    @Test
    public void calculate_OneArgs() {
        input = "8 -  ";
        boolean actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);

        input = " - 3 ";
        actual = false;
        try {
            calculator.calculate(input);
        } catch (IllegalArgumentException ex) {
            actual = true;
        }
        assertTrue(actual);
    }


}
