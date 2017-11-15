/**
 *
 * @author Wellington
 */
package com.equation.equate.utils.isdigit;

/**
 * @author Wellington
 *
 */
public class IsItADigit {
	public boolean isDigit(String d) {
		return d.matches("\\d\\d") || d.matches("\\d");
	}

}
