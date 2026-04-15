package com.practice.library;

class Book {
    int id;
    String title;
    BookStatus status;

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
        this.status = BookStatus.AVAILABLE;
    }
}
