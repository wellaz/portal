package com.equation.equate.utils.file.mimetype;

import java.util.Arrays;
import java.util.List;

public class ValidateFileMimeType {

	public boolean isValidCSVFile(String file) {
		List<String> fileTypes = Arrays.asList(".csv");
		return fileTypes.stream().anyMatch(t -> file.endsWith(t));
	}
}
