import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class Application {

    private static final String TAB = "\\t";
    private static Set<Long> DURATION_IN_DAYS = new HashSet<>(Arrays.asList(1L, 3L, 7L, 30L, 90L, 180L, 365L));

    public static void main(String[] args) {
        findWordByDictionary(0, 1); // word index of line as a words array -> en, ru
        findWordByDictionary(1, 0); // word index of line as a words array -> ru. en
    }

    private static void findWordByDictionary(int first, int second) {
        try (Stream<String> stream = Files.lines(Paths.get("dictionary.txt"), Charset.forName("utf8"))) {
            stream.filter(r -> checkRecordDuration(r.split(TAB)[2])).forEach(r -> printWordAndMeaning(r.split(TAB)[first], r.split(TAB)[second]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check record duration
     * @param date date of record creation
     * @return true if record duration one of durations
     */
    private static boolean checkRecordDuration(String date) {
        return DURATION_IN_DAYS.contains(Duration.between(LocalDate.parse(date).atStartOfDay(), LocalDate.now().atStartOfDay()).toDays());
    }

    private static void printWordAndMeaning(String word, String meaning) {
        System.out.println(word);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println(meaning + "\n__________________\n");
    }
}