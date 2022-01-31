package com.oguzhan.rig.security;

import java.util.ArrayList;

import com.oguzhan.rig.dao.Customer;
import com.oguzhan.rig.dto.CustomerDto;
import com.oguzhan.rig.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    CustomerService customerService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer =null;
        try {
            customer = customerService.getCustomerByEmail(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (customer == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(customer.getEmail(), bcryptEncoder.encode(customer.getPassword()),
                new ArrayList<>());
    }
}
