package com.redis.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash(value = "users")
public class User {

    @Id
    @Indexed
    private Long id;
    private String username;
    private String nama;
    private String password;
    private int status;
}
