package com.dongyang.seoulTravel.repository.member;

import com.dongyang.seoulTravel.entity.member.Friendship;
import com.dongyang.seoulTravel.entity.member.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
}
