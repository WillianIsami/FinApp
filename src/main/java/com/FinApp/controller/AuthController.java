package com.FinApp.controller;

import com.FinApp.dto.JwtResponse;
import com.FinApp.dto.RegisterRequest;
import com.FinApp.model.Role;
import com.FinApp.model.User;
import com.FinApp.dto.LoginRequest;
import com.FinApp.repository.RoleRepository;
import com.FinApp.repository.UserRepository;
import com.FinApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }
        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Set<String> roles = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());
            String token = jwtUtil.generateToken(user, roles);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("Email is already taken");
        }
        Set<Role> roles = new HashSet<>();
        for (String roleName : registerRequest.getRoles()) {
            Role userRole = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(userRole);
        }
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRoles(roles);

        userRepository.save(newUser);
        Set<String> roles_to_token = newUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        String token = jwtUtil.generateToken(newUser, roles_to_token);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}