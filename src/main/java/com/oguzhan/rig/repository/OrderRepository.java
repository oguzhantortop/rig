package com.oguzhan.rig.repository;

import com.oguzhan.rig.dao.Order;
import com.oguzhan.rig.dto.MonthlyOrderStatDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findAllOrderByCustomerId(Long customerId, Pageable pageable);

    @Query(value = "from Order o where o.orderDate BETWEEN :startDate AND :endDate")
    public List<Order> getAllOrdersByDateRange(@Param("startDate") Date startDate, @Param("endDate")Date endDate);


    @Query(name= "getMonthlyOrderStats" ,value = "SELECT MONTHNAME(o.order_date) as month ,count(o.id) as totalOrderCount, " +
            "sum(od.count) as totalBookCount,sum(o.total_price) as totalAmount " +
            "FROM orders o, ORDERS_ORDER_DETAIL_LIST oo, ORDER_DETAIL od " +
            "where o.id = oo.order_id and oo.order_detail_list_id = od.id and o.customer_id = ?1 " +
            "group by MONTHNAME(o.order_date) order by month", nativeQuery = true)
    List<MonthlyOrderStatDto> getMonthlyOrderStats(Long customerId);

}
