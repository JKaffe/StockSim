package com.stockSim.stocks;

import com.stockSim.StockMarket;

public final class FB extends Stock{
    private static final FB fbStock = new FB();
    
    private FB(){}
    
    protected static FB get(){
        return fbStock;
    }
   
    @Override
    public void subscribe(){
        StockMarket.getMarket().add(this);
    }
    
    @Override
    protected double getInitValue(){return 468.07;}
}
