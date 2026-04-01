package com.alti.stocks;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockCollectionTest {
  
  @Test
  public void testPriceRecord() {
    // Test basic PriceRecord functionality
    System.out.println("Running testPriceRecord");
    Stock testStock = new Stock("AAPL", "Apple Inc.");
    PriceRecord testPriceRecord = new PriceRecord(testStock, 100, "2023-07-01");

    assertEquals(testPriceRecord.stock, testStock);
    assertEquals(100, testPriceRecord.price);
    assertEquals("2023-07-01", testPriceRecord.date);
  }

  private static StockCollection makeStockCollection(Stock stock, Object[][] priceData) {
    StockCollection stockCollection = new StockCollection(stock);
    for (Object[] priceRecordData : priceData) {
      PriceRecord priceRecord = new PriceRecord(stock, (int) priceRecordData[0], (String) priceRecordData[1]);
      stockCollection.addPriceRecord(priceRecord);
    }
    return stockCollection;
  }

  @Test
  public void testStockCollection() {
    System.out.println("Running testStockCollection");
    // Test basic StockCollection functionality
    Stock testStock = new Stock("AAPL", "Apple Inc.");
    StockCollection stockCollection = new StockCollection(testStock);

    assertEquals(0, stockCollection.getNumPriceRecords());
    assertEquals(-1, stockCollection.getMaxPrice());
    assertEquals(-1, stockCollection.getMinPrice());
    assertEquals(-1.0, stockCollection.getAvgPrice(), 0.001);

    /*
     * Price Records: Price: 110 112 90 105 Date: 2023-06-29 2023-07-01 2023-06-28
     * 2023-07-06
     */
    Object[][] priceData = { { 110, "2023-06-29" }, { 112, "2023-07-01" }, { 90, "2023-06-28" },
        { 105, "2023-07-06" } };
    testStock = new Stock("AAPL", "Apple Inc.");
    stockCollection = makeStockCollection(testStock, priceData);

    assertEquals(priceData.length, stockCollection.getNumPriceRecords());
    assertEquals(112, stockCollection.getMaxPrice());
    assertEquals(90, stockCollection.getMinPrice());
    assertEquals(104.25, stockCollection.getAvgPrice(), 0.1);
  }
  
  @Test
  public void testGetBiggestChange() {
    // Test the getBiggestChange method
    System.out.println("Running testGetBiggestChange");
    Stock testStock = new Stock("AAPL", "Apple Inc.");
    StockCollection stockCollection = new StockCollection(testStock);

    assertNull(stockCollection.getBiggestChange());

    /*
     * Price Records: Price: 110 112 90 105 Date: 2023-06-29 2023-07-01 2023-06-25
     * 2023-07-06
     */
    Object[][] priceData = { { 110, "2023-06-29" }, { 112, "2023-07-01" }, { 90, "2023-06-25" },
        { 105, "2023-07-06" } };
    stockCollection = makeStockCollection(testStock, priceData);

    assertArrayEquals(new Object[] { 20, "2023-06-25", "2023-06-29" }, stockCollection.getBiggestChange());

    /*
     * Price Records: Price: 200 210 190 180 Date: 2000-01-04 1999-12-30 2000-01-03
     * 2000-01-01
     */
    Object[][] priceData2 = { { 200, "2000-01-04" }, { 210, "1999-12-30" }, { 190, "2000-01-03" },
        { 180, "2000-01-01" } };
    stockCollection = makeStockCollection(testStock, priceData2);

    assertArrayEquals(new Object[] { -30, "1999-12-30", "2000-01-01" }, stockCollection.getBiggestChange());
  }
}

