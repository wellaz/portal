package com.equation.utils.filesopener;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Wellington
 */
public class OpenFile {

	public OpenFile() {

	}

	public void open(String path) {
		if (Desktop.isDesktopSupported()) {
			try {
				File myfile = new File(path);
				Desktop.getDesktop().open(myfile);
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		}
	}
}