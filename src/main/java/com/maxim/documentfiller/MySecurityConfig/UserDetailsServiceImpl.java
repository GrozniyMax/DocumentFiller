package com.maxim.documentfiller.MySecurityConfig;

import com.maxim.documentfiller.PostgresDB.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var dbUser = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        return User.withUsername(dbUser.getUsername())
                .password(dbUser.getPassword())
                .roles(dbUser.getRole().toString()).build();
    }
}
