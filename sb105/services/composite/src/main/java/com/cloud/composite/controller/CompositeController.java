package com.cloud.composite.controller;

import com.cloud.api.controller.CompositeControllerInterface;
import com.cloud.api.dto.*;
import com.cloud.api.exception.NotFoundException;
import com.cloud.api.util.ServiceUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class CompositeController implements CompositeControllerInterface {

    private final IntegrateModule integration;
    private final ServiceUtil serviceUtil;

    @Override
    public void createComposite(Composite body) {
        try {
            Product product = new Product(body.getProductId(), body.getProductName(), null, null);
            integration.createProduct(product);

            if(body.getRecommendList() != null){
                body.getRecommendList().forEach(r -> {
                    Recommend recommend = new Recommend(body.getProductId(), r.getRecommendId(), r.getAuthor(), r.getContent(), null);
                    integration.createRecommend(recommend);
                });
            }

            if(body.getReviewList() != null){
                body.getReviewList().forEach(r -> {
                    Review review = new Review(body.getProductId(), r.getReviewId(), r.getAuthor(), r.getSubject(),r.getContent(), null);
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

        String productAddress = product.getServiceAddress();
        String recommendAddress = (recommendList != null && recommendList.size() > 0) ? recommendList.get(0).getServiceAddress() : "";
        String reviewAddress = (reviewList != null && reviewList.size() > 0) ? reviewList.get(0).getServiceAddress() : "";

        ServiceAddresses serviceAddresses = new ServiceAddresses(serviceUtil.getServiceAddress(), productAddress, recommendAddress, reviewAddress);
        return new Composite(product.getProductId(), product.getProductName(), recommendList, reviewList, serviceAddresses);
    }

    @Override
    public void deleteComposite(int productId) {
        integration.deleteProduct(productId);
        integration.deleteRecommends(productId);
        integration.deleteReviews(productId);
    }
}
