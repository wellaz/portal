package com.equation.system.users.login;

import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.accounts.administrator.receiptsbooks.RemindAdministratorForReceiptLeft;
import com.equation.administrator.users.create.SystemUserLevels;
import com.equation.checkin.teachers.CheckInView;
import com.equation.student.currentclass.retrieve.GetStudentClass;
import com.equation.student.name.retrieve.RetrieveStudentName;
import com.equation.system.users.AdministratorMainView;
import com.equation.system.users.BursarMainView;
import com.equation.system.users.Student;
import com.equation.system.users.TeacherMainView;
import com.vaadin.ui.UI;

/**
 *
 * @author Wellington
 */
public class UserLevelSelector {
	Statement stm, stmt, stmt1;
	ResultSet rs, rs1;

	protected static final String STUDENT = "student";

	protected static final String ADMINISTRATOR = "administrator";
	protected static final String TEACHER = "teacher";
	protected static final String BURSAR = "bursar";
	String identityID, userLevel, schoolname, nameOfDistrict;
	String emisNumber;

	public UserLevelSelector(Statement stm, Statement stmt, Statement stmt1, ResultSet rs, ResultSet rs1,
			String identityID, String emisNumber, String userLevel, String schoolname, String nameOfDistrict) {
		this.stm = stm;
		this.stmt = stmt;
		this.stmt1 = stmt1;
		this.rs = rs;
		this.rs1 = rs1;
		this.identityID = identityID;
		this.emisNumber = emisNumber;
		this.userLevel = userLevel;
		this.schoolname = schoolname;
		this.nameOfDistrict = nameOfDistrict;

	}

	public void selectUser(String userLevel) {

		AdministratorMainView administratorMainView = new AdministratorMainView(stm, stmt, stmt1, rs, rs1, emisNumber,
				identityID, userLevel, schoolname, nameOfDistrict);
		TeacherMainView teacherMainView = new TeacherMainView(rs, rs1, stm, stmt, stmt1, emisNumber, identityID,
				schoolname);
		BursarMainView bursarMainView = new BursarMainView(stm, stmt, rs, rs1, emisNumber, identityID, userLevel);

		if (SystemUserLevels.getTeacher().equals(userLevel)) {
			UI.getCurrent().getNavigator().addView(TEACHER, teacherMainView);
			UI.getCurrent().getNavigator().navigateTo(TEACHER);

		}
		if (SystemUserLevels.getAccountsclerk().equals(userLevel)) {

		}
		if (SystemUserLevels.getAdmin().equals(userLevel) || SystemUserLevels.getHead().equals(userLevel)) {
			UI.getCurrent().getNavigator().addView(ADMINISTRATOR, administratorMainView);
			UI.getCurrent().getNavigator().navigateTo(ADMINISTRATOR);
			new RemindAdministratorForReceiptLeft(this.rs, this.rs1, this.stm, this.stmt, emisNumber)
					.scanForReceiptsLeft();
		}
		if (SystemUserLevels.getBursar().equals(userLevel)) {
			UI.getCurrent().getNavigator().addView(BURSAR, bursarMainView);
			UI.getCurrent().getNavigator().navigateTo(BURSAR);
		}
		if (SystemUserLevels.getAdmin2().equals(userLevel)) {
			CheckInView checkIn = new CheckInView(stm, stmt, rs, rs1, emisNumber, identityID, userLevel, schoolname);
			UI.getCurrent().getNavigator().addView("checkin", checkIn);
			UI.getCurrent().getNavigator().navigateTo("checkin");
		}
		/*
		 * if (SystemUserLevels.getHead().equals(userLevel)) {
		 * UI.getCurrent().getNavigator().addView(ADMINISTRATOR,
		 * administratorMainView);
		 * UI.getCurrent().getNavigator().navigateTo(ADMINISTRATOR); }
		 */
		if (SystemUserLevels.getSchooltreasurer().equals(userLevel)) {

		}
		if (SystemUserLevels.getSdcchairman().equals(userLevel)) {

		}
		if (SystemUserLevels.getSdcsecretary().equals(userLevel)) {

		}
		if (SystemUserLevels.getStudent().equals(userLevel)) {
			String studentname = new RetrieveStudentName(rs, stm).fetchStudentName(identityID);
			String currentClass = new GetStudentClass(rs, stm).getClassName(emisNumber, identityID);
			Student student = new Student(rs, rs1, stm, stmt, studentname, identityID, currentClass, emisNumber);
			UI.getCurrent().getNavigator().addView(STUDENT, student);
			UI.getCurrent().getNavigator().navigateTo(STUDENT);
		} else if (SystemUserLevels.getSdctreasurer().equals(userLevel)) {

		}

	}
}
