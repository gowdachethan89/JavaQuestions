package com.alti.toll;


import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
                        new FileReader("D:/Workspace/Practice/java/src/test/resources/tollbooth_small.log")
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
        try (BufferedReader reader = new BufferedReader(new FileReader("D:/Workspace/Practice/java/src/test/resources/tollbooth_small.log"))) {
            LogFile logFile = new LogFile(reader);
            assertEquals(3, logFile.countJourneys());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("D:/Workspace/Practice/java/src/test/resources/tollbooth_medium.log"))) {
            LogFile logFile = new LogFile(reader);
            assertEquals(63, logFile.countJourneys());
        }
    }

    @Test
    public void testCatchSpeeders() throws IOException {
        System.out.println("Running testCatchSpeeders");

        // Test Case 1: No speeders (normal speeds)
        // Vehicle travels 10 km in 360 seconds = 100 km/h (safe)
        LogFile logFile1 = createTestLogFile(new String[]{
                "1000.000 ABC123 100E ENTRY",
                "1360.000 ABC123 110E EXIT"
        });
        assertEquals(0, logFile1.catchSpeeders().size(), "No speeders expected for normal speeds");

        // Test Case 2: Single segment violation (≥130 km/h in one segment)
        // 10 km in 275 seconds = 130.91 km/h (VIOLATION)
        LogFile logFile2 = createTestLogFile(new String[]{
                "1000.000 TST001 270W ENTRY",
                "1275.000 TST001 280W EXIT"
        });
        assertEquals(1, logFile2.catchSpeeders().size(), "Should catch speeder exceeding 130 km/h");
        assertEquals("TST001", logFile2.catchSpeeders().get(0), "Correct license plate should be flagged");

        // Test Case 3: Multiple segments at 120+ km/h (violation if 2+ segments)
        // Segment 1: 10 km in 300 sec = 120 km/h
        // Segment 2: 10 km in 300 sec = 120 km/h
        // Segment 3: 10 km in 400 sec = 90 km/h
        LogFile logFile3 = createTestLogFile(new String[]{
                "1000.000 TST002 100E ENTRY",
                "1300.000 TST002 110E MAINROAD",
                "1600.000 TST002 120E MAINROAD",
                "2000.000 TST002 130E EXIT"
        });
        assertEquals(1, logFile3.catchSpeeders().size(),
                "Should flag vehicle with 2+ segments at 120+ km/h");
        assertEquals("TST002", logFile3.catchSpeeders().get(0), "Correct license plate should be flagged");

        // Test Case 4: Multiple journeys - same vehicle speeds in both
        LogFile logFile4 = createTestLogFile(new String[]{
                "1000.000 TST003 100E ENTRY",
                "1275.000 TST003 110E EXIT",      // First journey: 130.91 km/h (VIOLATION)
                "2000.000 TST003 200E ENTRY",
                "2270.000 TST003 210E EXIT"       // Second journey: 130.91 km/h (VIOLATION)
        });
        assertEquals(2, logFile4.catchSpeeders().size(),
                "Same vehicle with 2 speeding journeys should appear twice");
        assertEquals("TST003", logFile4.catchSpeeders().get(0), "First speeding journey");
        assertEquals("TST003", logFile4.catchSpeeders().get(1), "Second speeding journey");

        // Test Case 5: Multiple vehicles - only some are speeders
        LogFile logFile5 = createTestLogFile(new String[]{
                "1000.000 SAFE01 100E ENTRY",
                "1360.000 SAFE01 110E EXIT",      // Normal speed: 100 km/h
                "2000.000 SPEED1 100E ENTRY",
                "2270.000 SPEED1 110E EXIT",      // Speed violation: 130.91 km/h
                "3000.000 SAFE02 100E ENTRY",
                "3400.000 SAFE02 110E EXIT",       // Normal speed: 100 km/h
                "1000.000 TST004 100E ENTRY",
                "1275.000 TST004 110E MAINROAD",
                "1675.000 TST004 120E MAINROAD",
                "2075.000 TST004 130E EXIT",
                "1000.000 TST003 100E ENTRY",
                "1275.000 TST003 110E EXIT",      // First journey: 130.91 km/h (VIOLATION)
                "2000.000 TST003 200E ENTRY",
                "2270.000 TST003 210E EXIT"       // Second journey: 130.91 km/h (VIOLATION)
        });
        List<String> logFile5Response = logFile5.catchSpeeders();
        assertEquals(4, logFile5Response.size(), "Only one speeder expected");
        assertEquals("TST004", logFile5Response.get(0), "Correct speeding vehicle flagged");

        // Test Case 6: Multiple MAINROAD booths in a journey
        // Journey: ENTRY -> MAINROAD -> MAINROAD -> EXIT
        // Segment 1: 10 km in 275 sec = 130.91 km/h (VIOLATION)
        LogFile logFile6 = createTestLogFile(new String[]{
                "1000.000 TST004 100E ENTRY",
                "1275.000 TST004 110E MAINROAD",
                "1475.000 TST004 120E MAINROAD",
                "1875.000 TST004 130E EXIT"
        });
        List<String> logFile6Response = logFile6.catchSpeeders();
        assertEquals(1, logFile6Response.size(),
                "Should flag vehicle with 130+ km/h in any segment");
        assertEquals("TST004", logFile6Response.get(0), "Correct vehicle flagged");

        // Test Case 7: Safe driving with multiple segments
        // All segments between 100-119 km/h (safe)
        LogFile logFile7 = createTestLogFile(new String[]{
                "1000.000 TST005 100E ENTRY",
                "1360.000 TST005 110E MAINROAD",  // 100 km/h
                "1760.000 TST005 120E EXIT"       // 100 km/h
        });
        assertEquals(0, logFile7.catchSpeeders().size(), "No violation for speeds between 100-119");
    }

    /**
     * Helper method to create a test LogFile from an array of log line strings
     */
    private LogFile createTestLogFile(String[] logLines) throws IOException {
        LogFile logFile = new LogFile(null);
        logFile.logEntries = new ArrayList<>();
        for (String line : logLines) {
            logFile.logEntries.add(new LogEntry(line));
        }
        return logFile;
    }
}
