package com.equation.teacher.primary.marks.tables.select;

import java.util.ArrayList;

import com.equation.utils.schoolterms.AllYearTermsBin;

/**
 *
 * @author Wellington
 */
public class ReturnPrimaryMarksTableName {

	ArrayList<String> terms = AllYearTermsBin.allTermsArray();
	ArrayList<String> tablenames = AllYearTermsTableNames.allPrimaryMarksTableNames();

	public ReturnPrimaryMarksTableName() {
		// TODO Auto-generated constructor stub
	}

	public String getTableName(String term) {
		String tablename = null;
		int size = terms.size();
		for (int i = 0; i < size; i++) {
			String termname = terms.get(i);
			if (termname.equals(term))
				tablename = tablenames.get(i);
		}
		return tablename;
	}
}