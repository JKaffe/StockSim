package com.stockSim;

import com.stockSim.stocks.Stock;
import com.stockSim.stocks.StockPool;

public interface Market{
    public void add(Stock stock);
    
    public int size();
    
    public Stock[] getStocks();
}
