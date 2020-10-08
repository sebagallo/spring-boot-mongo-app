package com.sebag.productapp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private String id;

    @DBRef
    @NonNull
    private List<Product> products;

    @NonNull private String email;

    private Date creationDate = new Date();
}
