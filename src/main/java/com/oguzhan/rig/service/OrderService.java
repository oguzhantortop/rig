package com.oguzhan.rig.service;

import com.oguzhan.rig.dao.Book;
import com.oguzhan.rig.dao.Customer;
import com.oguzhan.rig.dao.Order;
import com.oguzhan.rig.dao.OrderDetail;
import com.oguzhan.rig.dto.*;
import com.oguzhan.rig.exception.BusinessException;
import com.oguzhan.rig.exception.ResourceNotFound;
import com.oguzhan.rig.repository.BookRepository;
import com.oguzhan.rig.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderRepository orderRepository;

    public OrderResponseDto getOrder(Long orderId) throws Exception {
        return mapToOrderResponseDto(getOrderEntity(orderId));
    }

    public Order getOrderEntity(Long orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent())
            return order.get();
        else {
            ErrorResponse errorResponse = new ErrorResponse("ORDER_ERR_001","Order with given id does not exists!");
            throw new ResourceNotFound(errorResponse);
        }
    }

    public OrderResponseDto createOrder(OrderRequestDto orderDto) throws Exception {
        Order order = mapToOrderEntity(orderDto);
        Double totalPrice = 0.0;
        for (OrderDetailReqDto orderDetailReqDto: orderDto.getOrderDetailList()) {
            Double subTotalPrice = 0.0;
            Book book2Update = null;
            book2Update = bookRepository.findBookForUpdate(orderDetailReqDto.getBookId());

            if(book2Update == null) {
                ErrorResponse errorResponse = new ErrorResponse("ORDER_ERR_002","Book with given id does not exists!");
                throw new BusinessException(errorResponse);
            }else if(hasEnoughStock(orderDetailReqDto, book2Update)) {
                book2Update.setStockCount(book2Update.getStockCount()-orderDetailReqDto.getCount());
                bookRepository.save(book2Update);

                setOrderDetail(order.getOrderDetailList(), orderDetailReqDto, book2Update);
                totalPrice+= getSubTotalPrice(orderDetailReqDto, book2Update);
            } else {
                ErrorResponse errorResponse = new ErrorResponse("ORDER_ERR_003","Book with given id less than requested");
                throw new BusinessException(errorResponse);
            }
        }
        order.setTotalPrice(totalPrice);
        order = orderRepository.save(order);
        return mapToOrderResponseDto(order);
    }

    private boolean hasEnoughStock(OrderDetailReqDto orderDetailReqDto, Book book2Update) {
        return book2Update.getStockCount() >= orderDetailReqDto.getCount();
    }

    private OrderDetail setOrderDetail(List<OrderDetail> orderDetailList, OrderDetailReqDto orderDetailReqDto, Book book2Update) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setBook(book2Update);
        orderDetail.setCount(orderDetailReqDto.getCount());
        orderDetail.setUnitPrice(book2Update.getUnitPrice().doubleValue());
        orderDetail.setTotalPrice(getSubTotalPrice(orderDetailReqDto, book2Update));
        orderDetailList.add(orderDetail);
        return orderDetail;
    }

    private double getSubTotalPrice(OrderDetailReqDto orderDetailReqDto, Book book2Update) {
        return orderDetailReqDto.getCount() * book2Update.getUnitPrice().doubleValue();
    }

    private Order mapToOrderEntity(OrderRequestDto orderRequestDto) throws Exception{
        Order orderEntity = new Order();
        BeanUtils.copyProperties(orderRequestDto,orderEntity);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderEntity.setOrderDetailList(orderDetailList);
        Customer customer = customerService.getCustomerEntity(orderRequestDto.getCustomerId());
        orderEntity.setCustomer(customer);
        return orderEntity;
    }

    private OrderResponseDto mapToOrderResponseDto(Order orderEntity) {
        OrderResponseDto orderResponseDto  = new OrderResponseDto();
        BeanUtils.copyProperties(orderEntity,orderResponseDto);
        List<OrderDetailResponseDto> list = new ArrayList<>();
        for (OrderDetail orderDetail:orderEntity.getOrderDetailList()) {
            OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto();
            BeanUtils.copyProperties(orderDetail,orderDetailResponseDto);
            list.add(orderDetailResponseDto);
        }
        orderResponseDto.setOrderDetailList(list);
        return orderResponseDto;
    }

    public OrderResponsePageableDto getCustomerOrders(Long customerId, int page, int size) {
        OrderResponsePageableDto orderResponsePageableDto = new OrderResponsePageableDto();
        List<OrderResponseDto> orderList = new ArrayList<>();
        orderResponsePageableDto.setOrderList(orderList);
        Pageable paging = PageRequest.of(page, size);

        Page<Order> pageOrders = orderRepository.findAllOrderByCustomerId(customerId, paging);
        for (Order order:  pageOrders.getContent()) {
            orderList.add(mapToOrderResponseDto(order));
        };
        orderResponsePageableDto.setCurrentPage(pageOrders.getNumber());
        orderResponsePageableDto.setTotalItems(pageOrders.getTotalElements());
        orderResponsePageableDto.setTotalPages(pageOrders.getTotalPages());

        return orderResponsePageableDto;
    }

    public List<OrderResponseDto> getOrdersWithinDate(Date start, Date end) {
        List<OrderResponseDto> orderList = new ArrayList<>();

        List<Order> allOrdersByDateRange = orderRepository.getAllOrdersByDateRange(start, end);

        for (Order order: allOrdersByDateRange) {
            orderList.add(mapToOrderResponseDto(order));
        }
        return orderList;
    }

    public OrderStatsResponseDto getCustomerOrderStats(Long customerId) {
        OrderStatsResponseDto orderStatsResponseDto = new OrderStatsResponseDto();
        List<MonthlyOrderStatDto> monthlyOrderStats = orderRepository.getMonthlyOrderStats(customerId);
        orderStatsResponseDto.setList(monthlyOrderStats);
        return orderStatsResponseDto;
    }
}
