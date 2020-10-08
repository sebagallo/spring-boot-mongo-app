package com.sebag.productapp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class Product {

    @Id
    @NonNull private String sku;
    @NonNull private String name;
    @NonNull private Double price;
    private Boolean deleted = false;
    private Date creationDate = new Date();
}
