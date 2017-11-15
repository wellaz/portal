package com.equation.utils.uploads.mimetype.validate;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Wellington
 */
public class ValidateMIMEType {

	public boolean isValidCSVFile(String file) {
		List<String> fileTypes = Arrays.asList(".csv");
		return fileTypes.stream().anyMatch(t -> file.endsWith(t));
	}

}
