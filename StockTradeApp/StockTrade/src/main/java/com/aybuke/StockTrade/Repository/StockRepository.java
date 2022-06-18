package com.aybuke.StockTrade.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aybuke.StockTrade.Model.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
	
	 Optional<Stock> findByCode(String code);

}
