package com.dhanshree.Project.Service;

import com.dhanshree.Project.DTO.UserDTO;
import com.dhanshree.Project.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService  extends UserDetailsService{

    public User findByUserName(String name);
    public void addUser(UserDTO u);

    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;
}
