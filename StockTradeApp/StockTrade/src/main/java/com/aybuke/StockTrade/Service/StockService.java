package com.aybuke.StockTrade.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aybuke.StockTrade.Repository.StockRepository;
import com.aybuke.StockTrade.Model.Stock;

@Service
@Transactional
public class StockService {
	
	@Autowired
    private StockRepository stockRepository;
	
	public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    public Stock getStock(Integer id) {
        return stockRepository.findById(id).get();
    }

    public void deleteStock(Integer id) {
        stockRepository.deleteById(id);
    }

    public Stock getByCode(String code) {
        List<Stock>  liste = listAllStocks();
        Stock stockTmp = new Stock();
        for(Stock stock : liste){
            if(stock.getCode().equals(code)){
                stockTmp.setCode(code);
                stockTmp.setName(stock.getName());
                stockTmp.setId(stock.getId());
            }
        }
        return stockTmp;
    }
    public List<Stock> listAllStocks() {
        return stockRepository.findAll();
    }

}
