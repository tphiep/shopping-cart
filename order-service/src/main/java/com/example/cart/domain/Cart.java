package com.example.cart.domain;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {

    private String id;
    private List<CartItem> items;
}
