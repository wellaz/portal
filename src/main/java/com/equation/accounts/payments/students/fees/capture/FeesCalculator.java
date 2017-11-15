package com.equation.accounts.payments.students.fees.capture;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.accounts.adminaccount.InsertDataIntoAdminAccount;
import com.equation.accounts.bankaccount.InsertDataIntoBankAccount;
import com.equation.accounts.buildingaccount.InsertDataIntoBuildingAccount;
import com.equation.accounts.bzpzaccount.InsertDataIntoBSPZAccount;
import com.equation.accounts.generalpurpose.InsertDataIntoGeneralPurposeAccount;
import com.equation.accounts.levy.InsertDataIntoLevyAccount;
import com.equation.accounts.other.InsertDataIntoOtherAccount;
import com.equation.accounts.payments.print.printable.MyFeesPaymentPrintable;
import com.equation.accounts.payments.print.support.PrintSupport;
import com.equation.accounts.sportsaccount.InsertDataIntoSportsAccount;
import com.equation.accounts.tuition.InsertDataIntoTuitionAccount;
import com.equation.database.connection.pool.MainDatabaseConnectionPool;
import com.equation.school.details.retrieve.RetrieveSchoolDetails;
import com.equation.student.currentclass.retrieve.GetStudentClass;
import com.equation.student.name.retrieve.RetrieveStudentName;
import com.equation.system.users.teacher.getname.FetchStuffMemberName;
import com.equation.utils.schoolterms.AllYearTermsBin;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 *
 * @author Wellington
 */
public class FeesCalculator {
	ResultSet rs, rs1;
	Statement stm, stmt;
	IncrementReceiptNumber incrementReceiptNumber;

	public FeesCalculator(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		incrementReceiptNumber = new IncrementReceiptNumber(rs, rs1, stm, stmt);
	}

	@SuppressWarnings("deprecation")
	public void calculateFees(double amountTendered, double currentBalance, String term, int year, String studentID,
			String schoolID, String userID, String paidBy, String date, String time) {
		InsertDataIntoFeesPaymentsTable dataIntoFeesPaymentsTable = new InsertDataIntoFeesPaymentsTable(stm, stmt, rs,
				rs1);
		String description = "school fees";

		double newFeesBalance = currentBalance - amountTendered;

		if (newFeesBalance >= 0.0001) {

			int receiptNumber = incrementReceiptNumber.nextReceiptNumberr();
			if (receiptNumber > 0) {
				System.out.println("insert new Fees Payment of " + amountTendered + " record for " + term + " " + year);
				dataIntoFeesPaymentsTable.insertData(receiptNumber, studentID, userID, amountTendered, term, year,
						paidBy, schoolID, date, time, newFeesBalance, description);
				insertDataInOtherAccounts("Fees Student ID:" + studentID + " Receipt :" + receiptNumber, amountTendered,
						date, time, schoolID, year, Integer.toString(receiptNumber));
				makeReceipt(receiptNumber, amountTendered, newFeesBalance, studentID, term + " " + year, schoolID,
						userID);
			} else {
				new Notification("Error",
						"Transaction failed because a receipt number couldn't be found.<br/><br/>It's either the receipt book is exhausted OR the receipt book register is empty.<br/><br/>Elevate this message to the Administrator!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
		} else {
			int receiptNumber = incrementReceiptNumber.nextReceiptNumberr();
			if (receiptNumber > 0) {
				System.out.println("insert new Fees Payment of " + currentBalance + " record for " + term + " " + year);
				dataIntoFeesPaymentsTable.insertData(receiptNumber, studentID, userID, currentBalance, term, year,
						paidBy, schoolID, date, time, 0, description);
				insertDataInOtherAccounts("Fees Student ID:" + studentID + " Receipt :" + receiptNumber, currentBalance,
						date, time, schoolID, year, Integer.toString(receiptNumber));
				makeReceipt(receiptNumber, currentBalance, 0, studentID, term + " " + year, schoolID, userID);
			} else {
				new Notification("Error",
						"Transaction failed because a receipt number couldn't be found.<br/><br/>It's either the receipt book is exhausted OR the receipt book register is empty.<br/><br/>Elevate this message to the Administrator!",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}
			// start fro here
			while (newFeesBalance < 0) {
				double newAmountTendered = Math.abs(newFeesBalance);
				String newTerm = getFollowingTerm(term, year).split(",")[0];
				String newYear = getFollowingTerm(term, year).split(",")[1];
				double newFeesStructure = getStructureTotal(Integer.parseInt(newYear), schoolID);
				if (newAmountTendered >= newFeesStructure) {

					receiptNumber = incrementReceiptNumber.nextReceiptNumberr();
					if (receiptNumber > 0) {
						dataIntoFeesPaymentsTable.insertData(receiptNumber, studentID, userID, newFeesStructure,
								newTerm, Integer.parseInt(newYear), paidBy, schoolID, date, time, 0, description);
						insertDataInOtherAccounts("Fees Student ID:" + studentID + " Receipt :" + receiptNumber,
								newFeesStructure, date, time, schoolID, Integer.parseInt(newYear),
								Integer.toString(receiptNumber));
						System.out.println("insert new Fees Payment of  " + newFeesStructure + "record for " + newTerm
								+ " " + newYear);
						makeReceipt(receiptNumber, newFeesStructure, 0, studentID, newTerm + " " + newYear, schoolID,
								userID);
					} else {
						new Notification("Error",
								"Transaction failed because a receipt number couldn't be found.<br/><br/>It's either the receipt book is exhausted OR the receipt book register is empty.<br/><br/>Elevate this message to the Administrator!",
								Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
						break;
					}

				} else {
					System.out.println("insert new Fees Payment of  " + newAmountTendered + "record for " + newTerm
							+ " " + newYear);
					double balance = newFeesStructure - newAmountTendered;
					receiptNumber = incrementReceiptNumber.nextReceiptNumberr();
					if (receiptNumber > 0) {
						dataIntoFeesPaymentsTable.insertData(receiptNumber, studentID, userID, newAmountTendered,
								newTerm, Integer.parseInt(newYear), paidBy, schoolID, date, time, balance, description);
						insertDataInOtherAccounts("Fees Student ID:" + studentID + " Receipt :" + receiptNumber,
								newAmountTendered, date, time, schoolID, Integer.parseInt(newYear),
								Integer.toString(receiptNumber));
						makeReceipt(receiptNumber, newAmountTendered, balance, studentID, newTerm + " " + newYear,
								schoolID, userID);

					} else {
						new Notification("Error",
								"Transaction failed because a receipt number couldn't be found.<br/><br/>It's either the receipt book is exhausted OR the receipt book register is empty.<br/><br/>Elevate this message to the Administrator!",
								Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
						break;
					}
				}
				newFeesBalance = newFeesStructure - newAmountTendered;
				term = newTerm;
				year = Integer.parseInt(newYear);
			}

			/// end while
		}
	}

	public String getFollowingTerm(String term, int year) {
		String returnValue = null;
		ArrayList<String> array = AllYearTermsBin.allTermsArray();

		// String nextterm;

		int newyear;
		switch (term) {

		case "Term One": {
			newyear = year;
			returnValue = array.get(1) + "," + newyear;

			break;
		}
		case "Term Two": {
			newyear = year;
			returnValue = array.get(2) + "," + newyear;
			break;
		}
		case "Term Three": {
			newyear = year + 1;
			returnValue = array.get(0) + "," + newyear;
			break;
		}

		}
		return returnValue;
	}

	@SuppressWarnings("deprecation")
	public double getStructureTotal(int year, String schoolID) {
		double amount = 0;
		String query = "SELECT total FROM feestructure,schools WHERE year = '" + year + "' AND schools.schoolID='"
				+ schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			if (rs1.last()) {
				amount = rs1.getDouble(1);
			} else {

				new Notification("Error",
						"No fees structure set for " + year
								+ ". <br/>please contact the administrator  for more information.",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return amount;

	}

	public void insertDataInOtherAccounts(String description, double amountTendered, String date, String time,
			String schoolID, int year, String receiptNumber) {
		InsertDataIntoBankAccount bankAccount = new InsertDataIntoBankAccount(stm);
		bankAccount.postBankAccountTransaction(description, 0, amountTendered, date, time, schoolID);

		FeesStructureBreakdown breakdown = new FeesStructureBreakdown(stm, stmt, rs, rs1, schoolID, year);
		double total = breakdown.getTotal();

		double tution = (breakdown.getTution() / total) * amountTendered;
		double bspz = (breakdown.getBspz() / total) * amountTendered;
		double building = (breakdown.getBuilding() / total) * amountTendered;
		double generalPurpose = (breakdown.getGeneralPurpose() / total) * amountTendered;
		double sports = (breakdown.getSports() / total) * amountTendered;
		double levi = (breakdown.getLevi() / total) * amountTendered;
		double other = (breakdown.getOther() / total) * amountTendered;

		double admin = (breakdown.getAdmin() / total) * amountTendered;

		new InsertDataIntoAdminAccount(stm).postAdministrationAccountTransaction(receiptNumber, 0, admin, date, time,
				schoolID);

		new InsertDataIntoBSPZAccount(stm).postBSPZTransaction(receiptNumber, 0, bspz, date, time, schoolID);

		new InsertDataIntoBuildingAccount(stm).postBuildingAccountTransaction(receiptNumber, 0, building, date, time,
				schoolID);

		new InsertDataIntoTuitionAccount(stm).postData(receiptNumber, 0, tution, date, time, schoolID);

		new InsertDataIntoOtherAccount(stm).postData(receiptNumber, 0, other, date, time, schoolID);

		new InsertDataIntoSportsAccount(stm).postSportsAccountTransaction(receiptNumber, 0, sports, date, time,
				schoolID);

		new InsertDataIntoGeneralPurposeAccount(stm).postData(receiptNumber, 0, generalPurpose, date, time, schoolID);
		new InsertDataIntoLevyAccount(stm).postData(receiptNumber, 0, levi, date, time, schoolID);

	}

	public static void main(String[] args) {
		MainDatabaseConnectionPool connectionPool = new MainDatabaseConnectionPool();
		connectionPool.connectionDb();
		FeesCalculator calculator = new FeesCalculator(connectionPool.rs, connectionPool.rs1, connectionPool.stm,
				connectionPool.stmt);

		calculator.calculateFees(100, 15, "Term Two", 2016, "DFF", "990", "4567", "Me", "2017-05-04", "00:00:00");

	}

	public void makeReceipt(int receiptnumber, double amountTendered, double balance, String studentID, String term,
			String schoolID, String userID) {
		String studentName = new RetrieveStudentName(rs, stm).fetchStudentName(studentID);
		RetrieveSchoolDetails schoolDetails = new RetrieveSchoolDetails(rs, rs1, stm, stmt, schoolID);
		String schoolName = schoolDetails.getSchoolName();
		String schoolPostal = schoolDetails.getSchoolPostal();
		String schoolPhone = schoolDetails.getSchoolPhone();
		String schoolEmail = schoolDetails.getSchoolEmail();
		String schoolCell = schoolDetails.getSchoolCell();
		String tellerName = new FetchStuffMemberName(rs, rs1, stm, stmt).getActualName(userID);
		String classname = new GetStudentClass(rs, stm).getClassName(schoolID, studentID);

		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(
				new MyFeesPaymentPrintable(receiptnumber, amountTendered, balance, studentID, studentName, schoolName,
						tellerName, schoolPostal, schoolPhone, schoolCell, schoolEmail, term, classname),
				PrintSupport.getPageFormat(pj, 0));
		boolean ok = pj.printDialog();
		if (ok) {
			try {
				pj.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
	}
}