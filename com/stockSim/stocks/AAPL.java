package com.stockSim.stocks;

import com.stockSim.StockMarket;

public final class AAPL extends Stock{
    private static final AAPL aaplStock = new AAPL();
    
    private AAPL(){}
    
    protected static AAPL get(){
        return aaplStock;
    }
    
    @Override
    public void subscribe(){
        StockMarket.getMarket().add(this);
    }
    
    @Override
    protected double getInitValue(){return 554.92;}
}