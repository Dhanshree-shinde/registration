package com.dhanshree.Project.Service.ServiceImpl;

import com.dhanshree.Project.DAO.RoleDAO;
import com.dhanshree.Project.DAO.UserDAO;
import com.dhanshree.Project.DTO.UserDTO;
import com.dhanshree.Project.Entity.Role;
import com.dhanshree.Project.Entity.User;
import com.dhanshree.Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private RoleDAO roleDao;

    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserServiceImpl(UserDAO repo,RoleDAO roleDao, BCryptPasswordEncoder passwordEncoder){
        this.userDAO=repo;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public User findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }

    @Override
    public void addUser(UserDTO userDTO) {

        User user=new User(
                userDTO.getUserName(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getMobile(),
                userDTO.getGender()

        );
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setGender(userDTO.getGender());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

        user.setEnabled(true);

        userDAO.save(user);
        return ;

    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDAO.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                authorities);
    }
    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }

}
