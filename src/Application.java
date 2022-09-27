import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    private static final String TAB = "\\t";
    private static final Set<Long> DURATION_IN_DAYS = new HashSet<>(Arrays.asList(1L, 3L, 7L, 30L, 90L, 180L, 365L, 545L, 730L, 913L, 1095L));
    private static final String SEE_YOU = "See you next time.";

    public static void main(String[] args) throws IOException {
        List<DictionaryRecord> dictionaryRecords = findWordsForToday();
        Scanner scanner = new Scanner(System.in);
        if (dictionaryRecords.isEmpty()) {
            System.out.print("\nNothing to repeat today.  " + SEE_YOU);
            scanner.nextLine();
        } else {
            dictionaryRecords.forEach(r -> printWordAndMeaning(r.word, r.meaning));
            dictionaryRecords.forEach(r -> printWordAndMeaning(r.meaning, r.word));
            System.out.print("\nGood job! " + SEE_YOU);
            scanner.nextLine();
        }
    }

    private static List<DictionaryRecord> findWordsForToday() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("C:\\myDictionary\\dictionary.txt"), StandardCharsets.UTF_8)) {
            return stream
                    .map(r -> r.split(TAB))
                    .filter(r -> checkRecordDuration(r[2]))
                    .map(DictionaryRecord::new)
                    .collect(Collectors.toList());
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

    private static void printWordAndMeaning(String firstPrintedText, String secondPrintedText) {
        System.out.print(firstPrintedText);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.print(secondPrintedText + "\n-------!!--------!!---\n");
    }

    private static class DictionaryRecord {
        private final String word;
        private final String meaning;

        private DictionaryRecord(String[] dictionaryLine) {
            this.word = dictionaryLine[0];
            this.meaning = dictionaryLine[1];
        }
    }
}
