package com.user.Repository;

import com.user.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User findByUseridAndPassword(@Param("email") String userid, @Param("password") String password);
    User findByEmail(String Email);
    List<User> findByRole(String role);
}
