package com.sebag.productapp.controllers;

import com.sebag.productapp.dtos.OrderDTO;
import com.sebag.productapp.dtos.OrderRequestDTO;
import com.sebag.productapp.entities.Order;
import com.sebag.productapp.entities.Product;
import com.sebag.productapp.repositories.OrderRepository;
import com.sebag.productapp.repositories.ProductRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Gets all orders.
     *
     * @param startDate Start date in ISO DATETIME format (optional)
     * @param endDate   End date in ISO DATETIME format (optional)
     * @return the orders
     */
    @GetMapping
    @ApiOperation(value = "Get all orders and optionally filter them by start/end date")
    public List<OrderDTO> getAllOrders(
            @ApiParam(value = "optional start date in ISO DATETIME format", defaultValue = "2020-10-08T09:13:52.123+00:00") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
            @ApiParam(value = "optional end date in ISO DATETIME format", defaultValue = "2020-10-08T22:13:52.123+00:00") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
        List<Order> orders;
        if (startDate != null && endDate != null) {
            orders = orderRepository.getAllOrdersBetweenDates(startDate, endDate);
        } else {
            orders = orderRepository.findAll();
        }
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Add order.
     *
     * @param order the order, containing a list of SKUs (cart) and the email
     * @return the order dto
     */
    @PostMapping
    @ApiOperation(value = "Add an order")
    public OrderDTO addOrder(@ApiParam(value = "the order, containing a list of SKUs (cart) and the email")
                                 @RequestBody OrderRequestDTO order) {
        Order orderToSave = modelMapper.map(order, Order.class);
        orderToSave.setProducts(new ArrayList<>());
        for (String sku : order.getCart()) {
            log.info(orderToSave.toString());
            Optional<Product> product = productRepository.findById(sku);
            product.ifPresent(value -> orderToSave.getProducts().add(value));
        }
        if (!orderToSave.getProducts().isEmpty()) {
            log.info(orderToSave.toString());
            Order savedOrder = orderRepository.save(orderToSave);
            return modelMapper.map(savedOrder, OrderDTO.class);
        }
        return null;
    }

}
