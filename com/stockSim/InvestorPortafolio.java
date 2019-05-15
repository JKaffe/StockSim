package com.stockSim;

import java.util.concurrent.RejectedExecutionException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.stockSim.stocks.Stock;

public final class InvestorPortafolio{
    private float credit;
    private Map<Stock, Integer> stocks = new HashMap<Stock, Integer>();
    
    public InvestorPortafolio(float credit){
        this.credit = credit;
    }
    
    public boolean contains(Stock stock){
        return stocks.containsKey(stock);
    }
    
    public void buy(Stock stock, int amountToAdd) throws IllegalArgumentException, RejectedExecutionException{
        if(amountToAdd < 0){
            throw new IllegalArgumentException("Cannot buy " + amountToAdd + " stocks.");
        } else if(amountToAdd == 0){
            return;
        }
        
        final double totCost = stock.getPrice() * amountToAdd;
        
        if(totCost > credit){
            throw new RejectedExecutionException("Not enough credit.");
        }
        
        credit -= totCost;
        // Overwrites element if already present in the map.
        stocks.put(stock, getAmount(stock) + amountToAdd);
    }
    
    public void sell(Stock stock, int amountToSub) throws IllegalArgumentException, RejectedExecutionException{
        if(amountToSub < 0){
            throw new IllegalArgumentException("Cannot sell " + amountToSub + " stocks.");
        } else if(amountToSub == 0){
            return;
        }
        
        if(amountToSub > getAmount(stock)){
            throw new RejectedExecutionException("Not enough stocks.");
        }
              
        credit += stock.getPrice() * amountToSub;
        //Overrides the stock inside stocks
        stocks.put(stock, getAmount(stock) - amountToSub);
    }
       
    public float getCredit(){
        return this.credit;
    }
     
    public int getAmount(Stock stock){
        if(this.contains(stock)){
            return stocks.get(stock);
        }
        
        return 0;
    }
    
    public Map<Stock,Integer> getValues(){
        return this.stocks;
    }
    
    @Override
    public String toString(){
        if(stocks.isEmpty()){
            return "Portafolio is empty." + "\nCredit: " + credit + " £";
        } else {
            return "Portafolio:\n" + stocks.toString()
                    .replaceAll("[{]|[}]","")
                    .replaceAll(", ","\n") + "\nCredit: " + credit + " £";
        }
    }
}