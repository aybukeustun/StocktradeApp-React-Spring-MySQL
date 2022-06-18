package com.aybuke.StockTrade.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aybuke.StockTrade.Model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

}
