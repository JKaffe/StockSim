package com.stockSim;

import com.stockSim.stocks.*;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

public final class StockMarket implements Market{
    private static final Set<Stock> stocks = new HashSet<Stock>();
    private static final StockMarket stockMarket = new StockMarket();
    
    private StockMarket(){}
    
    public static StockMarket getMarket(){      
        return stockMarket;
    }
   
    @Override
    public void add(Stock stock) throws IllegalArgumentException{
        if(stock.getPrice() < 0) throw new IllegalArgumentException("Invalid Price.");
        
        stocks.add(stock);
    }
    
    @Override
    public int size(){
        return stocks.size();
    }
    
    @Override
    public Stock[] getStocks(){
        final Iterator<Stock> stockIter = stocks.iterator();
        
        final Stock[] stocksArr = new Stock[stocks.size()];
        
        int pointer = 0;
        while(stockIter.hasNext()){
            stocksArr[pointer] = stockIter.next();
            pointer += 1;
        }
        
        return stocksArr;
    }
    
    public String toString(){
        final StringBuilder stockPrintOut = new StringBuilder();
        stockPrintOut.append("Avaiable stocks:");
        
        final Iterator stocksIter = stocks.iterator();
        
        while(stocksIter.hasNext()){
            stockPrintOut.append("\n" + stocksIter.next());
        }
        
        return stockPrintOut.toString();
    }
}
