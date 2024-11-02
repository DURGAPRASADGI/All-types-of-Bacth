package com.example.Batch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`order`")  // Escaping the reserved keyword with backticks
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    private String email;
    
    @Column(name = "dept_id")
    private long deptId;
}
