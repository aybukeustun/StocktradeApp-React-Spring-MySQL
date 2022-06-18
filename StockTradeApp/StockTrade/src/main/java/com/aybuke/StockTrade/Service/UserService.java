package com.aybuke.StockTrade.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aybuke.StockTrade.Model.User;
import com.aybuke.StockTrade.Repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
    private UserRepository userRepository;
   
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }
    public Boolean existsById(Integer id){
        return userRepository.existsById(id);
    }
    
    public List<User> listAllUser() {
        return userRepository.findAll();
    }
    public Boolean existsUserByName(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

  

}
