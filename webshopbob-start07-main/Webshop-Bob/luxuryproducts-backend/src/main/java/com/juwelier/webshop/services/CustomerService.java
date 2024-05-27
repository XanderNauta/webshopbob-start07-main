package com.juwelier.webshop.services;

import com.juwelier.webshop.dao.CustomerRepository;
import com.juwelier.webshop.models.Customer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerService implements UserDetailsService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        return new User(email,
                customer.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("USER"))
        );
    }

    public Customer getActiveUser() {
        try {
            UsernamePasswordAuthenticationToken token =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            return customerRepository.findByEmail(token.getName());
        } catch (ClassCastException e) {
            return null;
        }
    }
}
