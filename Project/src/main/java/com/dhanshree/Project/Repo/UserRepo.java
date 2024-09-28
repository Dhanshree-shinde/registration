package com.dhanshree.Project.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dhanshree.Project.Entity.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer> {

}
