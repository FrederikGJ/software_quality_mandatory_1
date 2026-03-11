package com.fakeperson.controller;

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
class FakePersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCpr_shouldReturn200WithCprField() throws Exception {
        mockMvc.perform(get("/api/cpr"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpr").isString())
                .andExpect(jsonPath("$.cpr", matchesPattern("\\d{10}")));
    }

    @Test
    void getNameGender_shouldReturn200WithAllFields() throws Exception {
        mockMvc.perform(get("/api/name-gender"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").isString())
                .andExpect(jsonPath("$.gender", isOneOf("male", "female")));
    }

    @Test
    void getNameGenderDob_shouldReturn200WithDateField() throws Exception {
        mockMvc.perform(get("/api/name-gender-dob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateOfBirth").isString())
                .andExpect(jsonPath("$.firstName").isString());
    }

    @Test
    void getCprNameGender_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/cpr-name-gender"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpr", matchesPattern("\\d{10}")))
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.gender").isString());
    }

    @Test
    void getCprNameGenderDob_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/cpr-name-gender-dob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpr").isString())
                .andExpect(jsonPath("$.dateOfBirth").isString());
    }

    @Test
    void getAddress_shouldReturn200WithNestedFields() throws Exception {
        mockMvc.perform(get("/api/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").isString())
                .andExpect(jsonPath("$.number").isString())
                .andExpect(jsonPath("$.floor").isString())
                .andExpect(jsonPath("$.door").isString())
                .andExpect(jsonPath("$.postalCode", matchesPattern("\\d{4}")))
                .andExpect(jsonPath("$.townName").isString());
    }

    @Test
    void getPhone_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/phone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber", matchesPattern("\\d{8}")));
    }

    @Test
    void getPerson_shouldReturnFullPerson() throws Exception {
        mockMvc.perform(get("/api/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpr").isString())
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").isString())
                .andExpect(jsonPath("$.gender").isString())
                .andExpect(jsonPath("$.dateOfBirth").isString())
                .andExpect(jsonPath("$.address").isMap())
                .andExpect(jsonPath("$.phoneNumber").isString());
    }

    @Test
    void getPersonsBulk_shouldReturnRequestedCount() throws Exception {
        mockMvc.perform(get("/api/persons/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getPersonsBulk_invalidCount_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/persons/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isString());
    }
}
