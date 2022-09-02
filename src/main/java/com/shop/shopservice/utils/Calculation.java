package com.shop.shopservice.utils;

import java.text.DecimalFormat;

public class Calculation {

	public float DecimalCalculation(float value) {
		DecimalFormat df = new DecimalFormat("#.##");
		float data = Float.parseFloat(df.format(value));
		return data;
	}
}
