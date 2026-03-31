package com.alti.toll;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TollTest {

    @Test
    public void testLogFile() throws IOException {
        System.out.println("Running testLogFile");
        try (
                BufferedReader reader = new BufferedReader(
                        new FileReader("D:/Workspace/Practice/java/Java Learning/src/test/resources/tollbooth_small.log")
                );
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
}
