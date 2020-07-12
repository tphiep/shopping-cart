package com.example.products.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "prices")
@Access(AccessType.FIELD)
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    @Column(name = "created_date", nullable=false, updatable=false)
    private Date createdDate;

    private BigDecimal price;

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }
}