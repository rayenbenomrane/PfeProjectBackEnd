package com.example.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.repository.CompteRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class UserServiceImpli implements UserService {


	@Autowired
	private CompteRepository compteRepository;


@Autowired
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return compteRepository.findByEmail(username)
                        .orElseThrow(()->new UsernameNotFoundException("user not found"));
            }
        };
    }
}