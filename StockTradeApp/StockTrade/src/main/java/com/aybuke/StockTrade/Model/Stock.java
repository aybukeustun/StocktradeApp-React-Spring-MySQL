package com.aybuke.StockTrade.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "stock")
public class Stock {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id=0;
	    private String code = "";
	    private String name = "";
	    private Double price= 0.0;
	    @JsonIgnore
	    @OneToMany(mappedBy = "stock")
	    Set<Transaction> transactions = new HashSet<>();


	    public Stock() {
	    }

	    public Stock(String code, String name) {
	        this.code = code;
	        this.name = name;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Set<Transaction> getTransactions() {
	        return transactions;
	    }

	    public void setTransactions(Set<Transaction> transactions) {
	        this.transactions = transactions;
	    }

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
		
			this.price = price;
		}

}
