package com.cloud.review.controller;


import com.cloud.api.controller.ReviewControllerInterface;
import com.cloud.api.dto.Review;
import com.cloud.api.exception.InvalidInputException;
import com.cloud.review.domain.ReviewEntity;
import com.cloud.review.domain.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ReviewController implements ReviewControllerInterface {

    private final ReviewRepository repository;
    private final ReviewMapper mapper;

    @Override
    public Review createReview(Review body) {
        try {
            ReviewEntity entity = mapper.dtoToEntity(body);
            ReviewEntity newEntity = repository.save(entity);
            log.debug("createReview: product Id {}/ review Id {}", body.getProductId(), body.getReviewId());
            return mapper.entityToDto(newEntity);
        }catch (DataIntegrityViolationException dive){
            throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() + ", Review Id: " + body.getReviewId());
        }
    }

    @Override
    public List<Review> getReviews(int productId) {
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        List<ReviewEntity> entityList = repository.findByProductId(productId);
        List<Review> list = mapper.entityListToApiList(entityList);
        log.debug("getReviews: response size: {}", list.size());
        return list;
    }

    @Override
    public void deleteReviews(int productId) {
        repository.deleteAll(repository.findByProductId(productId));
    }
}
