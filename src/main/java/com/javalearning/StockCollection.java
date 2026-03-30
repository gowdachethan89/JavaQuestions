package com.javalearning;

import java.util.*;

/**
 * Data for a collection of price records for a particular stock, and methods for
 * getting useful statistics about the stock's prices.
 */
class StockCollection {
  ArrayList<PriceRecord> priceRecords = new ArrayList<>(); // list of PriceRecord objects, the price records for this particular stock
  Stock stock; // Stock, the Stock this StockCollection is for

  StockCollection(Stock stock) {
    this.stock = stock;
  }

  int getNumPriceRecords() {
    /** Returns the number of PriceRecords in this StockCollection */
    return priceRecords.size();
  }

  void addPriceRecord(PriceRecord priceRecord) {
    /** Adds a PriceRecord to this StockCollection. */
    if (!priceRecord.stock.equals(this.stock)) {
      throw new IllegalArgumentException("PriceRecord's Stock is not the same as the StockCollection's");
    }
    priceRecords.add(priceRecord);
  }

  int getMaxPrice() {
    /** Return the maximum price recorded in this StockCollection. */
    return priceRecords.stream().mapToInt(record -> record.price).max().orElse(-1);
  }

  int getMinPrice() {
    /** Return the minimum price recorded in this StockCollection. */
    return priceRecords.stream().mapToInt(record -> record.price).min().orElse(-1);
  }

  double getAvgPrice() {
    /** Return the average price recorded in this StockCollection. */
    if(priceRecords.isEmpty())
    {
      return -1.0;
    }
    else{
    double total = priceRecords.stream().mapToInt(record -> record.price).sum();
    return total / priceRecords.size();
    }
  }
  
  Object[] getBiggestChange(){
    if (priceRecords.size() < 2) return null;
    List<PriceRecord> sorted = new ArrayList<>(priceRecords);
    sorted.sort(Comparator.comparing(p -> p.date));
    String startDate = "";
    String endDate = "";
    int maxChange = 0;
    int maxAbs = 0;
    
    for(int i = 1; i < sorted.size(); i++) {
        PriceRecord prev = sorted.get(i - 1);
        PriceRecord curr = sorted.get(i);
        
        int change = curr.price - prev.price;
        int absChange = Math.abs(change);
        if (absChange > maxAbs) {
            maxAbs = absChange;
            maxChange = change;
            startDate = prev.date;
            endDate = curr.date;
        }
    }
    return new Object[] {maxChange, startDate, endDate};
  }
}
