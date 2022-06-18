package com.aybuke.StockTrade.Controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aybuke.StockTrade.Repository.UserRepository;
import com.aybuke.StockTrade.Model.EnumRole;
import com.aybuke.StockTrade.Model.Role;
import com.aybuke.StockTrade.Model.User;
import com.aybuke.StockTrade.Security.UserDetailsImpl;
import com.aybuke.StockTrade.Repository.RoleRepository;
import com.aybuke.StockTrade.Payload.Request.LoginRequest;
import com.aybuke.StockTrade.Payload.Request.SignupRequest;
import com.aybuke.StockTrade.Payload.Response.MessageResponse;
import com.aybuke.StockTrade.Payload.Response.JwtResponse;
import com.aybuke.StockTrade.Security.JwtUtils;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	  AuthenticationManager authenticationManager;
	  @Autowired
	    RoleRepository roleRepository;
    @Autowired
	  UserRepository userRepository;
    @Autowired
	  PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        String strRole = signUpRequest.getRole();
        Role role = new Role();
        
      

        if (strRole == "") {
            Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role.setRole(userRole);
        } else {
            if(strRole == "admin") {
                Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                role.setRole(adminRole);

            }else{
                Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                role.setRole(userRole);
            }}



        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
