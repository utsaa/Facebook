package com.utsa.identityService.repository;

import com.utsa.identityService.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u.userId from User u where u.name=:name and u.password=:password")
    List<Integer> getUserByNameAndPasswd(@Param("name") String name, @Param("password") String password);
}
