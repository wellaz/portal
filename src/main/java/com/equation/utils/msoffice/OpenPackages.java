package com.equation.utils.msoffice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class OpenPackages extends Panel {
	String[] text = { "Microsoft Word", "Microsoft Excel", "Microsoft Access", "Microsoft Power\nPoint",
			"Microsoft Publisher", "Microsoft OneNote", "Microsoft Outlook" };
	Button[] buttons;

	public OpenPackages() {
		// Panel pann = new Panel();
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		// pann.setLayout(new GridLayout(7, 1, 0, 7));
		// this.setLayout(new BorderLayout());

		buttons = new Button[text.length];
		for (int i = 0; i < text.length; i++) {
			buttons[i] = new Button(text[i]);
			buttons[i].addStyleName(ValoTheme.BUTTON_LINK);

			layout.addComponent(buttons[i]);
		}
		this.setContent(layout);

		buttons[0].addClickListener((event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msWord();
		});
		buttons[1].addClickListener((event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msExcel();

		});
		buttons[2].addClickListener((event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msAccess();

		});
		buttons[3].addClickListener((event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.mspowerPoint();
		});
		buttons[4].addClickListener((event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msLync();
		});
		buttons[5].addClickListener((event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msOnenote();
		});
		buttons[6].addClickListener((event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msOutLook();
		});

	}

	public class Worker extends SwingWorker<Void, Void> {
		JDialog dialog;
		JProgressBar prog;
		Button hider;
		JLabel waitlbl;

		public Worker() {

			dialog = new JDialog();
			dialog.setLayout(new BorderLayout());
			prog = new JProgressBar();
			dialog.setUndecorated(true);
			hider = new Button("Run in Background");
			hider.addClickListener((event) -> {
				// new AnimateDialog().fadeOut(dialog, 100);
			});
			waitlbl = new JLabel("Processing....");
			dialog.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent evvt) {
					dialog.setShape(new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), 5, 5));
				}
			});
			Box box = Box.createVerticalBox();
			box.add(waitlbl);
			box.add(prog);
			// box.add(hider);
			dialog.getContentPane().setBackground(new Color(0.5f, 0.5f, 1f));
			dialog.getContentPane().add(box, BorderLayout.CENTER);
			dialog.setSize(300, 100);
			Dimension d = dialog.getSize(), screen = Toolkit.getDefaultToolkit().getScreenSize();
			int a = (screen.width - d.width) / 2, c = (screen.height - d.height) / 2;
			dialog.setLocation(a, c);
		}

		@Override
		protected Void doInBackground() throws Exception {
			prog.setIndeterminate(true);
			// new AnimateDialog().fadeIn(dialog, 100);
			Thread.sleep(3000);
			return null;
		}

		@Override
		public void done() {
			prog.setIndeterminate(false);
			// new AnimateDialog().fadeOut(dialog, 100);
		}
	}

}
