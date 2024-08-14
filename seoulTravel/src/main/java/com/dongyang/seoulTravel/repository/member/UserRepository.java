package com.dongyang.seoulTravel.repository.member;

import com.dongyang.seoulTravel.entity.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
}
