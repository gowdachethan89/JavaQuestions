package com.practice.smartdesk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SmartDeskManagerTest {

    private SmartDeskManager manager;

    @BeforeEach
    void setUp() {
        manager = new SmartDeskManager();
        manager.addMember(1, Tier.BASIC);
        manager.addMember(2, Tier.PREMIUM);
        manager.addMember(3, Tier.CORPORATE);
    }

    @Test
    @DisplayName("Basic Tier: Should charge full price for Hot Desk")
    void testBasicHotDesk() {
        LocalDate start = LocalDate.of(2026, 4, 1);
        LocalDate end = LocalDate.of(2026, 4, 3); // 3 days: 1st, 2nd, 3rd

        BookingResult result = manager.bookDesk(1, DeskType.HOT_DESK, start, end);

        assertTrue(result.success);
        assertEquals(60.0, result.price, 0.01, "3 days * $20 = $60");
    }

    @Test
    @DisplayName("Premium Tier: Should apply 20% discount")
    void testPremiumDiscount() {
        LocalDate start = LocalDate.of(2026, 4, 1);
        LocalDate end = LocalDate.of(2026, 4, 1); // 1 day

        BookingResult result = manager.bookDesk(2, DeskType.DEDICATED_DESK, start, end);

        assertTrue(result.success);
        assertEquals(40.0, result.price, 0.01, "$50 - 20% = $40");
    }

    @Test
    @DisplayName("Corporate Tier: First 5 days free, then 30% discount")
    void testCorporatePricing() {
        LocalDate start = LocalDate.of(2026, 4, 1);
        LocalDate end = LocalDate.of(2026, 4, 7); // 7 days total

        BookingResult result = manager.bookDesk(3, DeskType.HOT_DESK, start, end);

        assertTrue(result.success);
        // Calculation: 5 days free + 2 days paid.
        // Paid days: (2 * $20) = $40. Apply 30% discount on those: $40 * 0.7 = $28.
        assertEquals(28.0, result.price, 0.01);
    }

    @Test
    @DisplayName("Dedicated Desk: Should fail if dates overlap for any member")
    void testDedicatedOverlap() {
        LocalDate start1 = LocalDate.of(2026, 5, 1);
        LocalDate end1 = LocalDate.of(2026, 5, 10);

        manager.bookDesk(1, DeskType.DEDICATED_DESK, start1, end1);

        // Member 2 tries to book during the same window
        LocalDate start2 = LocalDate.of(2026, 5, 5);
        LocalDate end2 = LocalDate.of(2026, 5, 15);

        BookingResult result = manager.bookDesk(2, DeskType.DEDICATED_DESK, start2, end2);

        assertFalse(result.success, "Should fail because May 5-10 is already taken");
        assertEquals("OVERLAP", result.message);
    }

    @Test
    @DisplayName("Hot Desk: Should fail if capacity (10) is reached on any single day in range")
    void testHotDeskCapacity() {
        LocalDate targetDate = LocalDate.of(2026, 6, 1);

        // Fill 10 slots for June 1st
        for (int i = 10; i < 20; i++) {
            manager.addMember(i, Tier.BASIC);
            manager.bookDesk(i, DeskType.HOT_DESK, targetDate, targetDate);
        }

        // Member 1 tries to book a range that INCLUDES June 1st
        LocalDate start = LocalDate.of(2026, 5, 30);
        LocalDate end = LocalDate.of(2026, 6, 2);

        BookingResult result = manager.bookDesk(1, DeskType.HOT_DESK, start, end);

        assertFalse(result.success, "Should fail because June 1st is at max capacity");
        assertEquals("CAPACITY_REACHED", result.message);
    }
}
