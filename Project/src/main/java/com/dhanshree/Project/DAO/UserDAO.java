package com.dhanshree.Project.DAO;

import com.dhanshree.Project.Entity.User;

public interface UserDAO {
    User findByUserName(String userName);

    void save(User theUser);
}
