package com.stockSim.transaction;

import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.time.LocalDateTime;

public class TransactionHandler{
    private static final ThreadPoolExecutor transactionHandler = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    // Executed after the first execution of a transaction
    private static final List<Consumer<Future<Transaction>>> postExecutionHooks = new LinkedList<>();
    
    public static Future<Transaction> handleTransaction(Transaction transaction){
        transaction.initiatedAt(LocalDateTime.now());
        final Future<Transaction> result = transactionHandler.submit(transaction);
        executePostExecutionHooks(result);
        return result;
    }
    
    //private static Runnable run =() -> postExecutionHooks.forEach(callable -> callable.accept(transaction));
    
    private static void executePostExecutionHooks(Future<Transaction> future){
        postExecutionHooks.forEach(consumer -> consumer.accept(future));
    }
    
    public static void executePostCompletion(Consumer<Future<Transaction>> task){
        postExecutionHooks.add(task);
    }
}
