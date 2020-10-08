package com.sebag.productapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebag.productapp.dtos.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void productEndpointIsReachable() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/product"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void productIsAdded() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(asJsonString(new ProductDTO("TESTSKU", "TESTNAME", 3.50)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sku").value("TESTSKU"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("TESTNAME"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(3.50));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
