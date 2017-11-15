package com.equation.school.subjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Statement;

import com.equation.utils.application.folders.Folder;
import com.equation.utils.uploads.mimetype.validate.ValidateMIMEType;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class UploadSubjectsFile implements Receiver, SucceededListener {
	public File file;
	String filename;
	Statement stm, stmt;
	ResultSet rs, rs1;

	public UploadSubjectsFile(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		try {

			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line;
			int lines = 0;
			InsertSubjects insertSubjects = new InsertSubjects();

			while ((line = bf.readLine()) != null) {
				if (lines == 0)
					lines++;
				else {
					String[] value = line.split(",");
					String subject = value[0];
					String code = value[1];
					if (!subject.equals("")) {
						if (!new AllSchoolSubjects(rs, rs1, stm, stmt).isSubjectPosted(subject)) {
							insertSubjects.insertData(stm, subject, code);
						}

					}

					lines++;
				}
			}
			bf.close();
			new Notification("<h1>The upload succeeded!<br>Thank you<h1/>", "", Notification.Type.WARNING_MESSAGE, true)
					.show(Page.getCurrent());
		} catch (Exception ee) {
			ee.printStackTrace(System.err);
			new Notification("Error", ee.getMessage(), Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
		}

	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null;
		try {
			if (new ValidateMIMEType().isValidCSVFile(filename)) {
				file = new File(Folder.makeUploadsFolder() + "/" + filename);
				fos = new FileOutputStream(file);
				this.filename = filename;
			} else {
				new Notification(
						"<h1 style='color:white;'>The selected file is not a CSV file!<br>Please select a prepared CSV file to upload.<h1/>",
						"", Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
			}

		} catch (FileNotFoundException e) {
			new Notification("<h1>Could not open file<br/><h1/>", e.getMessage(), Notification.Type.ERROR_MESSAGE, true)
					.show(Page.getCurrent());
			return null;

		}

		return fos;
	}

}
