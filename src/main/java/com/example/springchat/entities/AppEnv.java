package com.example.springchat.entities;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("appEnv")
@ConfigurationProperties(prefix = "app.env")
@Data
public class AppEnv {

    private boolean indentJson;
    private Rabbit rabbit;

}
