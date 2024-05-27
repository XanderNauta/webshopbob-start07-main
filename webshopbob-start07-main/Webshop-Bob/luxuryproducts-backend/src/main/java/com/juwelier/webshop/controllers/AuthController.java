package com.juwelier.webshop.controllers;

import com.juwelier.webshop.config.JWTUtil;
import com.juwelier.webshop.dao.CustomerRepository;
import com.juwelier.webshop.dto.AuthenticationDTO;
import com.juwelier.webshop.dto.LoginResponse;
import com.juwelier.webshop.models.Customer;
import com.juwelier.webshop.services.CredentialValidator;
import com.juwelier.webshop.services.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://s1130782.student.inf-hsleiden.nl:10782"})
@RequestMapping("/account")
public class AuthController {
    private final CustomerRepository customerRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private CredentialValidator validator;
    private CustomerService customerService;

    public AuthController(CustomerRepository customerRepository, JWTUtil jwtUtil, AuthenticationManager authManager, PasswordEncoder passwordEncoder, CredentialValidator validator, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody AuthenticationDTO authenticationDTO) {
        if (!validator.isValidEmail(authenticationDTO.email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid email provided"
            );
        }

        if (!validator.isValidPassword(authenticationDTO.password)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid password provided"
            );
        }

        Customer customer = this.customerRepository.findByEmail(authenticationDTO.email);

        if (customer != null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Can not register with this email"
            );
        }
        String encodedPassword = passwordEncoder.encode(authenticationDTO.password);

        Customer registerdCustomUser = new Customer(authenticationDTO.firstName,
                authenticationDTO.lastName,
                authenticationDTO.email,
                encodedPassword,
                "USER"
        );
        this.customerRepository.save(registerdCustomUser);
        String token = jwtUtil.generateToken(registerdCustomUser.getEmail());
        LoginResponse loginResponse = new LoginResponse(registerdCustomUser.getEmail(), token);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationDTO body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.email, body.password);

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.email);

            Customer customer = this.customerRepository.findByEmail(body.email);
            LoginResponse loginResponse = new LoginResponse(customer.getEmail(), token);


            return ResponseEntity.ok(loginResponse);

        } catch (AuthenticationException authExc) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No valid credentials"
            );
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");
        return ResponseEntity.ok("You have been logged out successfully 2");
    }

    @GetMapping
    public ResponseEntity<Customer> getCurrentUser() {
        Customer customer = customerService.getActiveUser();
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String email) {
        return ResponseEntity.ok(customerRepository.findByEmail(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateUser(@RequestBody AuthenticationDTO authenticationDTO, @PathVariable String email) {
        Customer updatedCustomer = this.customerRepository.findByEmail(email);
        updatedCustomer.setFirstName(authenticationDTO.firstName);
        updatedCustomer.setLastName(authenticationDTO.lastName);
        updatedCustomer.setEmail(authenticationDTO.email);
        updatedCustomer.setPassword(passwordEncoder.encode(authenticationDTO.password));
        this.customerRepository.save(updatedCustomer);
        return ResponseEntity.ok("Customer was succesfully updated.");
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String email) {
        this.customerRepository.delete(this.customerRepository.findByEmail(email));
        return ResponseEntity.ok("Customer was succesfully removed.");
    }
}

