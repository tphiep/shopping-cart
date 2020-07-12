package com.example.domain;

import java.math.BigDecimal;

public class ItemLine {
    private long productId;
    private long amount;
    private BigDecimal price;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static ItemLine make(long productId, int amount, BigDecimal price) {
        ItemLine item = new ItemLine();
        item.setAmount(amount);
        item.setPrice(price);
        item.setProductId(productId);

        return item;
    }

}
