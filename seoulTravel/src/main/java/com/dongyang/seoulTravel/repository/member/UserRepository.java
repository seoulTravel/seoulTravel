package com.dongyang.seoulTravel.repository.member;

import com.dongyang.seoulTravel.entity.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.user_Id = :userId")
    Optional<User> findByUserId(String userId);

    Optional<User> findByUser_emailOrUser_name(String email, String name); // 변경된 부분

    Optional<User> findByUser_email(String email);
}
