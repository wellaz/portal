/**
 *
 * @author Wellington
 */
package com.equation.equate.utils.maximum.subjects;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Wellington
 *
 */
public class Maximum {
	public static String ZERO = "0";
	public static String ONE = "1";
	public static String TWO = "2";
	public static String THREE = "3";
	public static String FOUR = "4";
	public static String FIVE = "5";

	public static ArrayList<String> max() {
		ArrayList<String> data = new ArrayList<>();
		data.addAll(Arrays.asList(FIVE, FOUR, THREE, TWO, ONE, ZERO));
		return data;
	}
}
