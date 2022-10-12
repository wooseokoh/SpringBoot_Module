package com.example.cloud.product01.product;

import com.example.cloud.api.product.Product;
import com.example.cloud.api.product.ProductService;
import com.example.util.http.ServiceUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ServiceUtil serviceUtil;

    @Override
    public Product getProduct(int productId) {
        return null;
    }
}
