/**
 * Contains main method.
 *
 * @author Karmjit Mahil
 * @version 17/01/2019
 */
package com.stockSim;

import com.stockSim.stocks.StockPool;
import com.stockSim.stocks.Stock;
import com.stockSim.gui.StockSim;
import com.stockSim.transaction.Transaction;
import javax.swing.JOptionPane;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.List;
import java.util.LinkedList;

public class StockInvestorSim{
    private static final ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(3);
    private static final List<Runnable> updateHooks = new LinkedList<>();
    private static final Runnable updateApp = () -> updateHooks.stream().forEach(Runnable::run);
    private static InvestorPortafolio portafolio;
    private static final String logFile = "log.txt";
    
    public static void hookToUpdate(Runnable updateFun){
        updateHooks.add(updateFun);
    }

    public static void main(String[] args){
        final StockMarket market = StockMarket.getMarket();
        final StockPool stockPool = StockPool.getStockPool();
        final TransactionLogger logger = new TransactionLogger(logFile);
        
        schedulerService.scheduleWithFixedDelay(updateApp, 0, 1000, TimeUnit.MILLISECONDS);
        
        portafolio = new InvestorPortafolio(getInitCredit());
        
        new StockSim(market, portafolio);
    }
    
    public static InvestorPortafolio getPortafolio(){
        return portafolio;
    }
    
    private static int getInitCredit(){
        final String inp = JOptionPane.showInputDialog("Initial credit:");
        try{
            if(inp == null){
                System.exit(0);
            }
            
            final int credit = Integer.parseInt(inp);
            
            if(credit <= 0){
                throw new IllegalArgumentException("INPUT ERROR");
            }
            
            return credit;
        } catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "INVALID INPUT", "ERROR", JOptionPane.ERROR_MESSAGE);
            return getInitCredit();
        }
    }
}
