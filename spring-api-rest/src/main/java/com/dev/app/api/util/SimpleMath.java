package com.dev.app.api.util;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class SimpleMath {
	
	public static Double sum(Double numberOne, Double numberTwo) {
		BinaryOperator<Double> binaryOperator = (n1, n2) -> {
			return n1 + n2;
		};
		return applyFunction(numberOne, numberTwo, binaryOperator);
	}
	
	public static Double subtraction(Double numberOne, Double numberTwo) {
		BinaryOperator<Double> binaryOperator = (n1, n2) -> n1 - n2;
		return applyFunction(numberOne, numberTwo, binaryOperator);
	}
	
	public static Double multiplication(Double numberOne, Double numberTwo) {
		BinaryOperator<Double> binaryOperator = (n1, n2) -> n1 * n2;
		return applyFunction(numberOne, numberTwo, binaryOperator);
	}
	
	public static Double division(Double numberOne, Double numberTwo) {
		BinaryOperator<Double> binaryOperator = (n1, n2) -> n1 / n2;
		return applyFunction(numberOne, numberTwo, binaryOperator);
	}
	
	public static Double main(Double numberOne, Double numberTwo) {
		BinaryOperator<Double> binaryOperator = (n1, n2) -> (n1 + n2) / 2;
		return applyFunction(numberOne, numberTwo, binaryOperator);
	}
	
	public static Double squareRoot(Double number) {
		UnaryOperator<Double> unaryOperator = Math::sqrt;
		return applyUniOperator(number, unaryOperator);
	}
	
	static <T> T applyFunction(T t1, T t2, BinaryOperator<T> binaryOperator) {
		return binaryOperator.apply(t1, t2);
	}
	
	static <T> T applyUniOperator(T t, UnaryOperator<T> unaryOperator) {
		return unaryOperator.apply(t);
	}
}