package com.equation.utils.date;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wellington
 */
public class Test {
	public static void main(String[] args) {
		List<String> arr = Arrays.asList("a", "a", "b", "a", "a");

		Set<String> printed = new HashSet<>();
		for (String s : arr) {
			if (printed.add(s)) // Set.add() also tells if the element was in
								// the Set!
				System.out.println("element: " + s + ", count: " + Collections.frequency(arr, s));
		}
	}

}
