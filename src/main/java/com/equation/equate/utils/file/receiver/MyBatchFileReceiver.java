/**
 *
 * @author Wellington
 */
package com.equation.equate.utils.file.receiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.equation.equate.adjustedmark.GetAdjustedMark;
import com.equation.equate.exceptions.DisplayUploadExceptions;
import com.equation.equate.insertdata.InsertDataIntoMarkSchedule;
import com.equation.equate.settings.passmark.PassMark;
import com.equation.equate.subjects.marks.settings.GetSubjectMarkSettings;
import com.equation.equate.subjects.passed.record.RecordSubjectsPassed;
import com.equation.equate.utils.application.basepath.ApplicationBasePath;
import com.equation.equate.utils.file.mimetype.ValidateFileMimeType;
import com.equation.equate.utils.isdigit.IsItADigit;
import com.equation.equate.utils.subjects.AllSubjects;
import com.equation.equate.utils.tablename.Tablename;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.Window;

/**
 * @author Wellington
 *
 */
@SuppressWarnings("serial")
public class MyBatchFileReceiver implements Receiver, SucceededListener {
	public File file;
	Window window;
	String filename, class_name, tablename;
	Statement stm, stmt;
	ResultSet rs, rs1;
	String term, year, date;

	public MyBatchFileReceiver(Window window, String class_name, Statement stm, Statement stmt, ResultSet rs,
			ResultSet rs1, String term, String year, String date) {
		this.window = window;
		this.class_name = class_name;
		this.stm = stm;
		this.rs = rs;
		this.stmt = stmt;
		this.rs1 = rs1;
		this.term = term;
		this.year = year;
		this.date = date;
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		UI.getCurrent().removeWindow(this.window);
		this.window.close();
		GetSubjectMarkSettings settings = new GetSubjectMarkSettings(stm, rs);
		InsertDataIntoMarkSchedule insertDataIntoMarkSchedule = new InsertDataIntoMarkSchedule();
		RecordSubjectsPassed passed = new RecordSubjectsPassed();
		ScanForDuplicates duplicates = new ScanForDuplicates(rs, rs1, stm, stmt, term, year);
		ArrayList<String> studentdata = duplicates.allStudents();
		int passMark = new PassMark(rs, rs1, stm, stmt).getPassMark();
		try {

			ArrayList<String> english = settings.getData(AllSubjects.ENGLISH, class_name, term, year);
			ArrayList<String> shona = settings.getData(AllSubjects.SHONA, class_name, term, year);
			ArrayList<String> maths = settings.getData(AllSubjects.MATHEMATICS, class_name, term, year);
			ArrayList<String> gp = settings.getData(AllSubjects.GENERAL_PAPER, class_name, term, year);
			ArrayList<String> agric = settings.getData(AllSubjects.AGRICULTURE, class_name, term, year);

			int english_p1raw = Integer.parseInt(english.get(0));
			int english_p1cont = Integer.parseInt(english.get(1));
			int english_p2raw = Integer.parseInt(english.get(2));
			int english_p2cont = Integer.parseInt(english.get(3));

			int shona_p1raw = Integer.parseInt(shona.get(0));
			int shona_p1cont = Integer.parseInt(shona.get(1));
			int shona_p2raw = Integer.parseInt(shona.get(2));
			int shona_p2cont = Integer.parseInt(shona.get(3));

			int maths_p1raw = Integer.parseInt(maths.get(0));
			int maths_p1cont = Integer.parseInt(maths.get(1));
			int maths_p2raw = Integer.parseInt(maths.get(2));
			int maths_p2cont = Integer.parseInt(maths.get(3));

			int gp_p1raw = Integer.parseInt(gp.get(0));
			int gp_p1cont = Integer.parseInt(gp.get(1));
			int gp_p2raw = Integer.parseInt(gp.get(2));
			int gp_p2cont = Integer.parseInt(gp.get(3));

			int agric_p1raw = Integer.parseInt(agric.get(0));
			int agric_p1cont = Integer.parseInt(agric.get(1));
			int agric_p2raw = Integer.parseInt(agric.get(2));
			int agric_p2cont = Integer.parseInt(agric.get(3));

			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line;
			int lines = 0;
			IsItADigit is = new IsItADigit();
			ArrayList<String> data = new ArrayList<>();
			while ((line = bf.readLine()) != null) {
				if (lines == 0)
					lines++;
				else {
					String[] value = line.split(",");
					// scan all values
					if (!(value[0].equals("") || value[1].equals("") || value[2].equals("") || value[3].equals("")
							|| value[4].equals("") || value[5].equals("") || value[6].equals("") || value[7].equals("")
							|| value[8].equals("") || value[9].equals("") || value[10].equals(""))) {
						if (is.isDigit(value[1]) && is.isDigit(value[2]) && is.isDigit(value[3]) && is.isDigit(value[4])
								&& is.isDigit(value[5]) && is.isDigit(value[6]) && is.isDigit(value[7])
								&& is.isDigit(value[8]) && is.isDigit(value[9]) && is.isDigit(value[10])) {

							String student_name = value[0];
							int english_raw1mark = Integer.parseInt(value[1]);
							int english_raw2mark = Integer.parseInt(value[2]);

							int shona_raw1mark = Integer.parseInt(value[3]);
							int shona_raw2mark = Integer.parseInt(value[4]);

							int maths_raw1mark = Integer.parseInt(value[5]);
							int maths_raw2mark = Integer.parseInt(value[6]);

							int gp_raw1mark = Integer.parseInt(value[7]);
							int gp_raw2mark = Integer.parseInt(value[8]);

							int agric_raw1mark = Integer.parseInt(value[9]);
							int agric_raw2mark = Integer.parseInt(value[10]);

							if (english_raw1mark <= english_p1raw) {
								if (english_raw2mark <= english_p2raw) {
									if (shona_raw1mark <= shona_p1raw) {
										if (shona_raw2mark <= shona_p2raw) {
											if (maths_raw1mark <= maths_p1raw) {
												if (maths_raw2mark <= maths_p2raw) {
													if (gp_raw1mark <= gp_p1raw) {
														if (gp_raw2mark <= gp_p2raw) {
															if (agric_raw1mark <= agric_p1raw) {
																if (agric_raw2mark <= agric_p2raw) {
																	if (!duplicates.isStudentPosted(student_name,
																			studentdata)) {

																		// temporary
																		// measure
																		// for
																		// Shona
																		/*
																		 * if
																		 * (english_raw1mark
																		 * <=
																		 * english_p1raw
																		 * &&
																		 * english_raw2mark
																		 * <=
																		 * english_p2raw
																		 * &&
																		 * maths_raw1mark
																		 * <=
																		 * maths_p1raw
																		 * &&
																		 * maths_raw2mark
																		 * <=
																		 * maths_p2raw
																		 * &&
																		 * gp_raw1mark
																		 * <=
																		 * gp_p1raw
																		 * &&
																		 * gp_raw2mark
																		 * <=
																		 * gp_p2raw
																		 * &&
																		 * agric_raw1mark
																		 * <=
																		 * agric_p1raw
																		 * &&
																		 * agric_raw2mark
																		 * <=
																		 * agric_p2raw)
																		 * {
																		 */
																		// up tp
																		// here
																		// will
																		// bw
																		// commented

																		// calculate
																		// papers
																		// adjusted
																		// marks

																		int student_english_p1 = GetAdjustedMark
																				.getAdjustedMark(english_raw1mark,
																						english_p1raw, english_p1cont);

																		int student_english_p2 = GetAdjustedMark
																				.getAdjustedMark(english_raw2mark,
																						english_p2raw, english_p2cont);

																		int student_shona_p1 = GetAdjustedMark
																				.getAdjustedMark(shona_raw1mark,
																						shona_p1raw, shona_p1cont);

																		int student_shona_p2 = GetAdjustedMark
																				.getAdjustedMark(shona_raw2mark,
																						shona_p2raw, shona_p2cont);

																		int student_maths_p1 = GetAdjustedMark
																				.getAdjustedMark(maths_raw1mark,
																						maths_p1raw, maths_p1cont);
																		int student_maths_p2 = GetAdjustedMark
																				.getAdjustedMark(maths_raw2mark,
																						maths_p2raw, maths_p2cont);

																		int student_gp_p1 = GetAdjustedMark
																				.getAdjustedMark(gp_raw1mark, gp_p1raw,
																						gp_p1cont);
																		int student_gp_p2 = GetAdjustedMark
																				.getAdjustedMark(gp_raw2mark, gp_p2raw,
																						gp_p2cont);

																		int student_agric_p1 = GetAdjustedMark
																				.getAdjustedMark(agric_raw1mark,
																						agric_p1raw, agric_p1cont);
																		int student_agric_p2 = GetAdjustedMark
																				.getAdjustedMark(agric_raw2mark,
																						agric_p2raw, agric_p2cont);

																		if ((student_english_p1
																				+ student_english_p2) >= passMark)
																			passed.recordPass(stm, student_name,
																					AllSubjects.ENGLISH, class_name,
																					term, year, date);
																		if ((student_shona_p1
																				+ student_shona_p2) >= passMark)
																			passed.recordPass(stm, student_name,
																					AllSubjects.SHONA, class_name, term,
																					year, date);
																		if ((student_maths_p1
																				+ student_maths_p2) >= passMark)
																			passed.recordPass(stm, student_name,
																					AllSubjects.MATHEMATICS, class_name,
																					term, year, date);
																		if ((student_gp_p1 + student_gp_p2) >= passMark)
																			passed.recordPass(stm, student_name,
																					AllSubjects.GENERAL_PAPER,
																					class_name, term, year, date);
																		if ((student_agric_p1
																				+ student_agric_p2) >= passMark)
																			passed.recordPass(stm, student_name,
																					AllSubjects.AGRICULTURE, class_name,
																					term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_ONE, class_name,
																				AllSubjects.ENGLISH,
																				Integer.toString(english_p1raw),
																				Integer.toString(english_p1cont),
																				student_name,
																				Integer.toString(english_raw1mark),
																				Integer.toString(student_english_p1),
																				stm, term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_TWO, class_name,
																				AllSubjects.ENGLISH,
																				Integer.toString(english_p2raw),
																				Integer.toString(english_p2cont),
																				student_name,
																				Integer.toString(english_raw2mark),
																				Integer.toString(student_english_p2),
																				stm, term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_ONE, class_name,
																				AllSubjects.SHONA,
																				Integer.toString(shona_p1raw),
																				Integer.toString(shona_p1cont),
																				student_name,
																				Integer.toString(shona_raw1mark),
																				Integer.toString(student_shona_p1), stm,
																				term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_TWO, class_name,
																				AllSubjects.SHONA,
																				Integer.toString(shona_p2raw),
																				Integer.toString(shona_p2cont),
																				student_name,
																				Integer.toString(shona_raw2mark),
																				Integer.toString(student_shona_p2), stm,
																				term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_ONE, class_name,
																				AllSubjects.MATHEMATICS,
																				Integer.toString(maths_p1raw),
																				Integer.toString(maths_p1cont),
																				student_name,
																				Integer.toString(maths_raw1mark),
																				Integer.toString(student_maths_p1), stm,
																				term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_TWO, class_name,
																				AllSubjects.MATHEMATICS,
																				Integer.toString(maths_p2raw),
																				Integer.toString(maths_p2cont),
																				student_name,
																				Integer.toString(maths_raw2mark),
																				Integer.toString(student_maths_p2), stm,
																				term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_ONE, class_name,
																				AllSubjects.GENERAL_PAPER,
																				Integer.toString(gp_p1raw),
																				Integer.toString(gp_p1cont),
																				student_name,
																				Integer.toString(gp_raw1mark),
																				Integer.toString(student_gp_p1), stm,
																				term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_TWO, class_name,
																				AllSubjects.GENERAL_PAPER,
																				Integer.toString(gp_p2raw),
																				Integer.toString(gp_p2cont),
																				student_name,
																				Integer.toString(gp_raw2mark),
																				Integer.toString(student_gp_p2), stm,
																				term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_ONE, class_name,
																				AllSubjects.AGRICULTURE,
																				Integer.toString(agric_p1raw),
																				Integer.toString(agric_p1cont),
																				student_name,
																				Integer.toString(agric_raw1mark),
																				Integer.toString(student_agric_p1), stm,
																				term, year, date);

																		insertDataIntoMarkSchedule.insertData(
																				Tablename.PAPER_TWO, class_name,
																				AllSubjects.AGRICULTURE,
																				Integer.toString(agric_p2raw),
																				Integer.toString(agric_p2cont),
																				student_name,
																				Integer.toString(agric_raw2mark),
																				Integer.toString(student_agric_p2), stm,
																				term, year, date);
																	} else {
																		// mark
																		// already
																		// posted
																		System.out.println(
																				student_name + " already posted");

																	}

																} else {
																	// agric
																	// mark
																	// paper 2
																	// out of
																	// bound
																	data.add(student_name + " At line " + (lines + 1)
																			+ ". Agric paper 2 Raw Mark is Out of Bound");
																}

															} else {
																// agric mark
																// paper 1 out
																// of bound
																data.add(student_name + " At line " + (lines + 1)
																		+ ". Agric paper 1 Raw Mark is Out of Bound");
															}

														} else {
															// gp paper 2 mark
															// out of bound
															data.add(student_name + " At line " + (lines + 1)
																	+ ". General paper 2 Raw Mark is Out of Bound");
														}

													} else {
														// gp paper 1 mark out
														// of bound
														data.add(student_name + " At line " + (lines + 1)
																+ ". General paper 1 Raw Mark is Out of Bound");
													}

												} else {
													// maths paper 2 mark out of
													// bound
													data.add(student_name + " At line " + (lines + 1)
															+ ". Maths paper 2 Raw Mark is Out of Bound");
												}

											} else {
												// maths paper 1 mark out of
												// bound
												data.add(student_name + " At line " + (lines + 1)
														+ ". Maths paper 1 Raw Mark is Out of Bound");
											}

										} else {
											// shona paper 2 mark out of bound
											data.add(student_name + " At line " + (lines + 1)
													+ ". Shona paper 2 Raw Mark is Out of Bound");
										}

									} else {
										// shona paper 1 mar out of bound
										data.add(student_name + " At line " + (lines + 1)
												+ ". Shona paper 1 Raw Mark is Out of Bound");
									}

								} else {
									// english paper 2 mark out of bound
									data.add(student_name + " At line " + (lines + 1)
											+ ". English paper 2 Raw Mark is Out of Bound");
								}

							} else {
								// english one subject mark is out of bound
								data.add(student_name + " At line " + (lines + 1)
										+ ". English paper 1 Raw Mark is Out of Bound");
							}

						} else {
							// one value is not a digit
							ScanForAbsent scanForAbsent = new ScanForAbsent(stm, stmt, rs, rs1);
							/**
							 * Scan for english
							 */
							scanForAbsent.doScanFor(AllSubjects.ENGLISH, class_name, value[1], value[2], english_p1raw,
									english_p1cont, english_p2raw, english_p2cont, value[0], lines, data, term, year,
									date);

							/**
							 * Scan for Shona
							 */
							scanForAbsent.doScanFor(AllSubjects.SHONA, class_name, value[3], value[4], shona_p1raw,
									shona_p1cont, shona_p2raw, shona_p2cont, value[0], lines, data, term, year, date);

							/**
							 * Scan for Mathematics
							 */

							scanForAbsent.doScanFor(AllSubjects.MATHEMATICS, class_name, value[5], value[6],
									maths_p1raw, maths_p1cont, maths_p2raw, maths_p2cont, value[0], lines, data, term,
									year, date);

							/**
							 * Scan for General paper
							 */
							scanForAbsent.doScanFor(AllSubjects.GENERAL_PAPER, class_name, value[7], value[8], gp_p1raw,
									gp_p1cont, gp_p2raw, gp_p2cont, value[0], lines, data, term, year, date);

							/**
							 * Scan foor Agric
							 */
							scanForAbsent.doScanFor(AllSubjects.AGRICULTURE, class_name, value[9], value[10],
									agric_p1raw, agric_p1cont, agric_p2raw, agric_p2cont, value[0], lines, data, term,
									year, date);

						}

					} else {
						// empty cell is detected
						String name = (value[0].equals("") ? " No Name " : value[0]);
						data.add(name + " At line " + (lines + 1) + ". An Empty Cell Is Detected. Record was bypassed");
					}
					lines++;
				}
			}
			bf.close();
			new Notification("Success", "The upload succeeded!<br>Thank you", Notification.Type.TRAY_NOTIFICATION, true)
					.show(Page.getCurrent());
			new DisplayUploadExceptions().DisplayWindow(data);
		} catch (Exception ee) {
			ee.printStackTrace(System.err);
			new Notification("Error", ee.getMessage(), Notification.Type.ERROR_MESSAGE, true).show(Page.getCurrent());
		}

	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null;
		try {
			if (new ValidateFileMimeType().isValidCSVFile(filename)) {
				file = new File(ApplicationBasePath.basePath() + "/WEB-INF/uploads/" + filename);
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
