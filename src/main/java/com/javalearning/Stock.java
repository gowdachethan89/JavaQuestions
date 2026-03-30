package com.javalearning;

class Stock {
  /** Data about a particular stock. */
  String symbol; // String, the symbol of the stock
  String name; // String, the name of the stock

  Stock(String symbol, String name) {
    this.symbol = symbol;
    this.name = name;
  }

  @Override
  public boolean equals(Object other) {
      if (this == other) return true;
      if (other == null || getClass() != other.getClass()) return false;
      Stock stock = (Stock) other;
      return symbol.equals(stock.symbol) && name.equals(stock.name);
  }
  @Override
  public int hashCode() {
    return java.util.Objects.hash(symbol, name);
  }
}
