package com.equation.utils.downloadable.filename;

import com.equation.utils.date.DateUtility;

/**
 *
 * @author Wellington
 */
public class Filename {

	public static String setFileName() {
		return new DateUtility().getDate().replace("-", "_");
	}
}