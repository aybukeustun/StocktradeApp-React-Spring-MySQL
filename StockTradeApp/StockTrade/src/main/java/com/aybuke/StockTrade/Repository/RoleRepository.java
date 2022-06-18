package com.aybuke.StockTrade.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aybuke.StockTrade.Model.Role;
import com.aybuke.StockTrade.Model.EnumRole;

public interface RoleRepository extends JpaRepository<Role,Integer>{
	Optional<Role> findByName(EnumRole name);

}
