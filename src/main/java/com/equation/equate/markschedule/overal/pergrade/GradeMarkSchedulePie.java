/**
 *
 * @author Wellington
 */
package com.equation.equate.markschedule.overal.pergrade;

import com.itextpdf.text.Image;

/**
 * @author Wellington
 *
 */
@SuppressWarnings("restriction")
public class GradeMarkSchedulePie {
	/**
	 * 
	 */
	Image image;

	public GradeMarkSchedulePie() {

	}
/*
	@SuppressWarnings("unused")
	public Image makePieChartImage(int englishPerf, int shonaPerf, int mathsPerf, int gpPerf, int agricPerf,
			String grade) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("ENGLISH", new Integer(englishPerf));
		dataset.setValue("SHONA", new Integer(shonaPerf));
		dataset.setValue("MATHEMATICS", new Integer(mathsPerf));
		dataset.setValue("GENERAL PAPER", new Integer(gpPerf));
		dataset.setValue("AGRICULTURE", new Integer(agricPerf));
		
		JFreeChart chart = ChartFactory.createPieChart(grade.toUpperCase() + " Pass Rate", dataset, true, true, true);

		PiePlot plot = (PiePlot) chart.getPlot();

		String imgnm = ApplicationBasePath.basePath() + "/WEB-INF/reports/" + "chart.png";

		final ChartRenderingInfo inf = new ChartRenderingInfo(new StandardEntityCollection());
		final File img = new File(imgnm);

		try {
			image = Image.getInstance(imgnm);
			image.scaleAbsolute(480, 300);
			ChartUtilities.saveChartAsPNG(img, chart, 600, 400, inf);
		} catch (IOException | BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}
*/
}
