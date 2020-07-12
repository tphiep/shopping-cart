package com.example.web;

import com.example.products.domain.Product;

public class GetProductResponse {

    private ProductInfo info;

    public ProductInfo getInfo() {
        return info;
    }

    public void setInfo(ProductInfo info) {
        this.info = info;
    }

    public static GetProductResponse from(Product product) {
        final GetProductResponse response = new GetProductResponse();
        ProductInfo info = new ProductInfo();
        response.setInfo(info);
        info.setName(product.getName());
        info.setPrice(product.getLatestPrice().getPrice());
        info.setQuantity(product.getQuantityLimit().getAmount());
        info.setId(product.getId());
        return response;
    }
}
