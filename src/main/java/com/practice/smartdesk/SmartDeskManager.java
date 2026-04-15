package com.practice.smartdesk;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

class SmartDeskManager {
    private final int MAX_HOT_DESKS = 10;
    private List<Booking> allBookings = new ArrayList<>();
    private Map<Integer, Tier> memberTiers = new HashMap<>();
    private Map<LocalDate, Integer> hotDeskCount = new HashMap<>();
    private Set<LocalDate> dedicatedBookedDays = new HashSet<>();

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
        // Calculate the number of days
        long numDays = ChronoUnit.DAYS.between(start, end) + 1;

        // Check if member exists
        if (!memberTiers.containsKey(memberId)) {
            return new BookingResult(false, 0, "Member not found");
        }

        // Check for Dedicated Desk overlaps
        if (type == DeskType.DEDICATED_DESK) {
            boolean overlap = start.datesUntil(end.plusDays(1)).anyMatch(date -> dedicatedBookedDays.contains(date));
            if (overlap) {
                return new BookingResult(false, 0, "OVERLAP");
            }
        }

        // Check for Hot Desk capacity
        if (type == DeskType.HOT_DESK) {
            boolean capacityReached = start.datesUntil(end.plusDays(1)).anyMatch(date -> hotDeskCount.getOrDefault(date, 0) >= MAX_HOT_DESKS);
            if (capacityReached) {
                return new BookingResult(false, 0, "CAPACITY_REACHED");
            }
        }

        // Apply Tier-based pricing logic
        double basePricePerDay = (type == DeskType.HOT_DESK) ? 20.0 : 50.0;
        Tier tier = memberTiers.get(memberId);
        double price = 0.0;
        if (tier == Tier.BASIC) {
            price = numDays * basePricePerDay;
        } else if (tier == Tier.PREMIUM) {
            price = numDays * basePricePerDay * 0.8; // 20% discount
        } else if (tier == Tier.CORPORATE) {
            long freeDays = Math.min(5, numDays);
            long paidDays = numDays - freeDays;
            price = paidDays * basePricePerDay * 0.7; // 30% discount on paid days
        }

        // Create and add booking
        Booking booking = new Booking(memberId, type, start, end, price);
        allBookings.add(booking);

        // Update tracking structures
        start.datesUntil(end.plusDays(1)).forEach(date -> {
            if (type == DeskType.DEDICATED_DESK) {
                dedicatedBookedDays.add(date);
            } else if (type == DeskType.HOT_DESK) {
                hotDeskCount.put(date, hotDeskCount.getOrDefault(date, 0) + 1);
            }
        });

        return new BookingResult(true, price, "Booking successful");
    }
}
