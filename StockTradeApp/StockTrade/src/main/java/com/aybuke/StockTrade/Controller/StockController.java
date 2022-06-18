package com.aybuke.StockTrade.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aybuke.StockTrade.Model.Stock;
import com.aybuke.StockTrade.Service.StockService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stocks")
public class StockController {
	
	@Autowired
	StockService stockService;
	
	@GetMapping("")
    public List<Stock> list() {
        return stockService.listAllStocks();
        
    }
    @PostMapping("")
    @GetMapping("/stocks/{id}")
    public ResponseEntity<Stock> get(@PathVariable Integer id) {
        try {
            Stock stock = stockService.getStock(id);
            return new ResponseEntity<Stock>(stock, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Stock>(HttpStatus.NOT_FOUND);
        }
    }
    

}
