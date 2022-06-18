package com.aybuke.StockTrade.Controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aybuke.StockTrade.Service.StockService;
import com.aybuke.StockTrade.Service.UserService;
import com.aybuke.StockTrade.Model.Stock;
import com.aybuke.StockTrade.Payload.Request.StockInfoRequest;
import com.aybuke.StockTrade.Model.EnumRole;
import com.aybuke.StockTrade.Model.Role;
import com.aybuke.StockTrade.Payload.Request.UserInfoRequest;
import com.aybuke.StockTrade.Payload.Response.MessageResponse;
import com.aybuke.StockTrade.Repository.RoleRepository;
import com.aybuke.StockTrade.Model.User;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    
    Random rnd = new Random(); 
    
    @Autowired
    StockService stockService;
    
    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody UserInfoRequest user) throws URISyntaxException {
        if (userService.existsUserByName(user.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsUserByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User userTmp = new User(user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getUsername(),
                encoder.encode(user.getPassword()));
        String strRole = user.getRole();
        Role role = new Role();

        if(strRole.equalsIgnoreCase("admin")){
            Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role.setRole(adminRole);
        }else{

        	 Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role.setRole(userRole);
        }

        userTmp.setRole(role);
        userTmp = userService.saveUser(userTmp);

        return ResponseEntity.ok(userTmp);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id,@Valid @RequestBody UserInfoRequest user) {
        User userTmp = userService.getUser(id);
        userTmp.setName(user.getName());
        userTmp.setSurname(user.getSurname());
        userTmp.setUsername(user.getUsername());
        userTmp.setEmail(user.getEmail());

        String strRole = user.getRole();
        Role role = new Role();

        if(strRole.equalsIgnoreCase("admin")){
            Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role.setRole(adminRole);
        }else{

            Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role.setRole(userRole);
        }

        userTmp.setRole(role);
        userTmp = userService.saveUser(userTmp);

        return ResponseEntity.ok(userTmp);
    }

    
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {

        userService.deleteUser(id);
    }
    
    @GetMapping("/users")
    public List<User> listAllUsers() {
    	return userService.listAllUser(); 
    	}
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/stocks")
    public void add(@Valid @RequestBody StockInfoRequest stock) {
        Stock stockTmp = new Stock();
        stockTmp.setName(stock.getName());
        stockTmp.setCode(stock.getCode());
        stockTmp.setPrice(rnd.nextDouble()*10);
        stockService.saveStock(stockTmp);
    }
    @PutMapping("/stocks/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody StockInfoRequest stock, @PathVariable Integer id) {
        try {
            Stock stockTmp = stockService.getStock(id);
            stockTmp.setName(stock.getName());
            stockTmp.setCode(stock.getCode());
            stockService.saveStock(stockTmp);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/stocks/{id}")
    public void delete(@PathVariable Integer id) {

        stockService.deleteStock(id);
    }
    @GetMapping("/stocks")
    public List<Stock> list() {
        return stockService.listAllStocks();
    }

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
