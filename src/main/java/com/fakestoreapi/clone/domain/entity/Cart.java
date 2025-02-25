package com.fakestoreapi.clone.domain.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    private Long id;
    private User user;
    private LocalDate date;
    private List<CartItem> products;
}
