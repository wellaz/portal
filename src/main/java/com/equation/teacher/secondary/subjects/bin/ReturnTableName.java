package com.equation.teacher.secondary.subjects.bin;

import java.util.ArrayList;

import com.equation.school.secondary.subjects.bin.AllSecondarySubjectsBin;

/**
 *
 * @author Wellington
 */
public class ReturnTableName {

	ArrayList<String> subjects = AllSecondarySubjectsBin.allSubjectsArray();
	ArrayList<String> tablenames = AllSecondarySubjectsTablesBin.allSubjectsTablesArray();

	public ReturnTableName() {
		// TODO Auto-generated constructor stub
	}

	public String getTableName(String subject) {
		String tablename = null;
		int size = subjects.size();
		for (int i = 0; i < size; i++) {
			String subjectname = subjects.get(i);
			if (subjectname.equals(subject))
				tablename = tablenames.get(i);
		}
		System.out.println("Table name is: " + tablename);
		return tablename;
	}
}  