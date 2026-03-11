package com.fakeperson.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // --- Endpoint 1: GET /api/cpr ---

    @Test
    void getCpr_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/cpr"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpr").exists())
                .andExpect(jsonPath("$.cpr", matchesPattern("\\d{10}")));
    }

    // --- Endpoint 2: GET /api/name-gender ---

    @Test
    void getNameGender_shouldReturn200WithAllFields() throws Exception {
        mockMvc.perform(get("/api/name-gender"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").isString())
                .andExpect(jsonPath("$.gender", isOneOf("male", "female")));
    }

    // --- Endpoint 3: GET /api/name-gender-dob ---

    @Test
    void getNameGenderDob_shouldReturn200WithDob() throws Exception {
        mockMvc.perform(get("/api/name-gender-dob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").isString())
                .andExpect(jsonPath("$.gender").isString())
                .andExpect(jsonPath("$.dateOfBirth").isString());
    }

    // --- Endpoint 4: GET /api/cpr-name-gender ---

    @Test
    void getCprNameGender_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/cpr-name-gender"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpr", matchesPattern("\\d{10}")))
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").isString())
                .andExpect(jsonPath("$.gender").isString());
    }

    // --- Endpoint 5: GET /api/cpr-name-gender-dob ---

    @Test
    void getCprNameGenderDob_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/cpr-name-gender-dob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpr", matchesPattern("\\d{10}")))
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").isString())
                .andExpect(jsonPath("$.gender").isString())
                .andExpect(jsonPath("$.dateOfBirth").isString());
    }

    // --- Endpoint 6: GET /api/address ---

    @Test
    void getAddress_shouldReturn200WithAllFields() throws Exception {
        mockMvc.perform(get("/api/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").isString())
                .andExpect(jsonPath("$.number").isString())
                .andExpect(jsonPath("$.floor").isString())
                .andExpect(jsonPath("$.door").isString())
                .andExpect(jsonPath("$.postalCode", matchesPattern("\\d{4}")))
                .andExpect(jsonPath("$.townName").isString());
    }

    // --- Endpoint 7: GET /api/phone ---

    @Test
    void getPhone_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/phone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber", matchesPattern("\\d{8}")));
    }

    // --- Endpoint 8: GET /api/person ---

    @Test
    void getPerson_shouldReturn200WithAllFields() throws Exception {
        mockMvc.perform(get("/api/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpr", matchesPattern("\\d{10}")))
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").isString())
                .andExpect(jsonPath("$.gender", isOneOf("male", "female")))
                .andExpect(jsonPath("$.dateOfBirth").isString())
                .andExpect(jsonPath("$.address").isMap())
                .andExpect(jsonPath("$.address.street").isString())
                .andExpect(jsonPath("$.address.postalCode", matchesPattern("\\d{4}")))
                .andExpect(jsonPath("$.phoneNumber", matchesPattern("\\d{8}")));
    }

    // --- Endpoint 9: GET /api/persons/{count} ---

    @Test
    void getPersonsBulk_shouldReturnCorrectCount() throws Exception {
        mockMvc.perform(get("/api/persons/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].cpr").exists())
                .andExpect(jsonPath("$[0].firstName").exists())
                .andExpect(jsonPath("$[0].address").exists())
                .andExpect(jsonPath("$[0].phoneNumber").exists());
    }

    @Test
    void getPersonsBulk_min2_shouldWork() throws Exception {
        mockMvc.perform(get("/api/persons/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getPersonsBulk_max100_shouldWork() throws Exception {
        mockMvc.perform(get("/api/persons/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(100)));
    }

    @Test
    void getPersonsBulk_belowMin_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/persons/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isString());
    }

    @Test
    void getPersonsBulk_aboveMax_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/persons/101"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isString());
    }

    // --- Invalid endpoints ---

    @Test
    void invalidEndpoint_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/nonexistent"))
                .andExpect(status().isNotFound());
    }
}
