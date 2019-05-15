
/**
 * Abstract class Stock - Represents a single stock in a stock market
 *
 * @author Karmjit Mahil
 * @version 17/01/2019
 */
package com.stockSim.stocks;

import com.stockSim.StockMarket;

public abstract class Stock implements MarketElement{
    private double price;
    
    public Stock(){
        this.price = getInitValue();
    }
    
    protected abstract double getInitValue();
    
    protected void update(){
        // With 0.5, eventually the stock reaches 0.001 and stop there
        this.price = (Math.random() + 0.6) * price;
        this.price = Math.round(price*1000.0)/1000.0;
    }
    
    public final double getPrice(){
        return this.price;
    }
    
    public final String getName(){
        return this.getClass().getSimpleName();
    }
    
    public final String toString(){
        return this.getName() + ": " + this.price + " £";  
    }
}
