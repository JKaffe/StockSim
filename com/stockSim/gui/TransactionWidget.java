package com.stockSim.gui;

import com.stockSim.stocks.Stock;
import com.stockSim.transaction.*;
import com.stockSim.InvestorPortafolio;
import java.util.InputMismatchException;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.BiFunction;
import java.awt.Color;
import java.awt.Panel;
import java.awt.Label;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import javax.swing.event.CaretListener;
import com.stockSim.StockInvestorSim;
import java.util.concurrent.ExecutionException;

public final class TransactionWidget extends Panel{
    private final Stock stock;
    private final InvestorPortafolio portafolio;
    private final Label title;
    private final Label ownedAmountLabel;
    private final Label totCost;
    private final JTextField amountField = new JTextField("", 30);
    
    public TransactionWidget(Stock stock, InvestorPortafolio portafolio){
        this.stock = stock;
        this.portafolio = portafolio;
        
        setLayout(new BorderLayout());
        
        final Panel topLabels = new Panel();
        topLabels.setLayout(new BorderLayout());
        
        this.title = new Label(stock.toString(), Label.LEFT);
        topLabels.add(title, BorderLayout.WEST);
        StockInvestorSim.hookToUpdate(this::updateStockLabel);
        
        this.ownedAmountLabel = new Label("Owned: 0", Label.CENTER);
        topLabels.add(ownedAmountLabel, BorderLayout.CENTER);
        TransactionHandler.executePostCompletion(future -> {
            try{
                future.get(); //Waits for the transaction to complete
                updateAmount();
            } catch(ExecutionException | InterruptedException e){
                // No Action
            } 
        });
        
        this.totCost = new Label("0 £", Label.RIGHT);
        topLabels.add(totCost, BorderLayout.EAST);
        StockInvestorSim.hookToUpdate(this::updateCostLabel);
        
        add(topLabels, BorderLayout.NORTH);
        
        amountField.addCaretListener(event -> updateCostLabel());

        add(amountField, BorderLayout.CENTER);
        setVisible(true);
        
        final Panel buttons = new Panel();
        buttons.setLayout(new GridLayout(1, 2, 1, 0));
              
        final JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(buyAction.apply(portafolio, stock));
        buyButton.setBorder(null);
        buyButton.setBackground(new Color(242, 65, 65));
        buyButton.setForeground(Color.WHITE);
        buyButton.setVisible(true);
        buttons.add(buyButton);
        
        final JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(sellAction.apply(portafolio, stock));
        sellButton.setBorder(null);
        sellButton.setBackground(new Color(76, 84, 239));
        sellButton.setForeground(Color.WHITE);
        sellButton.setVisible(true);
        buttons.add(sellButton);
        
        add(buttons, BorderLayout.SOUTH);
        
        setVisible(true);
    }
   
    private final BiFunction<InvestorPortafolio, Stock, ActionListener> buyAction = 
    (portafolio, stock) -> event -> 
    {  
        try{
            new Buy(stock, getEnteredAmount(), portafolio).submitForExecution();
        } catch(IllegalArgumentException | InputMismatchException e){
            // If a transaction cannot be made
            // If entered amount is invalid
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    };
    
    private final BiFunction<InvestorPortafolio, Stock, ActionListener> sellAction =
    (portafolio, stock) -> event ->
    {
        try{
            new Sell(stock, getEnteredAmount(), portafolio).submitForExecution();
        }catch(IllegalArgumentException | InputMismatchException e){
            // If a transacton cannot be made
            // If entered amount is invalid
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    };
    
    protected void updateStockLabel(){
        title.setText(this.stock.toString());
    }
    
    private void updateCostLabel(){
        try{
            totCost.setText(String.valueOf(Math.round(getEnteredAmount() * stock.getPrice()*1000.0)/1000.0) + " £");
        } catch(InputMismatchException e){
            totCost.setText("0 £");
        }
    }
    
    protected void updateAmount(){
        ownedAmountLabel.setText("Owned: " + String.valueOf(portafolio.getAmount(this.stock)));
    }
    
    private int getEnteredAmount() throws InputMismatchException{
        final String input = amountField.getText();
        try{
            return Integer.parseInt(input);
        } catch(NumberFormatException e){
            throw new InputMismatchException("The amount entered is invalid. Only whole numbers can be entered.");
        }
    }
}
