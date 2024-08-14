package com.dongyang.seoulTravel.repository.member;

import com.dongyang.seoulTravel.entity.member.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
