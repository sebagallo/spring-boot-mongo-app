package com.sebag.productapp;

import com.sebag.productapp.dtos.OrderDTO;
import com.sebag.productapp.entities.Order;
import com.sebag.productapp.entities.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();
        Converter<List<Product>, List<String>> productToString = mappingContext -> mappingContext.getSource() == null || mappingContext.getSource().isEmpty() ? null :
                mappingContext.getSource().stream().map(Product::getSku).collect(Collectors.toList());
        Converter<List<Product>, Double> productsTotalPrice = mappingContext -> mappingContext.getSource() == null || mappingContext.getSource().isEmpty() ? null :
                mappingContext.getSource().stream().mapToDouble(Product::getPrice).reduce(0, Double::sum);
        TypeMap<Order, OrderDTO> orderDTOTypeMap = mm.typeMap(Order.class, OrderDTO.class);
        orderDTOTypeMap.addMappings(mapper -> mapper.using(productToString).map(Order::getProducts, OrderDTO::setProducts));
        orderDTOTypeMap.addMappings(mapper -> mapper.using(productsTotalPrice).map(Order::getProducts, OrderDTO::setTotalPrice));
        return mm;
    }
}
