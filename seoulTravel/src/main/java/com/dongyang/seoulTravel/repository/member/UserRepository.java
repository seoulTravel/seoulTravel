package com.dongyang.seoulTravel.repository.member;

import com.dongyang.seoulTravel.entity.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.user_Id = :userId")
    Optional<User> findByUserId(@Param("userId") String userId);

    @Query("SELECT u FROM User u WHERE u.user_email = :email OR u.user_name = :name")
    Optional<User> findByEmailOrName(@Param("email") String email, @Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.user_email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
