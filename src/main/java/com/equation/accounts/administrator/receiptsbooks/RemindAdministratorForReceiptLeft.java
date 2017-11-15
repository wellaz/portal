package com.equation.accounts.administrator.receiptsbooks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.system.users.teacher.getname.FetchStuffMemberName;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("deprecation")
public class RemindAdministratorForReceiptLeft {

	ResultSet rs, rs1;
	Statement stm, stmt;
	String schoolID;
	int q = 0;

	public RemindAdministratorForReceiptLeft(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt,
			String schoolID) {
		this.rs1 = rs1;
		this.rs = rs;
		this.stm = stm;
		this.stmt = stmt;
		this.schoolID = schoolID;
	}

	public void scanForReceiptsLeft() {
		String query = "SELECT receiptNumber FROM feesreceived,schools WHERE schools.schoolID = '" + schoolID + "'";
		try {
			rs1 = stmt.executeQuery(query);
			rs1.last();
			int rows = rs1.getRow();
			if (rows > 0) {
				String query1 = "SELECT receiptNumber FROM feesreceived,schools WHERE schools.schoolID = '" + schoolID
						+ "'";
				rs = stm.executeQuery(query1);
				rs.last();
				int receiptNumber = rs.getInt(1);
				String query2 = "SELECT finalReceptNumber,bookNumber,firstReceiptNumber,date,time,status,userID FROM receiptbooks,schools WHERE schools.schoolID = '"
						+ schoolID + "'";
				rs = stm.executeQuery(query2);
				rs.last();
				int finalReceiptNumber = rs.getInt(1);
				String bookNumber = rs.getString(2);
				int firstReceiptNumber = rs.getInt(3);
				String dated = rs.getString(4) + " " + rs.getString(5);
				String status = rs.getString(6);
				String userID = rs.getString(7);
				String user = new FetchStuffMemberName(rs, rs1, stm, stmt).getActualName(userID);

				int receiptsLeft = finalReceiptNumber - receiptNumber;
				if (receiptsLeft <= 100 && receiptsLeft > 0) {
					new Notification("Warning",
							"<br/>We are left with " + receiptsLeft
									+ " receipts.<br/><br/>The receipt book may run out any time from now",
							Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
				}
				if (receiptsLeft == 0) {
					String state = "Closed";
					String query6 = "SELECT status FROM schools,receiptbooks WHERE schools.schoolID = '" + schoolID
							+ "' AND status = '" + state + "'";
					try {
						rs = stm.executeQuery(query6);
						if (!rs.last()) {

							String query3 = "SELECT SUM(amount) FROM feesreceived,schools WHERE schools.schoolID = '"
									+ schoolID + "' AND receiptNumber BETWEEN '" + firstReceiptNumber + "' AND '"
									+ finalReceiptNumber + "'";
							rs = stm.executeQuery(query3);
							rs.next();
							double sum = rs.getDouble(1);

							Label bookNumberlbl = new Label("Book Number");
							Label bookNumberlbl2 = new Label("" + bookNumber);

							Label firstReceiptNumberlbl = new Label("First Receipt Number");
							Label firstReceiptNumberlbl2 = new Label("" + firstReceiptNumber);

							Label finalReceiptNumberlbl = new Label("Final Receipt Number");
							Label finalReceiptNumberlbl2 = new Label("" + finalReceiptNumber);

							Label userlbl = new Label("Posted By");
							Label userlbl2 = new Label("" + user);

							Label datedlbl = new Label("On This");
							Label datedlbl2 = new Label("" + dated);

							Label statuslbl = new Label("Status");
							Label statuslbl2 = new Label(status);

							Label totallbl = new Label("Total Amount");
							Label totallbl2 = new Label("" + sum);

							Button closeReceipt = new Button("Close This Receipt Book");
							closeReceipt.addStyleName(ValoTheme.BUTTON_DANGER);
							closeReceipt.setIcon(FontAwesome.CLOSE);

							GridLayout gridLayout = new GridLayout(2, 8, bookNumberlbl, bookNumberlbl2,
									firstReceiptNumberlbl, firstReceiptNumberlbl2, finalReceiptNumberlbl,
									finalReceiptNumberlbl2, userlbl, userlbl2, datedlbl, datedlbl2, statuslbl,
									statuslbl2, totallbl, totallbl2, closeReceipt);
							gridLayout.setSpacing(true);
							HorizontalLayout horizontalLayout1 = new HorizontalLayout(gridLayout);
							VerticalLayout layout1 = new VerticalLayout(horizontalLayout1);
							layout1.setComponentAlignment(horizontalLayout1, com.vaadin.ui.Alignment.MIDDLE_CENTER);
							Window window1 = new Window("Attention", layout1);
							window1.center();
							window1.setWidth("50%");
							window1.setHeight("50%");
							window1.setModal(true);

							closeReceipt.addClickListener((e) -> {
								Window window = new Window("Confirmation Code");
								window.center();
								window.setWidth("30%");
								window.setHeight("30%");
								window.addStyleName("admin_confirm_window");
								window.setModal(true);
								PasswordField field = new PasswordField("Confirm");
								field.setDescription("Confirm with a secret code");
								Button confirn = new Button("Confirm");

								confirn.addClickListener((ee) -> {
									String pass = field.getValue();
									if (!pass.equals("")) {
										String validateQuery = "SELECT password FROM users WHERE identityID  ='"
												+ userID + "'";
										try {
											rs = stm.executeQuery(validateQuery);
											rs.next();
											String validpass = rs.getString(1);

											if (validpass.equals(pass)) {
												String newStatus = "Closed";
												String queryy = "UPDATE receiptbooks,schools SET status = '" + newStatus
														+ "' WHERE schools.schoolID ='" + schoolID
														+ "' AND bookNumber = '" + bookNumber + "'";
												try {
													stm.executeUpdate(queryy);
													UI.getCurrent().removeWindow(window);
													window.close();
													UI.getCurrent().removeWindow(window1);
													window1.close();
													new Notification("Information",
															"Book Number " + bookNumber
																	+ " has been closed and is no longer in use.<br/><br/>A new receipt book is required in order for the bursar to process transactions.<br/><br/>Thank You!!!",
															Notification.TYPE_TRAY_NOTIFICATION, true)
																	.show(Page.getCurrent());
												} catch (Exception e1) {
													e1.printStackTrace();
												}
											} else {
												q++;
												if (q == 3) {
													new Notification("Error",
															"Sorry you have exhausted all the 3 attempts.<br/><br/>Please try again later.",
															Notification.TYPE_ERROR_MESSAGE, true)
																	.show(Page.getCurrent());
													UI.getCurrent().getSession().close();
												}
												new Notification("Error",
														"The confirmation code is incorrect.<br/><br/>Please try again.<br/><br/>You are left with "
																+ (3 - q) + " Attempts!",
														Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
												field.clear();
												field.focus();
											}
										} catch (SQLException ee1) {
											ee1.printStackTrace();
										}
									} else {
										new Notification("Error",
												"The confirmation code is incorrect.<br/><br/>You may need to repeat the process.",
												Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
									}
								});
								FormLayout formLayout = new FormLayout(field, confirn);
								HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
								VerticalLayout layout = new VerticalLayout(horizontalLayout);
								layout.setComponentAlignment(horizontalLayout, com.vaadin.ui.Alignment.MIDDLE_CENTER);
								window.setContent(layout);
								UI.getCurrent().addWindow(window);
							});

							UI.getCurrent().addWindow(window1);
						} else {
							new Notification("Note",
									"We kindly remind you that there is no receipt book in the system that is currently working.<br/><br/>You may need to confirm with the bursar, as he or she may not be able to issue any receipt nor process any transaction!",
									Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
						}
					} catch (SQLException ee) {
						ee.printStackTrace();
					}
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}