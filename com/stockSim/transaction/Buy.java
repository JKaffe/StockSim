package com.stockSim.transaction;

import com.stockSim.stocks.Stock;
import com.stockSim.InvestorPortafolio;

public final class Buy extends Transaction{
    public Buy(Stock stock, int amount, InvestorPortafolio portafolio){
        super(stock, amount, portafolio);
    }

    @Override
    public Transaction call(){
        this.portafolio.buy(stock, amount);
        return this;
    }
}