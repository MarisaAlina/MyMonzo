package application.model;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVWriter.class);

    public static String createCSVString(List<LineItem> data) {

        String header="Date,Reference,Transaction Type,Money In, Money Out,Balance, Category\n";

        String placeholder = " ";
        String newLine = "\n";

        String csv = data.stream()
                .flatMap(item -> Stream.of(String.valueOf(item.getDate()),
                        String.valueOf(item.getDescription()),
                        placeholder,
                        placeholder,
                        String.valueOf(item.getAmount()),
                        String.valueOf(item.getCategory()),
                        newLine))
                .collect(Collectors.joining(","));
        LOGGER.info("csv: {}", csv);

        return header + csv;
    }

}


