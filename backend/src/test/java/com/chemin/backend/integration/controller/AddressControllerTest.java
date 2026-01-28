package com.chemin.backend.integration.controller;

import com.chemin.backend.BackendApplication;
import com.chemin.backend.dto.request.CreateAddressRequest;
import com.chemin.backend.repository.AddressRepository;
import com.chemin.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureMockMvc
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository createAddressResponse;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void CreateAddress_shouldReturnResponseEntity_whenSuccess() throws Exception {
        // Given

        CreateAddressRequest createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setUserId(1L);
        createAddressRequest.setRecipientName("John Doe");
        createAddressRequest.setPhone("1234567890");
        createAddressRequest.setCity("Test City");
        createAddressRequest.setDistrict("Test District");
        createAddressRequest.setDetailAddress("123 Test St");
        createAddressRequest.setIsDefault(true);

        // When & Then
        mockMvc.perform(post("/api/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAddressRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipientName").value("John Doe"))
                .andExpect(jsonPath("$.phone").value("1234567890"))
                .andExpect(jsonPath("$.province").value("Test Province"))
                .andExpect(jsonPath("$.city").value("Test City"))
                .andExpect(jsonPath("$.district").value("Test District"))
                .andExpect(jsonPath("$.detailAddress").value("123 Test St"))
                .andExpect(jsonPath("$.isDefault").value(true));
    }
}
