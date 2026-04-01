package com.alti.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryManagerTest {
    private LibraryManager library;

    @BeforeEach
    void setUp() {
        library = new LibraryManager();
        library.addBook(new Book(101, "Java Concurrency"));
        library.addBook(new Book(102, "Clean Code"));
        library.addBook(new Book(103, "Effective Java"));
        library.addBook(new Book(104, "Design Patterns"));
    }

    @Test
    void testSuccessfulCheckout() {
        String result = library.checkoutBook(1, 101);
        assertEquals("SUCCESS", result);
    }

    @Test
    void testMaximumLimit() {
        library.checkoutBook(1, 101);
        library.checkoutBook(1, 102);
        library.checkoutBook(1, 103);

        // This 4th book should fail because MAX_LOANS is 3
        String result = library.checkoutBook(1, 104);
        assertEquals("LIMIT_REACHED", result);
    }

    @Test
    void testCannotBorrowSameBookTwice() {
        library.checkoutBook(1, 101);

        // Another member tries to borrow the same book
        String result = library.checkoutBook(2, 101);
        assertEquals("ALREADY_BORROWED", result);
    }
}
