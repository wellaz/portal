package com.equation.utils.date;

import java.util.ArrayList;

import com.equation.utils.schoolterms.AllYearTermsBin;

/**
 *
 * @author Wellington
 */
public class AllTerms {

	public AllTerms() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> allTerms() {
		ArrayList<String> list = AllYearTermsBin.allTermsArray();

		return list;
	}

	public int currentTerm() {
		int currentTerm = 0;
		int currentMonth = new DateUtility().getMonth();
		if (currentMonth <= 4)
			currentTerm = 0;
		else if (currentMonth > 4 && currentMonth <= 8)
			currentTerm = 1;
		else
			currentTerm = 2;
		return currentTerm;
	}

	public String thisTerm() {
		String currentTerm = null;
		int currentMonth = new DateUtility().getMonth();
		if (currentMonth <= 4)
			currentTerm = allTerms().get(0);
		else if (currentMonth > 4 && currentMonth <= 8)
			currentTerm = allTerms().get(1);
		else
			currentTerm = allTerms().get(2);
		return currentTerm;
	}
	public String lastTerm(String term, int year) {
		String returnValue = null;
		ArrayList<String> array = AllYearTermsBin.allTermsArray();

		// String nextterm;

		int newyear;
		switch (term) {

		case "Term One": {
			newyear = year-1;
			returnValue = array.get(2) + "," + newyear;

			break;
		}
		case "Term Two": {
			newyear = year;
			returnValue = array.get(0) + "," + newyear;
			break;
		}
		case "Term Three": {
			newyear = year;
			returnValue = array.get(1) + "," + newyear;
			break;
		}

		}
		return returnValue;
		 
	}
}