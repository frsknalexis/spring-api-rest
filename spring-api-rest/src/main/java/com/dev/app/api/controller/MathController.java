package com.dev.app.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dev.app.api.controller.exception.UnsupportedMathOperationException;
import com.dev.app.api.util.NumberConverter;
import com.dev.app.api.util.SimpleMath;

@RestController
public class MathController {

	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(@PathVariable(value = "numberOne") String numberOne, 
					@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
			throw new UnsupportedMathOperationException("Porfavor, Ingrese un numero valido");
		return SimpleMath.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double subtraction(@PathVariable(value = "numberOne") String numberOne,
								@PathVariable(value = "numberTwo") String numberTwo) {
		if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		return SimpleMath.subtraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double multiplication(@PathVariable(value = "numberOne") String numberOne, 
									@PathVariable(value = "numberTwo") String numberTwo) {
		if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		return SimpleMath.multiplication(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double division(@PathVariable(value = "numberOne") String numberOne, 
							@PathVariable(value = "numberTwo") String numberTwo) {
		if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		return SimpleMath.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/main/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double main(@PathVariable(value = "numberOne") String numberOne, 
						@PathVariable(value = "numberTwo") String numberTwo) {
		if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		return SimpleMath.main(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/squareRoot/{number}", method = RequestMethod.GET)
	public Double squareRoot(@PathVariable(value = "number") String number) {
		if (!NumberConverter.isNumeric(number))
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		return SimpleMath.squareRoot(NumberConverter.convertToDouble(number));
	}
}