package com.example.itssd.repository;

import com.example.itssd.entity.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  FriendShipRepository extends JpaRepository<FriendShip, Long> {
    List<FriendShip> findBySenderIdAndStatus(Long senderId, Long status);
    List<FriendShip> findByReceiverIdAndStatus(Long receiverId, Long status);
}
