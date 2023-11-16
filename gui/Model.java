package gui;

import exception.MixedFractionException;
import jdk.jshell.JShell;
import jdk.jshell.Snippet;
import jdk.jshell.SnippetEvent;
import reference.MixedFraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Model class handles the underlying logic and calculations for the Fraction Calculator GUI application.
 * It interacts with the JShell API to evaluate mathematical expressions and performs operations on mixed fractions.
 *
 * @author Junsel Fabe
 * @version 1
 */
public class Model {

    /**
     * Calculates the result of a mathematical problem represented by a string.
     *
     * @param problem   The input string representing a mathematical problem.
     * @param character The character representing the type of calculation ('x' for multiplication, 'y' for other operations).
     * @return The result of the calculation as a formatted string.
     * @throws MixedFractionException If a mixed fraction or a mathematical error is encountered during calculation.
     */
    public String calculateProblem(String problem, char character) throws MixedFractionException {
        String answer;
        JShell shell = JShell.create();

        List<Double> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(problem);
        while (matcher.find()) {
            String match = matcher.group();
            double number = Double.parseDouble(match);
            numbers.add(number);
        }

        String formattedProblem = matcher.replaceAll("#");


        for (double number : numbers) {
            formattedProblem = formattedProblem.replaceFirst("#", String.valueOf(number));
        }

        List<SnippetEvent> events = shell.eval(formattedProblem);

        if (!events.isEmpty()) {
            SnippetEvent event = events.get(0);
            if (event.status() == Snippet.Status.VALID) {
                double result = Double.parseDouble(event.value());

                if (event.value().equals("Infinity")) {
                    throw new MixedFractionException("Undefined");
                } else if (Math.floor(result) == result) {
                    int intResult = (int) result;
                    answer = String.valueOf(intResult);
                } else {
                    if (character == 'x') {
                        answer = String.valueOf(result);
                    } else {
                        String resultStr = String.valueOf(result);
                        if (resultStr.length() > 10) {
                            resultStr = String.format("%.3f", result);
                        }
                        answer = resultStr;
                    }
                }
            } else {
                throw new MixedFractionException("Math Error");
            }
        } else {
            throw new MixedFractionException("Math Error");
        }

        return answer;
    }

    /**
     * Parses and calculates the result of a fractional expression represented by a string.
     *
     * @param problem The input string representing a fractional expression.
     * @return The result of the fractional calculation as a formatted string.
     */
    public String calculateFraction(String problem) {
        String answer = problem;
        answer = unFormatSuperscript(answer);
        answer = unFormatSubscript(answer);
        Pattern pattern = Pattern.compile("(\\d+)\\s+(\\d+)/(\\d+)|(\\d+)/(\\d+)");
        Matcher matcher = pattern.matcher(answer);

        List<MixedFraction> fractions = new ArrayList<>();
        while (matcher.find()) {
            int wholeNumber = 0;
            int numerator = 0;
            int denominator = 0;
            if (matcher.group(1) != null && matcher.group(2) != null && matcher.group(3) != null) {
                wholeNumber = Integer.parseInt(matcher.group(1).trim());
                numerator = Integer.parseInt(matcher.group(2).trim());
                denominator = Integer.parseInt(matcher.group(3).trim());
            } else if (matcher.group(4) != null && matcher.group(5) != null) {
                numerator = Integer.parseInt(matcher.group(4).trim());
                denominator = Integer.parseInt(matcher.group(5).trim());
            }
            if (numerator > 0 && denominator > 0) {
                MixedFraction mixedFraction = new MixedFraction(wholeNumber, numerator, denominator);
                fractions.add(mixedFraction);
            }
        }

        return removeDivision(answer);
    }

    /**
     * Replaces formatted superscript characters with their equivalent numbers.
     *
     * @param str The input string with superscript characters.
     * @return The string with superscript characters replaced by their numeric counterparts.
     */
    private String unFormatSuperscript(String str) {
        str = str.replaceAll("⁰", "0");
        str = str.replaceAll("¹", "1");
        str = str.replaceAll("²", "2");
        str = str.replaceAll("³", "3");
        str = str.replaceAll("⁴", "4");
        str = str.replaceAll("⁵", "5");
        str = str.replaceAll("⁶", "6");
        str = str.replaceAll("⁷", "7");
        str = str.replaceAll("⁸", "8");
        str = str.replaceAll("⁹", "9");
        str = str.replaceAll("/", "÷");
        str = str.replaceAll("⁄", "/");
        return str;
    }

    /**
     * Replaces formatted subscript characters with their equivalent numbers.
     *
     * @param str The input string with subscript characters.
     * @return The string with subscript characters replaced by their numeric counterparts.
     */
    private String unFormatSubscript(String str) {
        str = str.replaceAll("₀", "0");
        str = str.replaceAll("₁", "1");
        str = str.replaceAll("₂", "2");
        str = str.replaceAll("₃", "3");
        str = str.replaceAll("₄", "4");
        str = str.replaceAll("₅", "5");
        str = str.replaceAll("₆", "6");
        str = str.replaceAll("₇", "7");
        str = str.replaceAll("₈", "8");
        str = str.replaceAll("₉", "9");
        return str;
    }

    /**
     * Removes the '÷' character and replaces it with '/' in the given string.
     *
     * @param str The input string with '÷' characters.
     * @return The string with '÷' characters replaced by '/'.
     */
    private String removeDivision(String str) {
        str = str.replaceAll("÷", "/");
        return str;
    }

    /**
     * Converts a decimal number to a mixed fraction or a repeating decimal.
     *
     * @param decimal The input decimal number to be converted.
     * @return The converted result as a formatted string.
     */
    public String decimalToMixedFraction(double decimal) {
        int whole = (int) decimal;
        double fractionalPart = decimal - whole;
        int numerator = 0;
        int denominator = 1;
        boolean isRepeating = false;

        for (int i = 1; i <= 12; i++) {
            double multiplied = fractionalPart * Math.pow(10, i);
            int digit = (int) Math.floor(multiplied) % 10;
            if (digit == 0 && i > 1) {
                numerator = (int) (fractionalPart * Math.pow(10, i));
                denominator = (int) Math.pow(10, i);
                break;
            }
            if (i == 12) {
                isRepeating = true;
                break;
            }
        }

        if (isRepeating) {
            StringBuilder result = new StringBuilder();
            result.append(whole).append(".");
            List<Integer> pattern = new ArrayList<>();
            Map<Integer, Integer> seen = new HashMap<>();
            int currentIndex = 0;

            while (!seen.containsKey(currentIndex)) {
                int currentDigit = (int) Math.floor(fractionalPart * 10);
                pattern.add(currentDigit);
                seen.put(currentIndex, currentDigit);
                fractionalPart = fractionalPart * 10 - currentDigit;
                currentIndex++;
                if (fractionalPart == 0) {
                    break;
                }
            }

            if (fractionalPart != 0) {
                int repeatIndex = seen.get(currentIndex);
                pattern.add(currentIndex, repeatIndex);
            }

            for (int i = 0; i < pattern.size(); i++) {
                if (i == currentIndex) {
                    result.append("(");
                }
                result.append(pattern.get(i));
                if (i == pattern.size() - 1) {
                    result.append(")");
                }
            }

            return String.format("%.3f", Double.parseDouble(result.toString().replace(")", "")));
        } else if (numerator == 0) {
            return Integer.toString(whole);
        } else {
            int gcd = findGCD(numerator, denominator);
            numerator /= gcd;
            denominator /= gcd;
            if (whole == 0) {
                return toFormat(numerator, denominator);
            } else {
                return whole + toFormat(numerator, denominator);
            }
        }
    }

    /**
     * Finds the greatest common divisor (GCD) of two integers using the Euclidean algorithm.
     *
     * @param a The first integer.
     * @param b The second integer.
     * @return The GCD of the two integers.
     */
    private int findGCD(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return findGCD(b, a % b);
        }

    }

    /**
     * Converts formatted numerator and denominator to a fraction string.
     *
     * @param numerator   The numerator of the fraction.
     * @param denominator The denominator of the fraction.
     * @return The formatted fraction string.
     */
    public String toFormat(int numerator, int denominator) {
        View view = new View();
        return view.diagonalFraction(numerator, denominator);
    }
}