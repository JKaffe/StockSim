package com.stockSim.stocks;

import com.stockSim.StockMarket;

public final class MSFT extends Stock{
    private static final MSFT msftStock = new MSFT();
    
    private MSFT(){}
    
    protected static MSFT get(){
        return msftStock;
    }
    
    @Override
    public void subscribe(){
        StockMarket.getMarket().add(this);
    }
    
    @Override
    protected double getInitValue(){return 304.94;}
}
