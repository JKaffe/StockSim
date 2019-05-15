package com.stockSim.stocks;

import com.stockSim.StockInvestorSim;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public final class StockPool{
    private Stock[] stocks;
    private static StockPool stockPool;
    
    public static Runnable update = () -> stockPool.update();
   
    
    private StockPool(){
        this.stocks = new Stock[]{ AAPL.get(), MSFT.get(), FB.get() };
    }
    
    public static StockPool getStockPool(){
        if(stockPool == null){
            stockPool = new StockPool();
            stockPool.updateSubscription();
        }
        
        StockInvestorSim.hookToUpdate(update);
        
        return stockPool;
    }
    
    public void updateSubscription(){
        for(Stock stock : stocks){
            stock.subscribe();
        }
    }
    
    private void update(){
        for(Stock stock : stocks){
            stock.update();
        }
    }
}
