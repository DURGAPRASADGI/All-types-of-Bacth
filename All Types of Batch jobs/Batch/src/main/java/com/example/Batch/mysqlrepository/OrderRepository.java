package com.example.Batch.mysqlrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Batch.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
