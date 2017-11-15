package com.eqauation.assessment.teacher.createasssignment.receiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Statement;

import com.eqauation.assessment.assignments.insert.InsertAssignmentDetails;
import com.eqauation.assessment.teacher.createasssignment.AssignmentTypes;
import com.equation.utils.application.folders.Folder;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class AssignmentReceiver implements Receiver, SucceededListener {
	public File file;
	Window window;
	String filename, class_name, tablename;
	Statement stm, stmt;
	ResultSet rs, rs1;
	String topic, class_id, total_mark, subject_id, term, year, date_posted, due, classname;

	TextField topics, totalmark;

	public AssignmentReceiver(Window window, Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, String topic,
			String class_id, String total_mark, String subject_id, String term, String year, String date_posted,
			String due, String classname, TextField topics, TextField totalmark) {
		this.window = window;
		this.topics = topics;
		this.totalmark = totalmark;
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;
		this.term = term;
		this.year = year;
		this.topic = topic;
		this.class_id = class_id;
		this.total_mark = total_mark;
		this.subject_id = subject_id;
		this.date_posted = date_posted;
		this.due = due;
		this.classname = classname;

	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		new InsertAssignmentDetails().insertData(stm, topic, class_id, total_mark, subject_id, term, year, date_posted,
				due, AssignmentTypes.DOCUMENT);
		totalmark.clear();
		topics.clear();
		new Notification("Success", classname.toUpperCase() + " assignment was uploaded",
				Notification.Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
		UI.getCurrent().removeWindow(window);
		window.close();
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null;
		try {
			if (!filename.equals("")) {
				if (new File(filename).length() > 0) {

					file = new File(Folder.makeTeachersAssignmentsUploadsFolder(classname) + "/" + filename);
					fos = new FileOutputStream(file);
					this.filename = filename;
				} else {
					new Notification(
							"<h1 style='color:white;'>This selected File is Empty!<br>It does not have any content!<br/>Please select a prepared Assignment file to upload.<h1/>",
							"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
				}
			} else {
				new Notification(
						"<h1 style='color:white;'>No file is selected!<br>Please select a prepared Assignment file to upload.<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		} catch (FileNotFoundException e) {
			new Notification("<h1>Could not upload file<br/><h1/>", e.getMessage(), Notification.Type.ERROR_MESSAGE,
					true).show(Page.getCurrent());
			return null;

		}

		return fos;
	}

}
