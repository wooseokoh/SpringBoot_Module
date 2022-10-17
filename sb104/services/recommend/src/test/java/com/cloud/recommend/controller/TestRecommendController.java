package com.cloud.recommend.controller;

import com.cloud.api.dto.Product;
import com.cloud.api.dto.Recommend;
import com.cloud.recommend.domain.RecommendRepository;
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
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.data.mongodb.port: 0"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebClient
public class TestRecommendController {

    @Autowired
    private WebTestClient client;

    @Autowired
    private RecommendRepository repository;

    @BeforeEach
    public void setUp(){
        repository.deleteAll();
    }

    private WebTestClient.BodyContentSpec getRecommends(String query, HttpStatus expectedStatus){
        return client.get()
                .uri("/recommend" + query)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec addRecommend(int productId, int recommendId, HttpStatus expectedStatus){
        Recommend recommend = new Recommend(productId, recommendId, "Author" + recommendId, "Content" + recommendId);
        WebTestClient.BodyContentSpec result =
                client.post().uri("/recommend")
                        .body(Mono.just(recommend), Recommend.class)
                        .accept(APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isEqualTo(expectedStatus)
                        .expectHeader().contentType(APPLICATION_JSON)
                        .expectBody();
        return result;
    }

    private WebTestClient.BodyContentSpec deleteRecommend(int productId, HttpStatus expectedStatus){
        return client.delete()
                .uri("/recommend?productId=" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    @Test
    @Order(1)
    public void _getRecommends(){
        int productId = 1;
        addRecommend(productId, 1, OK);
        addRecommend(productId, 2, OK);
        addRecommend(productId, 3, OK);
        Assertions.assertEquals(3, repository.findByProductId(productId).size());
        getRecommends("?productId=" + String.valueOf(productId), OK)
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[2].productId").isEqualTo(productId)
                .jsonPath("$[2].recommendId").isEqualTo(3);
    }

    @Test
    @Order(2)
    public void _duplicateRecommendError(){
        int productId = 1;
        int recommendId = 1;
        addRecommend(productId, recommendId, OK)
                .jsonPath("$.productId").isEqualTo(productId)
                .jsonPath("$.recommendId").isEqualTo(recommendId);

        Assertions.assertEquals(1, repository.count());

        addRecommend(productId, recommendId, UNPROCESSABLE_ENTITY)
                .jsonPath("$.path").isEqualTo("/recommend")
                .jsonPath("$.message").isEqualTo("Duplicate key, Product Id: 1, Recommend Id: 1");
        Assertions.assertEquals(1, repository.count());
    }

    @Test
    @Order(3)
    public void _deleteRecommends(){
        int productId = 1;
        int recommendId = 1;
        addRecommend(productId, recommendId, OK);
        Assertions.assertEquals(1, repository.findByProductId(productId).size());

        deleteRecommend(productId, OK);
        Assertions.assertEquals(0, repository.findByProductId(productId).size());
    }

    @Test
    @Order(4)
    public void _setRecommendsMissingParameter(){
        getRecommends("", BAD_REQUEST)
                .jsonPath("$.path").isEqualTo("/recommend")
                .jsonPath("$.message").isEqualTo("Required int parameter 'productId' is not present");
    }

    @Test
    @Order(5)
    public void _setRecommendsInvalidParameter(){
        getRecommends("?productId=", BAD_REQUEST)
                .jsonPath("$.path").isEqualTo("/recommend")
                .jsonPath("$.message").isEqualTo("Type mismatch.");
    }

    @Test
    @Order(6)
    public void _getRecommendNotFound(){
        getRecommends("?productId="+ String.valueOf(1004), OK)
                .jsonPath("$.length()").isEqualTo(0);
    }
}
