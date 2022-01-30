package com.oguzhan.rig.repository;

import com.oguzhan.rig.dao.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findAllOrderByCustomerId(Long customerId, Pageable pageable);
}
