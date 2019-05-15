package com.stockSim.transaction;

import com.stockSim.stocks.Stock;
import com.stockSim.InvestorPortafolio;

public final class Sell extends Transaction{

    public Sell(Stock stock, int amount, InvestorPortafolio portafolio){
        super(stock, amount, portafolio);
    }
    
    @Override
    public Transaction call(){
        portafolio.sell(stock, amount);
        return this;
    }
}
