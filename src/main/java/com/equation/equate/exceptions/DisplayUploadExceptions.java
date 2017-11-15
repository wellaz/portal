/**
 *
 * @author Wellington
 */
package com.equation.equate.exceptions;

import java.util.ArrayList;

import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.v7.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author Wellington
 *
 */
@SuppressWarnings("deprecation")
public class DisplayUploadExceptions {

	public void DisplayWindow(ArrayList<String> data) {
		int size = data.size();
		if (size > 0) {
			Table table = new Table("<h2 style='color:red;'>Attention-Listed Students were not posted at all<h2/>");
			table.setCaptionAsHtml(true);
			table.addContainerProperty("Exceptions List", String.class, null);

			for (int i = 0; i < size; i++) {
				String val = data.get(i);
				table.addItem(new Object[] { val }, new Integer(i));
			}

			table.setFooterVisible(true);
			table.setColumnFooter("Exceptions List", String.valueOf(size) + " Exceptions Found And Not Posted");
			table.setPageLength(size);

			String num = (size > 1) ? "These " + size + " records were not posted" : "This Record was not posted";

			Panel panel = new Panel(table);
			Label lbl = new Label(
					"<center>" + num
							+ " We recommend that you post exceptions seperately without repeating the whole record to avoid duplicating successfully posted records.</center>",
					ContentMode.HTML);
			HorizontalLayout horizontalLayout = new HorizontalLayout(lbl);
			VerticalLayout layout = new VerticalLayout(horizontalLayout, panel);

			Window window = new Window(
					"<h1 style='color:red;'>Attention: All Raw Marks For These Students Were Processed At All<h2/>",
					layout);
			window.setCaptionAsHtml(true);
			window.setSizeFull();
			window.center();
			window.setModal(true);

			UI.getCurrent().addWindow(window);

			new Notification(
					"<h1 style='color:white;'>ATTENTION<br/>We suggest that you analyse the listed students raw marks for all their subjects. Prepare another CSV file just for these students with all their subjects raw marks,and upload it using this same facility. Make sure that you analyse their marks before uploading!<br/>Thank you!!! <h1/>",
					"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
		}
	}
}