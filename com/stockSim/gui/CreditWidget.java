package com.stockSim.gui;

import com.stockSim.InvestorPortafolio;
import com.stockSim.transaction.Transaction;
import java.awt.Label;
import java.awt.Panel;
import java.util.function.Consumer;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class CreditWidget extends Panel{

    private final InvestorPortafolio portafolio;
    private final Label credit = new Label("Credit: " + Label.CENTER);
    
    protected final Consumer<Future<Transaction>> update = transaction -> 
    {
        try{
            transaction.get(); //Waits for the transaction to be completed.
            this.update();
        } catch(ExecutionException | InterruptedException e){
            // No Change if the transaction fails
        }
    };
    
    public CreditWidget(InvestorPortafolio portafolio){
        this.portafolio = portafolio;
        update();
        add(credit);
    }
    
    protected void update(){
        this.credit.setText("Credit: " + String.valueOf(portafolio.getCredit()) + " £");
    }
    
}
