package com.rodarte.models.service;

import com.rodarte.models.dao.UserDao;
import com.rodarte.models.entity.Role;
import com.rodarte.models.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// the AuthenticationProvider bean will use this implementation of the UserDetailsService to use the Authentication
// credentials and find the user by username
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDao userDao;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    // this is the method our AuthenticationProvider instance will access through the UserDetailsService implementation
    // to attempt to find a user through any method we implemented (database storage in this case)
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);

        if (user == null) {
            logger.error("User with username " + username + " was not found in the database.");
            throw new UsernameNotFoundException("User with username " + username + " was not found in the database.");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getEnabled(),
            true,
            true,
            true,
            mapRolesToAuthorities(user.getRoles())
        );

    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    private static Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
