package com.aybuke.StockTrade.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aybuke.StockTrade.Service.UserService;
import com.aybuke.StockTrade.Model.Transaction;
import com.aybuke.StockTrade.Payload.Request.TransactionRequest;
import com.aybuke.StockTrade.Service.StockService;
import com.aybuke.StockTrade.Model.Stock;
import com.aybuke.StockTrade.Service.TransactionService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
	  @Autowired
	    UserService userService;
	  @Autowired
	    StockService stockService;
	  @Autowired
	  TransactionService transactionService;
	  
	  @GetMapping("/stocks")
	    public List<Stock> list() {
	        return stockService.listAllStocks();
	    }
	  
	  @PostMapping("/portfolio")
	    public List<Transaction> list(@RequestBody TransactionRequest request) {
	        if(userService.existsById(request.getUserId())) {
	            return transactionService.getPortfolio(request.getUserId());
	        }

	        return new ArrayList<>();
	    }
	  
	  @PostMapping("/stocks/{id}")
	    public ResponseEntity<Stock> getById(@PathVariable Integer id) {
	        try {
	            Stock stock = stockService.getStock(id);
	            
	            return new ResponseEntity<Stock>(stock, HttpStatus.OK);
	        } catch (NoSuchElementException e) {
	            return new ResponseEntity<Stock>(HttpStatus.NOT_FOUND);
	        }
	    }
	  @PostMapping("/buyStock")
	    public void buyStock(@RequestBody TransactionRequest request){
	        Boolean isFound = false;
	        if(userService.existsById(request.getUserId())) {
	            List<Transaction> transactions = transactionService.getPortfolio(request.getUserId());
	            for(Transaction transaction : transactions) {
	                if (transaction.getStock().getCode().equals(request.getCode())) {
	                	Double tmp = request.getPrice()*request.getStockCount();
	                
	                    transaction.setPrice(transaction.getPrice() + tmp);
	                    transaction.setCount(transaction.getCount() + request.getStockCount());
	                    transaction.setDate(request.getDate());
	                    transactionService.saveTransaction(transaction);
	                    isFound = true;
	                }
	            }
	            if(!isFound){
	                Transaction transaction = new Transaction(
	                        userService.getUser(request.getUserId()),
	                        stockService.getByCode(request.getCode()),
	                        request.getPrice()*request.getStockCount(),
	                        request.getDate(),
	                        request.getStockCount()
	                );
	                transactionService.saveTransaction(transaction);
	            }

	        }
	    }


	    @PostMapping("/sellStock")
	    public void sellStock(@RequestBody TransactionRequest request){
	        Boolean isFound = false;
	        if(userService.existsById(request.getUserId())) {
	            List<Transaction> transactions = transactionService.getPortfolio(request.getUserId());
	            for (Transaction transaction : transactions) {
	            	  
	                if (transaction.getStock().getCode().equals(request.getCode())) {
	                	Double tmp = request.getPrice()*request.getStockCount();
	                    transaction.setPrice(transaction.getPrice()-tmp);
	                    
	                    transaction.setCount(transaction.getCount() - request.getStockCount());
	                    transaction.setDate(request.getDate());
	                    transactionService.saveTransaction(transaction);
	                    if (transactionService.getTransaction(transaction.getId()).getPrice() < 0 || transactionService.getTransaction(transaction.getId()).getCount() <= 0) {
	                        transactionService.deleteTransaction(transaction.getId());
	                    }
	                }
	            }
	        }}
}


