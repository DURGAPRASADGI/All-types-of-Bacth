package com.example.Batch.postgrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Batch.entity.Student;

@Repository
public interface PostgreRepository extends JpaRepository<Student, Long> {

}
