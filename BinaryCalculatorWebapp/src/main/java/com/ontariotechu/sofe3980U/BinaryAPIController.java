package com.ontariotechu.sofe3980U;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BinaryAPIController {
	@GetMapping("/add")
	public String add(@RequestParam(name="operand1") String operand1, @RequestParam(name="operand2") String operand2) {
		Binary binary1 = new Binary(operand1);
		Binary binary2 = new Binary(operand2);
		return (String) binary1.add(binary1, binary2).getNumber();
	}

	@GetMapping("/multiply")
	public String multiply(@RequestParam(name="operand1") String operand1, @RequestParam(name="operand2") String operand2) {
		Binary binary1 = new Binary(operand1);
		Binary binary2 = new Binary(operand2);
		return (String) binary1.multiply(binary2).getNumber();
	}

	@GetMapping("/and")
	public String and(@RequestParam(name="operand1") String operand1, @RequestParam(name="operand2") String operand2) {
		Binary binary1 = new Binary(operand1);
		Binary binary2 = new Binary(operand2);
		return (String) binary1.and(binary2).getNumber();
	}

	@GetMapping("/or")
	public String or(@RequestParam(name="operand1") String operand1, @RequestParam(name="operand2") String operand2) {
		Binary binary1 = new Binary(operand1);
		Binary binary2 = new Binary(operand2);
		return (String) binary1.or(binary2).getNumber();
	}
}