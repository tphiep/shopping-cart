package com.example.products.domain;

import com.example.exceptions.OutOfStockException;
import com.example.web.ProductInfo;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Entity(name = "products")
@Access(AccessType.FIELD)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Embedded
    private Quantity quantityLimit;

    @ElementCollection
    private Map<Long, Quantity> quantityReservations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<Price> prices = new ArrayList<>();

    @ManyToOne
    @JoinFormula(
            "(SELECT p.id FROM prices p WHERE p.product_id = id ORDER BY p.created_date DESC LIMIT 1)"
    )
    private Price latestPrice;

    public Long getId() {
        return id;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void reserveProductQuantity(Long orderId, Quantity productOrderQuantity) {
        if (availableCredit().isGreaterThanOrEqual(productOrderQuantity)) {
            quantityReservations.put(orderId, productOrderQuantity);
        } else
            throw new OutOfStockException();
    }

    public void addPrice(BigDecimal priceValue) {
        Price price = new Price();
        price.setPrice(priceValue);
        getPrices().add(price);
        price.setProduct(this);
        latestPrice = price;
    }

    public Price getLatestPrice() {
        return latestPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quantity getQuantityLimit() {
        return quantityLimit;
    }

    public void setQuantityLimit(Quantity quantityLimit) {
        this.quantityLimit = quantityLimit;
    }

    public static Product create(ProductInfo productInfo) {
        Product product = new Product();
        Price price = new Price();
        price.setPrice(productInfo.getPrice());
        product.getPrices().add(price);
        product.setName(productInfo.getName());
        product.setQuantityLimit(new Quantity(productInfo.getQuantity()));
        price.setProduct(product);
        product.quantityReservations = Collections.emptyMap();
        return product;
    }

    public void setLatestPrice(Price latestPrice) {
        this.latestPrice = latestPrice;
    }

    protected Quantity availableCredit() {
        return quantityLimit.subtract(quantityReservations.values().stream().reduce(Quantity.ZERO, Quantity::add));
    }


}
