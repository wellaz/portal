package com.equation.equate.generate.units;

public class GenerateMarkUnit {

	/**
	 * 
	 */

	public int getUnit(double mark) {
		int unit = 0;
		if (mark <= 19)
			unit = 9;
		else if (mark <= 29 && mark >= 20)
			unit = 8;
		else if (mark <= 39 && mark >= 30)
			unit = 7;
		else if (mark <= 49 && mark >= 40)
			unit = 6;
		else if (mark <= 59 && mark >= 50)
			unit = 5;
		else if (mark <= 69 && mark >= 60)
			unit = 4;
		else if (mark <= 79 && mark >= 70)
			unit = 3;
		else if (mark <= 89 && mark >= 80)
			unit = 2;
		else
			unit = 1;

		return unit;
	}

}
