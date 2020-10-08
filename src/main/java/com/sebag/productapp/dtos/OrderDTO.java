package com.sebag.productapp.dtos;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private String id;
    private List<String> products;
    private String email;
    private Date creationDate;
    private double totalPrice;
}
