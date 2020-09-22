package com.zvkvc.eksperti.service;

import com.zvkvc.eksperti.dao.UserRepository;
import com.zvkvc.eksperti.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

// our custom UserDetailsService class
// the job is to give AuthManager correct UserDetail object to authenticate against
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Optional<User> userOptional = userRepository.findByUsername(username);
         User user =  userOptional.orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));

         // return user.map(MyUserDetails::new).get();
        // return Spring Security User(kind of UserDetails) object constructed from our  model User
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER")); // here we assign USER role
        // this class is provided by Spring and implements UserDetails interface
        // here, we're just mapping UserDetails to User class

    }
    //                <? extends Object>
    private Collection<? extends GrantedAuthority> getAuthorities(String role) { // for assigning roles
        return singletonList(new SimpleGrantedAuthority(role));
    }


}

