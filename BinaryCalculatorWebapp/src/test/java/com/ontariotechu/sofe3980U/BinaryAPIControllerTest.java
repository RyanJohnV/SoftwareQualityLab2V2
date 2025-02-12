package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@RunWith(SpringRunner.class)
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;

    @GetMapping("/add")
    public String add(@RequestParam(name="operand1") String operand1, @RequestParam(name="operand2") String operand2) {
        Binary binary1 = new Binary(operand1);
        Binary binary2 = new Binary(operand2);
        return (String) binary1.add(binary1, binary2).getNumber();
    }

    private boolean isValidBinary(String input) {
        return input.matches("[01]+");
    }

    @GetMapping("/add_json")
    public APIResult addJson(@RequestParam(name="operand1") String operand1, @RequestParam(name="operand2") String operand2) {
        Binary binary1 = new Binary(operand1);
        Binary binary2 = new Binary(operand2);
        return new APIResult(operand1, operand2);
    }

   
    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("10001"));
    }
	@Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

    @Test//returns correct result for valid inputs
    public void testAddAPIWithValidInputs() throws Exception {
        this.mvc.perform(get("/add")
                        .param("operand1", "1010") // Binary for 10
                        .param("operand2", "1100")) // Binary for 12
                .andExpect(status().isOk()) // Check if the request is successful
                .andExpect(content().string("10110")); // Binary for 22 (10 + 12)
    }

    //Test handling inputs
    @Test
    public void testAddAPIWithInvalidInputs() throws Exception {
        this.mvc.perform(get("/add")
                        .param("operand1", "1010") // Binary for 10
                        .param("operand2", "1234")) // Invalid binary input
                .andExpect(status().isBadRequest()); // Check if the request fails with a 400 error
    }

    //Test add_json endpoint returns correct
    @Test
    public void testAddJsonAPIWithValidInputs() throws Exception {
        this.mvc.perform(get("/add_json")
                        .param("operand1", "1010") // Binary for 10
                        .param("operand2", "1100")) // Binary for 12
                .andExpect(status().isOk()) // Check if the request is successful
                .andExpect(jsonPath("$.operand1").value("1010")) // Check operand1 in JSON response
                .andExpect(jsonPath("$.operand2").value("1100")) // Check operand2 in JSON response
                .andExpect(jsonPath("$.result").value("10110")); // Check result in JSON response
    }




}