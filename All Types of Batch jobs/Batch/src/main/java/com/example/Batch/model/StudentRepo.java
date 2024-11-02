package com.example.Batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StudentRepo {
     private long id;
     private String firstName;
     private String lastName;
     private String email;
}
