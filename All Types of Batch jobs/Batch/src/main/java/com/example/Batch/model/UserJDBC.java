package com.example.Batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserJDBC {
     private long userId;
     private String username;
     private String email;
     private String password;
}
