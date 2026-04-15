package com.practice.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LibraryManager {
    private Map<Integer, Book> inventory = new HashMap<>();
    private Map<Integer, List<Integer>> memberLoans = new HashMap<>();
    private final int MAX_LOANS = 3;

    public void addBook(Book book) {
        inventory.put(book.id, book);
    }

    public String checkoutBook(int memberId, int bookId) {
        Book book = inventory.get(bookId);

        if (book == null) return "BOOK_NOT_FOUND";

        // BUG 1: Look closely at how we check the member's current loan count
        List<Integer> loans = memberLoans.getOrDefault(memberId, new ArrayList<>());
        if (loans.size() >= MAX_LOANS) {
            return "LIMIT_REACHED";
        }

        // BUG 2: Check the state transition logic here
        if (book.status == BookStatus.BORROWED) {
            return "ALREADY_BORROWED";
        }

        // Perform the checkout
        book.status = BookStatus.BORROWED;
        loans.add(bookId);
        memberLoans.put(memberId, loans);

        return "SUCCESS";
    }
}