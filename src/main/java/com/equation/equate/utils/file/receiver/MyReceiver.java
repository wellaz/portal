package com.equation.equate.utils.file.receiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.sql.Statement;

import com.equation.equate.adjustedmark.GetAdjustedMark;
import com.equation.equate.insertdata.InsertDataIntoMarkSchedule;
import com.equation.equate.utils.application.basepath.ApplicationBasePath;
import com.equation.equate.utils.file.mimetype.ValidateFileMimeType;
import com.equation.equate.utils.tablename.Tablename;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.Window;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class MyReceiver implements Receiver, SucceededListener {
	public File file;
	Window window;
	String filename, subject, paper, out_of, percentage, class_name, tablename;
	Statement stm;
	String term, year, date;

	public MyReceiver(Window window, String class_name, String subject, String paper, String out_of, String percentage,
			Statement stm, String term, String year, String date) {
		this.window = window;
		this.subject = subject;
		this.paper = paper;
		this.class_name = class_name;
		this.percentage = percentage;
		this.stm = stm;
		this.out_of = out_of;
		this.percentage = percentage;
		this.date = date;
		this.year = year;
		this.term = term;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		UI.getCurrent().removeWindow(this.window);
		this.window.close();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line;
			while ((line = bf.readLine()) != null) {
				String[] value = line.split(",");
				String student_name = value[0];
				int raw_mark = Integer.parseInt(value[1]);

				if (raw_mark <= Integer.parseInt(out_of)) {
					new GetAdjustedMark();
					String adjusted_mark = Integer.toString(GetAdjustedMark.getAdjustedMark(raw_mark,
							Integer.parseInt(out_of), Integer.parseInt(percentage)));
					if (this.paper.equals("1")) {
						new InsertDataIntoMarkSchedule().insertData(Tablename.PAPER_ONE, class_name, subject, out_of,
								percentage, student_name, Integer.toString(raw_mark), adjusted_mark, stm, term, year,
								date);
					} else {
						new InsertDataIntoMarkSchedule().insertData(Tablename.PAPER_TWO, class_name, subject, out_of,
								percentage, student_name, Integer.toString(raw_mark), adjusted_mark, stm, term, year,
								date);
					}
				} else {

				}
			}
			bf.close();
			new Notification("Success", "The upload succeeded!<br>Thank you", Notification.TYPE_TRAY_NOTIFICATION, true)
					.show(Page.getCurrent());

		} catch (Exception ee) {
			ee.printStackTrace(System.err);
			new Notification("Error", ee.getMessage(), Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null;
		try {
			if (new ValidateFileMimeType().isValidCSVFile(filename)) {
				file = new File(ApplicationBasePath.basePath() + "/WEB-INF/uploads/" + filename);
				fos = new FileOutputStream(file);
				this.filename = filename;
			} else {
				new Notification("",
						"The selected file is not a CSV file!<br>Please select a prepared CSV file to upload.",
						Notification.TYPE_ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		} catch (FileNotFoundException e) {
			new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE)
					.show(Page.getCurrent());
			return null;

		}

		return fos;
	}

}
