package com.alti.toll;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int totalJourneys = 0;
        for(LogEntry entry:logEntries){
            if(entry.getBoothType().equals("EXIT")){
                totalJourneys++;
            }
        }
        System.out.println("total Journeys: "+totalJourneys);
        return totalJourneys;

    }

    public List<String> catchSpeeders() {
        List<String> speeders = new ArrayList<>();
        List<List<LogEntry>> violatedJourneysByPlate = new ArrayList<>();

        // Group entries by license plate
        Map<String, List<LogEntry>> journeysByPlate = new HashMap<>();
        for (LogEntry entry : logEntries) {
            journeysByPlate.computeIfAbsent(entry.getLicensePlate(), k -> new ArrayList<>()).add(entry);
        }
        
        // Process each license plate's entries
        for (String licensePlate : journeysByPlate.keySet()) {
            List<LogEntry> entries = journeysByPlate.get(licensePlate);
            
            // Extract complete journeys for this license plate
            List<List<LogEntry>> journeys = extractJourneys(entries);
            
            // Check each journey for speed violations
            for (List<LogEntry> journey : journeys) {
                if (hasSpeedViolation(journey)) {
                    speeders.add(licensePlate);
                    violatedJourneysByPlate.add(journey);
                }
            }
        }
        System.out.println("Violated Journeys:");
        violatedJourneysByPlate.forEach(journey -> {
            System.out.println("  Entry: " + journey.get(0) + " -> Exit: " + journey.get(journey.size() - 1));
        });
        return speeders;
    }
    
    private List<List<LogEntry>> extractJourneys(List<LogEntry> entries) {
        List<List<LogEntry>> journeys = new ArrayList<>();
        List<LogEntry> currentJourney = new ArrayList<>();
        
        for (LogEntry entry : entries) {
            currentJourney.add(entry);
            
            // A journey ends with an EXIT booth
            if (entry.getBoothType().equals("EXIT")) {
                journeys.add(new ArrayList<>(currentJourney));
                currentJourney.clear();
            }
        }
        
        return journeys;
    }
    
    private boolean hasSpeedViolation(List<LogEntry> journey) {
        // A journey should start with ENTRY and end with EXIT
        if (journey.isEmpty() || !journey.get(0).getBoothType().equals("ENTRY") ||
            !journey.get(journey.size() - 1).getBoothType().equals("EXIT")) {
            return false;
        }
        
        List<Float> segmentSpeeds = new ArrayList<>();
        
        // Calculate speed for each segment (between consecutive toll booth entries)
        for (int i = 0; i < journey.size() - 1; i++) {
            LogEntry current = journey.get(i);
            LogEntry next = journey.get(i + 1);
            
            // Calculate time difference in seconds
            float timeDifference = next.getTimestamp() - current.getTimestamp();
            
            // Distance between toll booths is always 10 km (they are 10 km apart)
            float distance = 10.0f; // km
            
            // Speed in km/h = (distance * 3600) / time_in_seconds
            float speedKmh = (distance * 3600) / timeDifference;
            segmentSpeeds.add(speedKmh);
        }
        
        // Check for violations:
        // 1. Any segment >= 130 km/h
        for (float speed : segmentSpeeds) {
            if (speed >= 130.0f) {
                return true;
            }
        }
        
        // 2. Two or more segments >= 120 km/h
        int count120Plus = 0;
        for (float speed : segmentSpeeds) {
            if (speed >= 120.0f) {
                count120Plus++;
            }
        }
        
        if (count120Plus >= 2) {
            return true;
        }
        
        return false;
    }
}
