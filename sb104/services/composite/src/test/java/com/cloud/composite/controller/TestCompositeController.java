package com.cloud.composite.controller;

import com.cloud.api.dto.Product;
import com.cloud.api.dto.Recommend;
import com.cloud.api.dto.Review;
import com.cloud.api.exception.InvalidInputException;
import com.cloud.api.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebClient
public class TestCompositeController {

    private static final int PRODUCT_ID_OK = 1;
    private static final int PRODUCT_ID_NOT_FOUND = 2;
    private static final int PRODUCT_ID_INVALID = 3;

    @Autowired
    private WebTestClient client;

    @MockBean
    private  IntegrateModule integrateModule;

    @BeforeEach
    public void setUp(){
        when(integrateModule.getProduct(PRODUCT_ID_OK)).thenReturn(new Product(PRODUCT_ID_OK, "name", null));
        when(integrateModule.getRecommends(PRODUCT_ID_OK)).thenReturn(singletonList( new Recommend(PRODUCT_ID_OK, 1, "author", "content")));
        when(integrateModule.getReviews(PRODUCT_ID_OK)).thenReturn((singletonList( new Review(PRODUCT_ID_OK, 1,"author", "subject","content"))));

        // 실제 테스트 되는 것은 RestControllerExceptionHandler @RestControllerAdvice 입니다.
        when(integrateModule.getProduct(PRODUCT_ID_NOT_FOUND)).thenThrow(new NotFoundException("No productId" + PRODUCT_ID_NOT_FOUND));
        when(integrateModule.getProduct(PRODUCT_ID_INVALID)).thenThrow(new InvalidInputException("Invalid productId" + PRODUCT_ID_INVALID));
    }

    @Test
    public void composite(){
        String result = client.get()
                .uri("/composite/" + PRODUCT_ID_OK)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID_OK)
                .jsonPath("$.recommendList.length()").isEqualTo(1)
                .jsonPath("$.reviewList.length()").isEqualTo(1)
                .returnResult().toString();
        System.out.println(result);
    }

    @Test
    public void compositeNOT_FOUND(){
        String result = client.get()
                .uri("/composite/" + PRODUCT_ID_NOT_FOUND)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/composite/" + PRODUCT_ID_NOT_FOUND)
                .jsonPath("$.message").isEqualTo("No productId" + PRODUCT_ID_NOT_FOUND)
                .returnResult().toString();
        System.out.println(result);
    }

    @Test
    public void composite_INVALID(){
        String result = client.get()
                .uri("/composite/" + PRODUCT_ID_INVALID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/composite/" + PRODUCT_ID_INVALID)
                .jsonPath("$.message").isEqualTo("Invalid productId" + PRODUCT_ID_INVALID)
                .returnResult().toString();
        System.out.println(result);
    }
}
