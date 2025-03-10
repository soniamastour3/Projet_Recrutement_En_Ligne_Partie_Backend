package com.securityModel.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securityModel.models.User;
import com.securityModel.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String nom) throws UsernameNotFoundException {
    User user = userRepository.findByNom(nom)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with nom: " + nom));

    return UserDetailsImpl.build(user);
  }

}
