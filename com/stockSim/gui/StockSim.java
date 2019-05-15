package com.stockSim.gui;


import com.stockSim.Market;

import com.stockSim.InvestorPortafolio;
import com.stockSim.stocks.Stock;
import com.stockSim.transaction.Transaction;
import com.stockSim.transaction.TransactionHandler;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class StockSim{
    
    private final TransactionWidget[] transactionWidgets;
    
    public StockSim(Market market, InvestorPortafolio portafolio){ 
        final Frame frame = new Frame("Stock Investment Simulator.");
        frame.setSize(480, 680);
        frame.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){System.exit(0);}});
        frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
        frame.setBackground(new Color(250,250,250));
        
        final Panel emptyTopSpacer = new Panel();
       
        final Panel emptyBottomSpacer = new Panel();
        
        final Panel mainPanel = new Panel();
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        final Panel widgetContainer = new Panel();
        widgetContainer.setLayout(new BoxLayout(widgetContainer, BoxLayout.Y_AXIS));
        
        final CreditWidget creditWidget = new CreditWidget(portafolio);
        widgetContainer.add(creditWidget);
        TransactionHandler.executePostCompletion(creditWidget.update);
        
        this.transactionWidgets = new TransactionWidget[market.size()];
        final Stock[] stocks = market.getStocks();

        for(int k = 0; k < stocks.length; k++){
            final TransactionWidget stock = new TransactionWidget(stocks[k], portafolio);           
            this.transactionWidgets[k] = stock;
            widgetContainer.add(stock);
        }
        
        TransactionHandler.executePostCompletion(handleTransactionErrors);
        
        mainPanel.add(widgetContainer);
        
        frame.add(emptyTopSpacer);
        frame.add(mainPanel);
        frame.add(emptyBottomSpacer);
        frame.setVisible(true);
    }
    
    private final Consumer<Future<Transaction>> handleTransactionErrors =
    future -> 
    {
        try{
            future.get();   //Waits fot the transaction to complete
        } catch(ExecutionException | InterruptedException e0){
            // If the transaction is not successful
            JOptionPane.showMessageDialog(null, e0.getCause().getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    };
    
}
