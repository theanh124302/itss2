package com.example.itssd.service;

import com.example.itssd.entity.FriendShip;
import com.example.itssd.entity.Resp;
import com.example.itssd.entity.User;
import com.example.itssd.repository.FriendShipRepository;
import com.example.itssd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendShipRepository friendShipRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersByMbti(String mbti) {
        return userRepository.findByMbti(mbti);
    }

    public List<User> getAllFriends(Long userId) {
        List<User> friends = new ArrayList<>();
        List<FriendShip> sentFriendShips = friendShipRepository.findBySenderIdAndStatus(userId, 1L);
        List<FriendShip> receivedFriendShips = friendShipRepository.findByReceiverIdAndStatus(userId, 1L);
        for (FriendShip friendShip : sentFriendShips) {
            friends.add(userRepository.findById(friendShip.getReceiverId()).orElse(null));
        }
        for (FriendShip friendShip : receivedFriendShips) {
            friends.add(userRepository.findById(friendShip.getSenderId()).orElse(null));
        }
        return friends;
    }

//    public User logIn() {
//        Random random = new Random();
//        User user = null;
//        while (user == null || user.getStatus() == 1L) {
//            Long id = 1L + random.nextInt(30); // Random id from 1 to 30
//            user = userRepository.findById(id).orElse(null);
//        }
//        user.setStatus(1L);
//        userRepository.save(user);
//        return user;
//    }

    public User logIn() {
        List<User> availableUsers = userRepository.findByStatus(0L);
        if (availableUsers.isEmpty()) {
            return null;
        }
        Random random = new Random();
        User chosenUser = availableUsers.get(random.nextInt(availableUsers.size()));
        chosenUser.setStatus(1L);
        userRepository.save(chosenUser);
        return chosenUser;
    }

    public void logOut(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setStatus(0L);
            userRepository.save(user);
        }
    }

    public Resp getRecommendFriends(Long userId) {
        Resp resp = new Resp();
        List<User> recommendFriends = new ArrayList<>();
        List<User> friends = new ArrayList<>();
        User currentUser = userRepository.findById(userId).orElse(null);
        if (currentUser == null) {
            return resp;
        }
        List<FriendShip> sentFriendShips = friendShipRepository.findBySenderIdAndStatus(userId, 1L);
        List<FriendShip> receivedFriendShips = friendShipRepository.findByReceiverIdAndStatus(userId, 1L);
        for (FriendShip friendShip : sentFriendShips) {
            friends.add(userRepository.findById(friendShip.getReceiverId()).orElse(null));
        }
        for (FriendShip friendShip : receivedFriendShips) {
            friends.add(userRepository.findById(friendShip.getSenderId()).orElse(null));
        }
        resp.setFriends(friends);
        String currentUserMbti = currentUser.getMbti();
        List<User> equalMbtiUsers = getUsersByMbti(currentUserMbti);
        for (User user : equalMbtiUsers) {
            if (!friends.contains(user) && !user.getId().equals(userId) && user.getMbti().equals(currentUserMbti)) {
                recommendFriends.add(user);
            }
        }
        resp.setRecommendFriends(recommendFriends);
        return resp;
    }
}
