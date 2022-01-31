package com.oguzhan.rig;

import com.oguzhan.rig.controller.CustomerController;
import com.oguzhan.rig.dto.CustomerDto;
import com.oguzhan.rig.security.WebSecurityConfig;
import com.oguzhan.rig.service.CustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;


    private CustomerDto customer;
    private String token;


    @BeforeAll
    void createTestData() throws Exception{
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John");
        customerDto.setSurname("Doe");
        customerDto.setEmail("ordertest@gmail.com");
        customerDto.setPassword("password");
        this.customer = customerService.createCustomer(customerDto);

        String username = "ordertest@gmail.com";
        String password = "password";

        String body = "{\"username\":\"" + username + "\", \"password\":\""
                + password + "\"}";
        System.out.println(body);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        response = response.replace("{\"token\":\"", "");
        token = response.replace("\"}", "");

    }

    @Test
    public void get_customer_thenStatus200()
            throws Exception {
        System.out.println(this.token);
        mvc.perform(get("/api/v1/Customer/"+this.customer.getId().intValue())
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("John")));
    }


}
