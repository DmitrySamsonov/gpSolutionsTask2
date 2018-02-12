package textFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TextFilter {

    public static void main(String[] args) {

        List<String> strings = new LinkedList<>();
        getStringsFromTextInputStream(strings);


        for (String line : strings) {
            boolean exist;
            String[] words = line.split(" ");
            for (String word : words) {
                exist = isWordExistInArgs(word, args);
                if (exist) {
                    System.out.println(line + ";");
                    break;
                }
            }

        }

    }

    private static boolean isWordExistInArgs(String word, String[] param) {
        for (String arg : param) {

            if (isValidRegex(arg)) {

                StringBuilder regex = new StringBuilder();
                if (!arg.startsWith("^"))
                    regex.append("^");

                regex.append(arg);

                if (!arg.endsWith("$"))
                    regex.append("$");

                if (word.matches(regex.toString())) {
                    return true;
                }

            } else {
                //if arg is a not regular expression -> work as a string
                if (word.equals(arg)) {
                    return true;
                }
            }

        }
        return false;
    }

    private static boolean isValidRegex(String arg) {
        try {
            Pattern.compile(arg);
            return true;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

    private static void getStringsFromTextInputStream(List<String> strings) {
        System.out.println("Strings : ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String input = br.readLine();
                if ("".equals(input)) {
                    break;
                }
                strings.add(input.endsWith(";") ? input.substring(0, input.length() - 1) : input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
