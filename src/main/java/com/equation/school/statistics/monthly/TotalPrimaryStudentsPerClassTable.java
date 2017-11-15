package com.equation.school.statistics.monthly;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.util.gender.Gender;
import com.equation.utils.primary.grades.AllPrimaryGrades;
import com.equation.utils.student.status.StudentStatus;
import com.vaadin.v7.ui.Table;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class TotalPrimaryStudentsPerClassTable {
	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;

	public TotalPrimaryStudentsPerClassTable(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt,
			String schoolID) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public Table getPrimaryStatisticsTable() {
		Table table = new Table("ENROLMENT BY GENDER AND GRADE");
		ArrayList<String> grades = AllPrimaryGrades.allGrades();
		table.addContainerProperty("GRADE", String.class, null);
		for (String header : grades)
			table.addContainerProperty(header, Integer.class, null);
		table.addContainerProperty("TOTALS 1", Integer.class, null);
		table.addContainerProperty("RU", Integer.class, null);
		table.addContainerProperty("SPECL", Integer.class, null);
		String status = StudentStatus.ACTIVE;

		ArrayList<String> ecds = AllPrimaryGrades.allECD();
		for (String ecd : ecds)
			table.addContainerProperty(ecd, Integer.class, null);

		table.addContainerProperty("TOTALS 2", Integer.class, null);

		ArrayList<Integer> onetosevenmales = new TotalPrimaryStudentsPerClass(stm, stmt, rs, rs1, schoolID)
				.getOneToSevenTotals(grades, Gender.MALE, status);
		int onetosevenmalestotal = 0;
		for (int i : onetosevenmales)
			onetosevenmalestotal += i;

		ArrayList<Integer> onetosevenfemales = new TotalPrimaryStudentsPerClass(stm, stmt, rs, rs1, schoolID)
				.getOneToSevenTotals(grades, Gender.FEMALE, status);
		int onetosevenfemalestotat = 0;
		for (int i : onetosevenfemales)
			onetosevenfemalestotat += i;
		ArrayList<Integer> onetosevenclasstotals = new ArrayList<>();
		int size = onetosevenmales.size();
		for (int i = 0; i < size; i++) {
			int totalmales = onetosevenmales.get(i);
			int totalfemales = onetosevenfemales.get(i);
			int sum = totalmales + totalfemales;
			onetosevenclasstotals.add(sum);
		}

		int onetosevenclasstotalstotal = 0;
		for (int i : onetosevenclasstotals)
			onetosevenclasstotalstotal += i;

		ArrayList<Integer> ecdamales = new TotalPrimaryStudentsPerClass(stm, stmt, rs, rs1, schoolID)
				.getOneToSevenTotals(ecds, Gender.MALE, status);
		int ecdamalestotal = 0;
		for (int i : ecdamales)
			ecdamalestotal += i;
		ArrayList<Integer> ecdafemales = new TotalPrimaryStudentsPerClass(stm, stmt, rs, rs1, schoolID)
				.getOneToSevenTotals(ecds, Gender.FEMALE, status);
		int ecdafemalestottal = 0;
		for (int i : ecdafemales)
			ecdafemalestottal += i;

		int ecdatotal = ecdamalestotal + ecdafemalestottal;

		ArrayList<Integer> ecdbmales = new TotalPrimaryStudentsPerClass(stm, stmt, rs, rs1, schoolID)
				.getOneToSevenTotals(ecds, Gender.MALE, status);
		int ecdbmalestotal = 0;
		for (int i : ecdbmales)
			ecdbmalestotal += i;
		ArrayList<Integer> ecdbfemales = new TotalPrimaryStudentsPerClass(stm, stmt, rs, rs1, schoolID)
				.getOneToSevenTotals(ecds, Gender.FEMALE, status);
		int ecdbfemalestotal = 0;
		for (int i : ecdbfemales)
			ecdbfemalestotal += i;

		int ecdbtotal = ecdbmalestotal + ecdbfemalestotal;

		String male = Gender.MALE, female = Gender.FEMALE, totalNarration = "TOTAL";
		table.addItem(new Object[] { male, onetosevenmales.get(0), onetosevenmales.get(1), onetosevenmales.get(2),
				onetosevenmales.get(3), onetosevenmales.get(4), onetosevenmales.get(5), onetosevenmales.get(6),
				onetosevenmalestotal, 0, 0, ecdamales.get(0), ecdbmales.get(0), (ecdamalestotal + ecdbmalestotal) }, 0);

		table.addItem(new Object[] { female, onetosevenfemales.get(0), onetosevenfemales.get(1),
				onetosevenfemales.get(2), onetosevenfemales.get(3), onetosevenfemales.get(4), onetosevenfemales.get(5),
				onetosevenfemales.get(6), onetosevenfemalestotat, 0, 0, ecdafemales.get(0), ecdbfemales.get(0),
				(ecdafemalestottal + ecdbfemalestotal) }, 1);

		table.addItem(new Object[] { totalNarration, onetosevenclasstotals.get(0), onetosevenclasstotals.get(1),
				onetosevenclasstotals.get(2), onetosevenclasstotals.get(3), onetosevenclasstotals.get(4),
				onetosevenclasstotals.get(5), onetosevenclasstotals.get(6), onetosevenclasstotalstotal, 0, 0, ecdatotal,
				ecdbtotal, (ecdatotal + ecdbtotal) }, 2);

		int size1 = table.size();
		int totalEnrollment = ecdatotal + ecdbtotal + onetosevenclasstotalstotal;

		table.setFooterVisible(true);
		table.setSelectable(true);
		table.setPageLength(size1);
		table.setSizeFull();
		table.setColumnCollapsingAllowed(true);

		table.setColumnFooter("GRADE", "Total Enrollment: " + String.valueOf(totalEnrollment));

		return table;
	}
}