package com.cloud.review.controller;

import com.cloud.api.dto.Recommend;
import com.cloud.api.dto.Review;
import com.cloud.review.domain.ReviewRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:review-db", "eureka.client.enabled=false"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebClient
public class TestReviewController {

    @Autowired
    private WebTestClient client;

    @Autowired
    private ReviewRepository repository;

    @BeforeEach
    public void setUp(){
        repository.deleteAll();
    }

    private WebTestClient.BodyContentSpec getReviews(String query, HttpStatus expectedStatus){
        return client.get()
                .uri("/review" + query)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec addReviews(String productId, int reviewId, HttpStatus expectedStatus){
        Review review = new Review(Integer.parseInt(productId), reviewId, "Author" + reviewId, "Subject" + reviewId, "Content" + reviewId);
        WebTestClient.BodyContentSpec result =
                client.post().uri("/review")
                        .body(Mono.just(review), Review.class)
                        .accept(APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isEqualTo(expectedStatus)
                        .expectHeader().contentType(APPLICATION_JSON)
                        .expectBody();
        return result;
    }

    private WebTestClient.BodyContentSpec deleteReviews(int productId, HttpStatus expectedStatus){
        return client.delete()
                .uri("/review?productId=" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    @Test
    @Order(1)
    public void _getReviews(){
        int productId = 1;
        Assertions.assertEquals(0, repository.findByProductId(productId).size());
        addReviews(String.valueOf(productId), 1, OK);
        addReviews(String.valueOf(productId), 2, OK);
        addReviews(String.valueOf(productId), 3, OK);
        Assertions.assertEquals(3, repository.findByProductId(productId).size());
        getReviews("?productId=" + String.valueOf(productId), OK)
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[2].productId").isEqualTo(productId)
                .jsonPath("$[2].reviewId").isEqualTo(3);
    }

    @Test
    @Order(2)
    public void _duplicateReivewError(){
        int productId = 1;
        int reviewId = 1;

        Assertions.assertEquals(0, repository.count());

        addReviews(String.valueOf(productId), reviewId, OK)
                .jsonPath("$.productId").isEqualTo(productId)
                .jsonPath("$.reviewId").isEqualTo(reviewId);

        Assertions.assertEquals(1, repository.count());

        addReviews(String.valueOf(productId), reviewId, UNPROCESSABLE_ENTITY)
                .jsonPath("$.path").isEqualTo("/review")
                .jsonPath("$.message").isEqualTo("Duplicate key, Product Id: 1, Review Id: 1");
        Assertions.assertEquals(1, repository.count());
    }

    @Test
    @Order(3)
    public void _deleteReviews(){
        int productId = 1;
        int reviewId = 1;
        addReviews(String.valueOf(productId), reviewId, OK);
        Assertions.assertEquals(1, repository.findByProductId(productId).size());

        deleteReviews(productId, OK);
        Assertions.assertEquals(0, repository.findByProductId(productId).size());

        deleteReviews(productId, OK);
    }
}
