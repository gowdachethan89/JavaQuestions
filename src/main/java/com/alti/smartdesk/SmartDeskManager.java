package com.alti.smartdesk;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SmartDeskManager {
    private final int MAX_HOT_DESKS = 10;
    private List<Booking> allBookings = new ArrayList<>();
    private Map<Integer, Tier> memberTiers = new HashMap<>();

    public void addMember(int id, Tier tier) {
        memberTiers.put(id, tier);
    }

    /**
     * TODO: Implement this method.
     * 1. Calculate the number of days.
     * 2. Check for Dedicated Desk overlaps (no two people can have one on the same day).
     * 3. Check for Hot Desk capacity (max 10 people per day).
     * 4. Apply Tier-based pricing logic.
     */
    public BookingResult bookDesk(int memberId, DeskType type, LocalDate start, LocalDate end) {
        // Your logic here
        return null;
    }
}
