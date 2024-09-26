package com.dongyang.seoulTravel.repository.member;

import com.dongyang.seoulTravel.entity.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT user_Id FROM User WHERE user_Id = :userId")
    Optional<User> findByUserId(String userId);

    Optional<User> findByEmailOrName(String email, String name);

    Optional<User> findByEmail(String email);
}
