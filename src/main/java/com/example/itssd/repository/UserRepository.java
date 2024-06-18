package com.example.itssd.repository;

import com.example.itssd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByMbti(String mbti);
    User findByIdAndStatus(Long id, Long status);
    User findByEmail(String email);
    List<User> findByStatus(Long status);
    @Query("SELECT u FROM User u WHERE u.id IN (SELECT f.receiverId FROM FriendShip f WHERE f.senderId = :userId AND f.status = 1L) OR u.id IN (SELECT f.senderId FROM FriendShip f WHERE f.receiverId = :userId AND f.status = 1L)")
    List<User> findFriends(@Param("userId") Long userId);
}
