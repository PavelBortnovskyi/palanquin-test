import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Marking will be based upon producing a readable, well engineered solution rather than factors
 * such as speed of processing or other performance-based optimizations, which are less
 * important.
 *
 *
 package sample;

 import java.time.LocalDate;
 import java.util.Collection;
 import java.util.List;

 /**
 * Marking will be based upon producing a readable, well engineered solution rather than factors
 * such as speed of processing or other performance-based optimizations, which are less
 * important.
 *
 * Implement in single class. Don't chane constructor and signature method.
 */
public class DateSorter {

    /**
     * The implementation of this method should sort dates.
     * The output should be in the following order:
     * Dates with an 'r' in the month,
     * sorted ascending (first to last),
     * then dates without an 'r' in the month,
     * sorted descending (last to first).
     * For example, October dates would come before May dates,
     * because October has an 'r' in it.
     * thus: (2004-07-01, 2005-01-02, 2007-01-01, 2032-05-03)
     * would sort to
     * (2005-01-02, 2007-01-01, 2032-05-03, 2004-07-01)
     *
     * @param unsortedDates - an unsorted list of dates
     * @return the collection of dates now sorted as per the spec
     */

    // Simple solution
    public Collection<LocalDate> sortDates(List<LocalDate> unsortedDates) {
        // Filter and sort dates with 'r' in the month
        List<LocalDate> datesWithR = unsortedDates.stream()
                .filter(date -> date.getMonth().name().toLowerCase().contains("r"))
                .sorted()
                .toList();

        // Filter and sort dates without 'r' in the month
        List<LocalDate> datesWithoutR = unsortedDates.stream()
                .filter(date -> !date.getMonth().name().toLowerCase().contains("r"))
                .sorted(Comparator.reverseOrder())
                .toList();

        // Combine both lists
        List<LocalDate> sortedDates = new ArrayList<>(datesWithR);
        sortedDates.addAll(datesWithoutR);

        return sortedDates;
    }

    // One stream solution
    public Collection<LocalDate> sortDatesOption(List<LocalDate> unsortedDates) {
        return unsortedDates.stream()
                .collect(Collectors.partitioningBy(date -> !date.getMonth().name().toLowerCase().contains("r")))
                .entrySet().stream()
                .flatMap(entry -> {
                    List<LocalDate> dates = entry.getValue();
                    if (!entry.getKey()) {
                        dates.sort(Comparator.naturalOrder());
                    } else {
                        dates.sort(Comparator.reverseOrder());
                    }
                    return dates.stream();
                })
                .collect(Collectors.toList());
    }

    // Checking
    public static void main(String[] args) {
        List<LocalDate> unsortedDates = Arrays.asList(
                LocalDate.of(2004, 7, 1),
                LocalDate.of(2005, 1, 2),
                LocalDate.of(2007, 1, 1),
                LocalDate.of(2032, 5, 3)
        );

        DateSorter sorter = new DateSorter();
        Collection<LocalDate> sortedDates = sorter.sortDates(unsortedDates);

        sortedDates.forEach(System.out::println);
    }
}