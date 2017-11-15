package com.equation.accounts.administrator.receiptsbooks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.system.users.teacher.getname.FetchStuffMemberName;
import com.equation.user.session.attributes.UserSessionAttributes;
import com.equation.utils.date.DateUtility;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings({ "serial", "deprecation" })
public class CaptureNewReceiptBook extends CustomComponent implements View {

	TextField bookNumber, firstReceiptNumber, finalReceptNumber;
	Statement stm, stmt;
	ResultSet rs, rs1;
	TabSheet tabs;
	String schoolID, userID;

	public CaptureNewReceiptBook(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, TabSheet tabs,
			String schoolID, String userID) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.tabs = tabs;
		this.schoolID = schoolID;
		this.userID = userID;

		bookNumber = new TextField("Enter Receipt Book Number");
		bookNumber.setRequiredIndicatorVisible(true);

		firstReceiptNumber = new TextField("First Receipt  Number");
		firstReceiptNumber.setRequiredIndicatorVisible(true);

		finalReceptNumber = new TextField("Last Receipt Number");
		finalReceptNumber.setRequiredIndicatorVisible(true);

		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_DANGER);
		submit.addStyleName("receipt_book_submit_button");
		submit.addClickListener((e) -> {
			String schoolIDd = (String) getSession().getAttribute(UserSessionAttributes.SCHOOL_EMIS_NUMBER);
			String bookNumberr = bookNumber.getValue();
			String firstReceiptNumberr = firstReceiptNumber.getValue();
			String finalReceptNumberr = finalReceptNumber.getValue();
			String date = new DateUtility().getDate();
			String time = new DateUtility().getTime();
			String status = "Open";

			if (!(bookNumberr.equals("") || firstReceiptNumberr.equals("") || finalReceptNumberr.equals(""))) {

				String query = "SELECT bookNumber,firstReceiptNumber,finalReceptNumber,date,time,userID FROM receiptbooks,schools WHERE schools.schoolID = '"
						+ schoolID + "'  AND status = '" + status + "'";
				try {
					this.rs = this.stm.executeQuery(query);
					if (!this.rs.next()) {
						ValidateReceiptBook validateReceiptBook = new ValidateReceiptBook(stm, rs, schoolID);
						validateReceiptBook.validateRecord(schoolIDd, bookNumberr, firstReceiptNumberr,
								finalReceptNumberr, date, time, status, bookNumber, firstReceiptNumber,
								finalReceptNumber, userID);
						bookNumber.clear();
						firstReceiptNumber.clear();
						finalReceptNumber.clear();
					} else {
						String bookNumber = this.rs.getString(1);
						String firstReceiptNumber = this.rs.getString(2);
						String finalReceptNumber = this.rs.getString(3);
						String dated = this.rs.getString(4) + " " + this.rs.getString(5);
						String userIDd = this.rs.getString(6);
						String userame = new FetchStuffMemberName(this.rs, this.rs1, this.stm, this.stmt)
								.getActualName(userIDd);

						new Notification("Information",
								"Book Number " + bookNumber + "<br/>Receipt Range " + firstReceiptNumber + " - "
										+ finalReceptNumber + "<br/>Posted By " + userame + "<br/>On this " + dated
										+ "<br/>The above mentioned receipt book is yet to be approved and balanced.<br/>A new receipt book cannot be accepted by the system before closing a previous one!",
								Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
					}
				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			} else {
				new Notification("Error", "A blank field cannot be submitted!", Notification.TYPE_ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			}
		});

		FormLayout formLayout = new FormLayout(bookNumber, firstReceiptNumber, finalReceptNumber, submit);
		formLayout.setCaption("New Receipt Book");
		formLayout.setIcon(FontAwesome.BOOK);
		formLayout.setSpacing(true);

		VerticalLayout verticalLayout = new VerticalLayout();

		HorizontalLayout gridLayout = new HorizontalLayout();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(formLayout);

		verticalLayout.addComponent(gridLayout);
		verticalLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
		this.setCompositionRoot(verticalLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		bookNumber.focus();
	}

	public void insertTab() {
		boolean exist = false;
		int count = tabs.getComponentCount();
		for (int x = 0; x < count; x++) {
			if (tabs.getTab(x).getCaption().equals("Receipt Books")) {
				exist = true;
				tabs.setSelectedTab(x);
				break;
			}
		}
		if (!exist) {
			tabs.addTab(this, "Receipt Books", VaadinIcons.BOOK);
			tabs.setSelectedTab(count);
			tabs.getTab(this).setClosable(true);
		}
	}
}