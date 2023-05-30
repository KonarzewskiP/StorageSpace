package com.storage.security.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.repositories.UserRepository;
import com.storage.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username))
            throw new BadRequestException("Invalid username");

        return userRepository
                .findByEmailIgnoreCase(username)
                .map(user -> new User(
                        user.getEmail(),
                        user.getPassword(),
                        user.isEnabled(), true, true, true,
                        List.of(new SimpleGrantedAuthority(user.getRole().toString()))
                ))
                .orElseThrow(() -> new NotFoundException("Can not authenticate user"));


    }
}
