package com.aybuke.StockTrade.Model;

import javax.persistence.*;


import org.springframework.security.core.GrantedAuthority;




@Entity
@Table(name = "role")
public class Role {
	
	 @Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	 
	    @Enumerated(EnumType.STRING)
	    @Column(length = 20)
	    private EnumRole name;
	 
	 GrantedAuthority grantedAuthority ;
	 
	 public Role() {

	    }
	    public void setRole(Role role){
	        this.name = role.getName();
	        this.id = role.getId();
	    }

	    public Role(EnumRole name) {
	        this.name = name;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public EnumRole getName() {
	        return name;
	    }

	    public void setName(EnumRole name) {
	        this.name = name;
	    }



}
