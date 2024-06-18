package com.example.itssd.entity;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Resp {
    List<User> friends;
    List<User> recommendFriends;
}
