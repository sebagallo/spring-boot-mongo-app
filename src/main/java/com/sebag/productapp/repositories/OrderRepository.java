package com.sebag.productapp.repositories;

import com.sebag.productapp.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    @Query("{'creationDate': {$gte: ?0, $lte:?1 }}")
    List<Order> getAllOrdersBetweenDates(Date startDate, Date endDate);
}
