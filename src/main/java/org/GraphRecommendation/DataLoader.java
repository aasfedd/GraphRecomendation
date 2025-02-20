package org.GraphRecommendation;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.List;

public class DataLoader {
    public static List<String[]> loadData(String filePath) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        reader.skip(1);
            return reader.readAll();

    }
}
