package com.example.springchat.entities;

import lombok.Data;

@Data
public class Rabbit {
    String host;
    int port;
    String user;
    String pass;

    String exchange;
    String queue;

    Integer maxConnections;
    Integer maxConcurrentConsumers;
}
