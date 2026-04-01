package com.alti.toll;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class LogFile {

    /*
     * Represents a file containing a number of log lines, converted to LogEntry
     * objects.
     */

    List<LogEntry> logEntries;

    public LogFile(BufferedReader reader) throws IOException {
        this.logEntries = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            LogEntry logEntry = new LogEntry(line.strip());
            this.logEntries.add(logEntry);
            line = reader.readLine();
        }
    }

    public LogEntry get(int index) {
        return this.logEntries.get(index);
    }

    public int size() {
        return this.logEntries.size();
    }

    public int countJourneys(){
        Set<String> set=new HashSet<>();
        int totalJourneys = 0;
        for(LogEntry entry:logEntries){
            String booth1=entry.getBoothType();
            String carNo=entry.getLicensePlate();
            if(booth1.equals("ENTRY")){
                set.add(carNo);
            }else if(booth1.equals("EXIT")){
                if(set.contains(carNo)){
                    totalJourneys++;
                }
            }
        }
        System.out.println("total Journeys: "+totalJourneys);
        return totalJourneys;

    }

}
