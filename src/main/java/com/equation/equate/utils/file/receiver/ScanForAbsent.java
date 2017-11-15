/**
 *
 * @author Wellington
 */
package com.equation.equate.utils.file.receiver;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.equate.absent.InsertAbsentStudents;
import com.equation.equate.adjustedmark.GetAdjustedMark;
import com.equation.equate.insertdata.InsertDataIntoMarkSchedule;
import com.equation.equate.settings.passmark.PassMark;
import com.equation.equate.subjects.passed.record.RecordSubjectsPassed;
import com.equation.equate.utils.tablename.Tablename;

/**
 * @author Wellington
 *
 */
public class ScanForAbsent {
	Statement stm, stmt;
	ResultSet rs, rs1;
	InsertAbsentStudents absentStudents;
	InsertDataIntoMarkSchedule insertDataIntoMarkSchedule;
	RecordSubjectsPassed passed;

	public ScanForAbsent(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
		absentStudents = new InsertAbsentStudents(this.stm);
		insertDataIntoMarkSchedule = new InsertDataIntoMarkSchedule();
		passed = new RecordSubjectsPassed();
	}

	public void doScanFor(String subjectname, String class_name, String value1, String value2, int out_of1,
			int contribution1, int out_of2, int contribution2, String student_name, int lines, ArrayList<String> data,
			String term, String year, String date) {
		int passMark = new PassMark(rs, rs1, stm, stmt).getPassMark();
		if (value1.equalsIgnoreCase("x") && !value2.equalsIgnoreCase("x")) {
			if (Integer.parseInt(value2) <= out_of2) {
				insertDataIntoMarkSchedule.insertData(Tablename.PAPER_ONE, class_name, subjectname,
						Integer.toString(out_of1), Integer.toString(contribution1), student_name, "0", "0", stm, term,
						year, date);
				int student_english_p2 = GetAdjustedMark.getAdjustedMark(Integer.parseInt(value2), out_of2,
						contribution2);
				insertDataIntoMarkSchedule.insertData(Tablename.PAPER_TWO, class_name, subjectname,
						Integer.toString(out_of2), Integer.toString(contribution2), student_name, value2,
						Integer.toString(student_english_p2), stm, term, year, date);
				absentStudents.insertData(student_name, class_name, subjectname, "1", term, year, date);

			} else {
				// english paper 2 is out of bound
				data.add(student_name + " At line " + (lines + 1) + ". " + subjectname
						+ "  Paper 2 Mark is out of Bounds");

			}

		} else if (value2.equalsIgnoreCase("x") && !value1.equalsIgnoreCase("")) {
			if (Integer.parseInt(value1) <= out_of1) {
				int student_english_p1 = GetAdjustedMark.getAdjustedMark(Integer.parseInt(value1), out_of1,
						contribution1);
				insertDataIntoMarkSchedule.insertData(Tablename.PAPER_ONE, class_name, subjectname,
						Integer.toString(out_of1), Integer.toString(contribution1), student_name, value1,
						Integer.toString(student_english_p1), stm, term, year, date);
				insertDataIntoMarkSchedule.insertData(Tablename.PAPER_TWO, class_name, subjectname,
						Integer.toString(out_of2), Integer.toString(contribution2), student_name, "0", "0", stm, term,
						year, date);
				absentStudents.insertData(student_name, class_name, subjectname, "2", term, year, date);

			} else {
				// english paper one is out of boundd
				data.add(student_name + " At line " + (lines + 1) + ". " + subjectname
						+ "  Paper 1 Mark is out of Bounds");

			}

		} else if (value1.equalsIgnoreCase("x") && value2.equalsIgnoreCase("x")) {
			insertDataIntoMarkSchedule.insertData(Tablename.PAPER_ONE, class_name, subjectname,
					Integer.toString(out_of1), Integer.toString(contribution1), student_name, "0", "0", stm, term, year,
					date);
			insertDataIntoMarkSchedule.insertData(Tablename.PAPER_TWO, class_name, subjectname,
					Integer.toString(out_of2), Integer.toString(contribution2), student_name, "0", "0", stm, term, year,
					date);
			absentStudents.insertData(student_name, class_name, subjectname, "1", term, year, date);
			absentStudents.insertData(student_name, class_name, subjectname, "2", term, year, date);

		} else {
			if (Integer.parseInt(value1) <= out_of1 && Integer.parseInt(value2) <= out_of2) {
				int student_english_p1 = GetAdjustedMark.getAdjustedMark(Integer.parseInt(value1), out_of1,
						contribution1);

				int student_english_p2 = GetAdjustedMark.getAdjustedMark(Integer.parseInt(value2), out_of2,
						contribution2);
				insertDataIntoMarkSchedule.insertData(Tablename.PAPER_ONE, class_name, subjectname,
						Integer.toString(out_of1), Integer.toString(contribution1), student_name, value1,
						Integer.toString(student_english_p1), stm, term, year, date);

				insertDataIntoMarkSchedule.insertData(Tablename.PAPER_TWO, class_name, subjectname,
						Integer.toString(out_of2), Integer.toString(contribution2), student_name, value2,
						Integer.toString(student_english_p2), stm, term, year, date);
				if ((student_english_p1 + student_english_p2) >= passMark)
					passed.recordPass(stm, student_name, subjectname, class_name, term, year, date);

			} else {
				// one english mark is out of bound
				data.add(student_name + " At line " + (lines + 1) + ". " + subjectname + " Mark is out of Bounds. "
						+ subjectname + " was not posted!");
			}

		}
	}
}