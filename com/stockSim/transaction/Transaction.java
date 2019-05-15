package com.stockSim.transaction;

import com.stockSim.stocks.Stock;
import com.stockSim.InvestorPortafolio;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import java.time.LocalDateTime;

public abstract class Transaction implements Callable<Transaction>{
    protected final Stock stock;
    protected final int amount;
    protected final InvestorPortafolio portafolio;
        
    private LocalDateTime initiationTime;
    
    public Transaction(Stock stock, int amount, InvestorPortafolio portafolio) throws IllegalArgumentException{
        if(amount <= 0){
            throw new IllegalArgumentException("Invalid amount.");
        }
                
        this.stock = stock;
        this.amount = amount;
        this.portafolio = portafolio;
    }
    
    public Future<Transaction> submitForExecution(){
        return TransactionHandler.handleTransaction(this);
    }
    
    protected void initiatedAt(LocalDateTime time) throws RejectedExecutionException{
        if(initiationTime != null){
            throw new RejectedExecutionException("Transaction expired. "  + this.toString() + " Previously executed:" + initiationTime.toString());
        }
        
        this.initiationTime = time;
    }
    
    public Stock getStock(){
        return this.stock;
    }
    
    public InvestorPortafolio getPortafolio(){
        return this.portafolio;
    }
    
    public String toString(){
       return stock.toString() + ". Initiated at " + initiationTime.toString() + ". " + this.getClass().getSimpleName() + ": " + amount;
    }
    
    
}
