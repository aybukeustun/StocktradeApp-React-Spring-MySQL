package com.aybuke.StockTrade.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aybuke.StockTrade.Repository.TransactionRepository;
import com.aybuke.StockTrade.Model.Transaction;

@Service
@Transactional
public class TransactionService {
	 @Autowired
	    private TransactionRepository transactionRepository;
	 
	 public void saveTransaction(Transaction transaction){
	        transactionRepository.save(transaction);

	    }
	 public Boolean deleteTransaction(Integer transactionId){
	        if(transactionRepository.existsById(transactionId)) {
	            Transaction transaction = transactionRepository.getById(transactionId);
	            transactionRepository.deleteById(transactionId);
	            return true;
	        }
	        return false;
	        

	    }
	 public List<Transaction> getPortfolio(Integer userId){
	        List<Transaction> portfolio = new ArrayList<Transaction>();
	        for(Transaction transaction : transactionRepository.findAll()){
	            if(transaction.getUser().getId() == userId){
	                portfolio.add(transaction);
	            }}
	        return portfolio;
	    }
	 public Transaction getTransaction(Integer id){
	        return transactionRepository.findById(id).get();
	    }


}
