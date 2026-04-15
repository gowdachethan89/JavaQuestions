package com.alti.toll;


import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TollTest {

    @Test
    public void testLogFile() throws IOException {
        System.out.println("Running testLogFile");
        try (
                BufferedReader reader = new BufferedReader(
                        new FileReader("D:/Workspace/Practice/java/Java Learning/src/test/resources/tollbooth_small.log")
                )
        ) {
            LogFile logFile = new LogFile(reader);
            assertEquals(13, logFile.size());
            for (LogEntry entry : logFile.logEntries) {
                assert (entry instanceof LogEntry);
            }
        }
    }

    @Test
    public void testLogEntry() {
        System.out.println("Running testLogEntry");
        String logLine = "44776.619 KTB918 310E MAINROAD";
        LogEntry logEntry = new LogEntry(logLine);
        assertEquals(44776.619f, logEntry.getTimestamp(), 0.0001);
        assertEquals("KTB918", logEntry.getLicensePlate());
        assertEquals(310, logEntry.getLocation());
        assertEquals("EAST", logEntry.getDirection());
        assertEquals("MAINROAD", logEntry.getBoothType());
        logLine = "52160.132 ABC123 400W ENTRY";
        logEntry = new LogEntry(logLine);
        assertEquals(52160.132f, logEntry.getTimestamp(), 0.0001);
        assertEquals("ABC123", logEntry.getLicensePlate());
        assertEquals(400, logEntry.getLocation());
        assertEquals("WEST", logEntry.getDirection());
        assertEquals("ENTRY", logEntry.getBoothType());
    }

    @Test
    public void testCountJourneys() throws IOException {
        System.out.println("Running testCountJourneys");
        try (BufferedReader reader = new BufferedReader(new FileReader("D:/Workspace/Practice/java/Java Learning/src/test/resources/tollbooth_small.log"))) {
            LogFile logFile = new LogFile(reader);
            assertEquals(3, logFile.countJourneys());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("D:/Workspace/Practice/java/Java Learning/src/test/resources/tollbooth_medium.log"))) {
            LogFile logFile = new LogFile(reader);
            assertEquals(63, logFile.countJourneys());
        }
    }

    @Test
    public void testCatchSpeeders() throws IOException {
        System.out.println("Running testCatchSpeeders");
        try (BufferedReader reader = new BufferedReader(new FileReader("D:/Workspace/Practice/java/Java Learning/src/test/resources/tollbooth_speeders.log"))) {
            LogFile logFile = new LogFile(reader);
            List<String> ticketList = logFile.catchSpeeders();
            // ticketList should be a list similar to
            // ["TST002", "TST003", "TST003"]
            // In this case, TST002 had one journey with unsafe driving, and
            // TST003 had two journeys with unsafe driving. The license plates
            // may be in any order.
            Map<String, Integer> ticketCounts = new HashMap<>();
            for (String ticket : ticketList) {
                ticketCounts.put(ticket, ticketCounts.getOrDefault(ticket, 0) + 1);
            }
            assertEquals(1, (int) ticketCounts.get("TST002"));
            assertEquals(2, (int) ticketCounts.get("TST003"));
            assertEquals(2, ticketCounts.size());
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("D:/Workspace/Practice/java/Java Learning/src/test/resources/tollbooth_medium.log"))) {
            LogFile logFile = new LogFile(reader);
            List<String> ticketList = logFile.catchSpeeders();
            assertEquals(10, ticketList.size());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("D:/Workspace/Practice/java/Java Learning/src/test/resources/tollbooth_long.log"))) {
            LogFile logFile = new LogFile(reader);
            List<String> ticketList = logFile.catchSpeeders();
            assertEquals(129, ticketList.size());
        }
    }
}
