package com.alti.toll;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

class LogFile {

    /*
     * Represents a file containing a number of log lines, converted to LogEntry
     * objects.
     */

    List<LogEntry> logEntries;

    public LogFile(BufferedReader reader) throws IOException {
        this.logEntries = new ArrayList<>();
        if (reader != null) {
            String line = reader.readLine();
            while (line != null) {
                LogEntry logEntry = new LogEntry(line.strip());
                this.logEntries.add(logEntry);
                line = reader.readLine();
            }
        }
    }

    public LogEntry get(int index) {
        return this.logEntries.get(index);
    }

    public int size() {
        return this.logEntries.size();
    }

    public int countJourneys(){

        return 0;

    }

    public List<String> catchSpeeders() {

        return null;
    }
}
