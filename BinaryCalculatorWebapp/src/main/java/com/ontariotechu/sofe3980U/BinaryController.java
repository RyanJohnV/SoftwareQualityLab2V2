package com.ontariotechu.sofe3980U;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BinaryController {
	@GetMapping("/")
	public String getCalculator(@RequestParam(name="operand1", required=false) String operand1, Model model) {
		model.addAttribute("operand1", operand1);
		model.addAttribute("operand1Focused", operand1 != null);
		return "calculator";
	}

	@PostMapping("/")
	public String getResult(
			@RequestParam(name="operand1") String operand1,
			@RequestParam(name="operator") String operator,
			@RequestParam(name="operand2") String operand2,
			Model model
	) {
		Binary binary1 = new Binary(operand1);
		Binary binary2 = new Binary(operand2);
		Binary result;

		switch (operator) {
			case "+":
				result = binary1.add(binary1, binary2);
				break;
			case "*":
				result = binary1.multiply(binary2);
				break;
			case "&":
				result = binary1.and(binary2);
				break;
			case "|":
				result = binary1.or(binary2);
				break;
			default:
				return "error";
		}

		model.addAttribute("operand1", operand1);
		model.addAttribute("operator", operator);
		model.addAttribute("operand2", operand2);
		model.addAttribute("result", result.getNumber());
		return "result";
	}
}