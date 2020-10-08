package com.sebag.productapp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @NonNull private List<String> cart;
    @NonNull private String email;
}
