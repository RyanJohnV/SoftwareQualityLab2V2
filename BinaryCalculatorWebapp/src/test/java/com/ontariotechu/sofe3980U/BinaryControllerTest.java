package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;



import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
    }
	
	    @Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
    }
	@Test
	    public void postParameter() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1110"))
			.andExpect(model().attribute("operand1", "111"));
    }



    //Test if calculator page is accessible; displays correct initial state
    @Test
    public void testCalculatorPageLoads() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isOk()) // Check if the page loads successfully
                .andExpect(view().name("calculator")) // Check if the correct view is returned
                .andExpect(model().attributeExists("operand1")) // Check if operand1 attribute exists
                .andExpect(model().attributeExists("operand1Focused")); // Check if operand1Focused attribute exists
    }

    //Test addition with valid numbers
    @Test
    public void testAdditionWithValidInputs() throws Exception {
        this.mvc.perform(post("/")
                        .param("operand1", "1010") // Binary for 10
                        .param("operator", "+")
                        .param("operand2", "1100")) // Binary for 12
                .andExpect(status().isOk()) // Check if the request is successful
                .andExpect(view().name("result")) // Check if the correct view is returned
                .andExpect(model().attribute("result", "10110")); // Binary for 22 (10 + 12)
    }

    //Test addition with invalid inputs
    @Test
    public void testAdditionWithInvalidInputs() throws Exception {
        this.mvc.perform(post("/")
                        .param("operand1", "1010") // Binary for 10
                        .param("operator", "+")
                        .param("operand2", "1234")) // Invalid binary input
                .andExpect(status().isOk()) // Check if the request is successful
                .andExpect(view().name("error")); // Check if the error view is returned
    }

    @Test//Binary Multiply
    public void testMultiply() {
        Binary binary1 = new Binary("1010"); // 10 in decimal
        Binary binary2 = new Binary("1100"); // 12 in decimal
        Binary result = binary1.multiply(binary2);
        assertEquals("1111000", result.getNumber()); // 120 in binary
    }

    @Test//Binary And
    public void testAnd() {
        Binary binary1 = new Binary("1010"); // 10 in decimal
        Binary binary2 = new Binary("1100"); // 12 in decimal
        Binary result = binary1.and(binary2);
        assertEquals("1000", result.getNumber()); // 8 in binary
    }

    @Test//Binary Or
    public void testOr() {
        Binary binary1 = new Binary("1010"); // 10 in decimal
        Binary binary2 = new Binary("1100"); // 12 in decimal
        Binary result = binary1.or(binary2);
        assertEquals("1110", result.getNumber()); // 14 in binary
    }







}