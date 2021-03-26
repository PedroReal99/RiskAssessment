/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.cutils;

import com.opencsv.CSVParser; 
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author morei
 */
public class FileReaderCSV {

    private Reader reader;
    private CSVParser csvparser;
    private CSVReader csvreader;
    private boolean isClosed;

    private static final char DELIMITER = ';';
    private static final char QUOTE = '"';

    public FileReaderCSV(String file) throws IOException {
        this.reader = Files.newBufferedReader(Paths.get(file));
        csvparser = new CSVParserBuilder().withSeparator(DELIMITER)
                .withQuoteChar(QUOTE).withFieldAsNull(CSVReaderNullFieldIndicator.BOTH).build();

        csvreader = new CSVReaderBuilder(reader).withCSVParser(csvparser).build();
        isClosed = false;
    }

    /**
     * Reads a line, if possible, returns null otherwise
     *
     * @return
     */
    public String[] nextLine() {
        String[] parseElements = null;
        if (isClosed) {
            return null;
        }

        try {
            parseElements = csvreader.readNext();
        } catch (IOException ex) {
            //
        }

        return parseElements;
    }

    public boolean close() {
        if (isClosed) {
            return false;
        }
        try {
            csvreader.close();
            reader.close();

        } catch (IOException ex) {
            return false;
        }
        isClosed=true;
        return true;
    }

}
