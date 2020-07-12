package com.example.products.domain;

import com.example.domain.ItemLine;
import com.example.exceptions.OutOfStockException;
import com.example.web.ProductInfo;

import java.util.List;
import java.util.Optional;


public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(ProductInfo info) {
        Product product = Product.create(info);
        productRepository.save(product);
        return product;
    }

    public Optional<Product> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product;
    }

//    @Transactional
    /**
     * Reserve product quantity if there is an item exceed available quantity, an {@code OutOfStockException} will be thrown.
     * @param items
     * @param orderId
     * @throws OutOfStockException
     */
    public void updateProducts(List<ItemLine> items, Long orderId) throws OutOfStockException {

        for (ItemLine item : items) {
            Optional<Product> product = productRepository.findById(item.getProductId());
            product.orElseThrow(() -> new OutOfStockException(String.format("Product with id % is not found", item.getProductId())))
                    .reserveProductQuantity(orderId, new Quantity(item.getAmount()));
        }
    }
}
