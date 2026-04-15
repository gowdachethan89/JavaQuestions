package com.practice.smartdesk;

import java.time.LocalDate;

class Booking {
    int memberId;
    DeskType type;
    LocalDate startDate;
    LocalDate endDate;
    double finalPrice;

    public Booking(int memberId, DeskType type, LocalDate startDate, LocalDate endDate, double finalPrice) {
        this.memberId = memberId;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finalPrice = finalPrice;
    }

    public int getMemberId() {
        return memberId;
    }

    public DeskType getType() {
        return type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}