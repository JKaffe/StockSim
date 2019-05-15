package com.stockSim;

import com.stockSim.transaction.TransactionHandler;
import com.stockSim.transaction.Transaction;
import static java.nio.file.Files.write;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.io.IOException;

public class TransactionLogger{

    private final Path file;
       
    public TransactionLogger(String fileName){
        this.file = Paths.get(fileName);
        TransactionHandler.executePostCompletion(
            future -> {
                try{
                    final Transaction completedTransaction = future.get();
                    write(file, (completedTransaction.toString() + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch(ExecutionException | InterruptedException e0){
                    //No action
                } catch(IOException e1){
                    System.out.println("ERROR: " + e1.getMessage());
                }
            }
        );
    }
    
}
