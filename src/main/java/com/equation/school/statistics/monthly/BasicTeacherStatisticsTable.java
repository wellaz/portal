package com.equation.school.statistics.monthly;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.teacher.categories.TeacherCategory;
import com.equation.teacher.status.TeacherStatus;
import com.equation.util.gender.Gender;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class BasicTeacherStatisticsTable {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;

	public BasicTeacherStatisticsTable(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, String schoolID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public Table basicTeachersTable() {
		Table table = new Table("NUMBER OF ALL TEACHERS AT THE SCHOOL INCLUDING THOSE ON LEAVE");
		TeacherStatistics teacherStatistics = new TeacherStatistics(stm, stmt, rs, rs1);

		int traidedMaleTeachers = teacherStatistics.getTeachersCount(Gender.MALE, TeacherCategory.TRAINED,
				TeacherStatus.TRANSFERRED, schoolID);
		int traindFemaleTeachers = teacherStatistics.getTeachersCount(Gender.FEMALE, TeacherCategory.TRAINED,
				TeacherStatus.TRANSFERRED, schoolID);
		int totalTrained = traidedMaleTeachers + traindFemaleTeachers;

		int collegeMaleTeachers = teacherStatistics.getTeachersCount(Gender.MALE, TeacherCategory.COLLEGE,
				TeacherStatus.TRANSFERRED, schoolID);
		int collegeFemaleTeachers = teacherStatistics.getTeachersCount(Gender.FEMALE, TeacherCategory.COLLEGE,
				TeacherStatus.TRANSFERRED, schoolID);
		int totalCollege = collegeMaleTeachers + collegeFemaleTeachers;

		int zintecMaleTeachers = teacherStatistics.getTeachersCount(Gender.MALE, TeacherCategory.ZINTEC,
				TeacherStatus.TRANSFERRED, schoolID);
		int zintecFemaleTeachers = teacherStatistics.getTeachersCount(Gender.FEMALE, TeacherCategory.ZINTEC,
				TeacherStatus.TRANSFERRED, schoolID);
		int totalZintec = zintecMaleTeachers + zintecFemaleTeachers;

		int untrainedMaleTeachers = teacherStatistics.getTeachersCount(Gender.MALE, TeacherCategory.UNTRAINED,
				TeacherStatus.TRANSFERRED, schoolID);
		int untrainedFemaleTeachers = teacherStatistics.getTeachersCount(Gender.FEMALE, TeacherCategory.UNTRAINED,
				TeacherStatus.TRANSFERRED, schoolID);
		int totalUntrained = untrainedMaleTeachers + untrainedFemaleTeachers;

		int totalMaleTeachers = traidedMaleTeachers + collegeMaleTeachers + zintecMaleTeachers + untrainedMaleTeachers;
		int totalFemaleTeachers = traindFemaleTeachers + collegeFemaleTeachers + zintecFemaleTeachers
				+ untrainedFemaleTeachers;
		int totalNumberOfAllTeachers = totalTrained + totalCollege + totalZintec + totalUntrained;

		table.addContainerProperty("Gender", String.class, null);
		table.addContainerProperty("TRAINED TEACHERS", Integer.class, null);
		table.addContainerProperty("COLLEGE STUDENTS", Integer.class, null);
		table.addContainerProperty("ZINTEC STUDENTS", Integer.class, null);
		table.addContainerProperty("UNTRAINED TEACHERS", Integer.class, null);
		table.addContainerProperty("TOTALS", Integer.class, null);

		table.addItem(new Object[] { Gender.MALE.toUpperCase(), traidedMaleTeachers, collegeMaleTeachers,
				zintecMaleTeachers, untrainedMaleTeachers, totalMaleTeachers }, 0);
		table.addItem(new Object[] { Gender.FEMALE.toUpperCase(), traindFemaleTeachers, collegeFemaleTeachers,
				zintecFemaleTeachers, untrainedFemaleTeachers, totalFemaleTeachers }, 1);
		table.addItem(new Object[] { "total".toUpperCase(), totalTrained, totalCollege, totalZintec, totalUntrained,
				totalNumberOfAllTeachers }, 2);

		table.setFooterVisible(true);
		table.setColumnCollapsingAllowed(true);
		int size = table.size();
		table.setPageLength(size);
		table.setSelectable(true);
		table.setSizeFull();

		return table;
	}

}
