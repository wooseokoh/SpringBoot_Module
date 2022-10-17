package com.cloud.composite.controller;

import com.cloud.api.controller.CompositeControllerInterface;
import com.cloud.api.dto.Composite;
import com.cloud.api.dto.Product;
import com.cloud.api.dto.Recommend;
import com.cloud.api.dto.Review;
import com.cloud.api.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class CompositeController implements CompositeControllerInterface {

    private final IntegrateModule integration;

    @Override
    public void createComposite(Composite body) {
        try {
            Product product = new Product(body.getProductId(), body.getProductName(), null);
            integration.createProduct(product);

            if(body.getRecommendList() != null){
                body.getRecommendList().forEach(r -> {
                    Recommend recommend = new Recommend(body.getProductId(), r.getRecommendId(), r.getAuthor(), r.getContent());
                    integration.createRecommend(recommend);
                });
            }

            if(body.getReviewList() != null){
                body.getReviewList().forEach(r -> {
                    Review review = new Review(body.getProductId(), r.getReviewId(), r.getAuthor(), r.getSubject(),r.getContent());
                    integration.createReview(review);
                });
            }
        }catch (RuntimeException re){
            log.error("createComposite failed", re);
            throw re;
        }
    }

    @Override
    public Composite getComposite(int productId) {
        Product product = integration.getProduct(productId);
        if(product == null) throw new NotFoundException("No productId" + productId);

        List<Recommend> recommendList = integration.getRecommends(productId);
        List<Review> reviewList = integration.getReviews(productId);

        return new Composite(product.getProductId(), product.getProductName(), recommendList, reviewList);
    }

    @Override
    public void deleteComposite(int productId) {
        integration.deleteProduct(productId);
        integration.deleteRecommends(productId);
        integration.deleteReviews(productId);
    }
}
