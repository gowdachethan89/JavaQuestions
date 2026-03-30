package com.javalearning;

class PriceRecord {
  /** Data and methods about a single price record of a stock. */
  Stock stock; // Stock object representing the stock
  int price; // int, the price of the stock
  String date; // String, the date of the price record is of the format "YYYY-MM-DD"

  PriceRecord(Stock stock, int price, String date) {
    this.stock = stock;
    this.price = price;
    this.date = date;
  }
}
