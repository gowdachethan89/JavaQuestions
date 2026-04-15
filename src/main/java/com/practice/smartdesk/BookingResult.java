package com.practice.smartdesk;

class BookingResult {
    public boolean success;
    public double price;
    public String message;

    public BookingResult(boolean success, double price, String message) {
        this.success = success;
        this.price = price;
        this.message = message;
    }
}
