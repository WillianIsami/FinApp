package com.FinApp.controller;

import com.FinApp.dto.JwtResponse;
import com.FinApp.dto.LoginRequest;
import com.FinApp.dto.RegisterRequest;
import com.FinApp.model.Role;
import com.FinApp.model.User;
import com.FinApp.repository.RoleRepository;
import com.FinApp.repository.UserRepository;
import com.FinApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.password()
                    )
            );
            String token = jwtUtil.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        if(userRepository.findByEmail(registerRequest.email()) != null){
            return ResponseEntity.badRequest().body("Email is already taken");
        }
        Set<Role> roles = new HashSet<>();
        for (String roleName : registerRequest.roles()) {
            Role userRole = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(userRole);
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User newUser = new User(registerRequest.username(), registerRequest.email(), encryptedPassword, roles);
        userRepository.save(newUser);

        String token = jwtUtil.generateToken(newUser);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}