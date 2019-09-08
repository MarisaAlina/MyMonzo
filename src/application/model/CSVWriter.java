package application.model;

import java.util.ArrayList;
import java.util.List;

public class CSVWriter {

    // todo has to process List of LineItems to become String
    // another option: xml export - will need input change
    public static String createCSVString(List<List<LineItem>> data, final String separator) {
        List<String> csv = new ArrayList<>();
//        data.forEach(line ->
//                csv.add(line.stream().
//                        reduce((t, u) -> t + separator + u).
//                        get() + separator)
//        );

        StringBuilder csvString = new StringBuilder();

        for (String line : csv) {
            csvString.append(line);
            csvString.append("\n");
        }

        return csvString.toString();
    }
}


