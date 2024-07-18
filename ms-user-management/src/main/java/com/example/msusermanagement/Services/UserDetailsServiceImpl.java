package com.example.msusermanagement.Services;


import com.example.msusermanagement.Entities.User;
import com.example.msusermanagement.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(user.getDisabled())
                .authorities(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).toList())
                .build();
        //return UserDetailsImpl.build(user);
    }
    public User saveCustomer(User customer){
        return userRepository.save(customer);
    }

    public Long isCustomerPresent(User customer){
        User customer1 = userRepository.findByEmailAndUsername(customer.getEmail(),customer.getUsername());
        return customer1!=null ? customer1.getId(): null ;
    }
}
