package com.springbootbackendMicrosrevices.Productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootbackendMicrosrevices.Productservice.dto.ProductRequest;
import com.springbootbackendMicrosrevices.Productservice.dto.ProductResponse;
import com.springbootbackendMicrosrevices.Productservice.model.Product;
import com.springbootbackendMicrosrevices.Productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {



	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	static {
		mongoDBContainer.start();
	}

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;


	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}


	private ProductRequest getProductRequest(){
		return ProductRequest.builder().name("i-phone14")
				.description("i phone 14 pro max")
				.price(BigDecimal.valueOf(701)).build();
	}

    @Test
    void shouldGettheProduct() throws Exception {
        ProductResponse productResponse = getProductResposnse(Product.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product").contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(productResponse)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private ProductResponse getProductResposnse(Product product){
        return ProductResponse.builder().id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice()).build();

    }


}
