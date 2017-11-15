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
public class GradeMarkScheduleBarChart {
	Image image;

	/**
	 * 
	 */
	public GradeMarkScheduleBarChart() {
		// TODO Auto-generated constructor stub
	}
	/*
	@SuppressWarnings("deprecation")
	public Image makePieChartImage(int englishPerf, int shonaPerf, int mathsPerf, int gpPerf, int agricPerf,
			String grade) {
		ArrayList<Integer> percentages = new ArrayList<>();
		percentages.addAll(Arrays.asList(englishPerf, shonaPerf, mathsPerf, gpPerf, agricPerf));
		ArrayList<String> subjects = new ArrayList<>();
		subjects.addAll(Arrays.asList("ENGLISH", "SHONA", "MATHEMATICS", "GENERAL PAPER", "AGRICULTURE"));
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int dt = 0; dt < percentages.size(); dt++) {
			dataset.setValue(percentages.get(dt), "Marks", subjects.get(dt));
		}

		JFreeChart chart = ChartFactory.createBarChart3D(grade.toUpperCase() + " Performance", "SUBJECTS",
				"PERCENTAGES", dataset, PlotOrientation.VERTICAL, false, true, false);
		chart.setBackgroundPaint(new Color(0.5f, 0.5f, 1f));
		chart.getTitle().setPaint(Color.BLACK);
		final CategoryPlot p = chart.getCategoryPlot();

		BarRenderer renderer = (BarRenderer) p.getRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		p.setRenderer(renderer);
		renderer.setBasePositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.HALF_ASCENT_CENTER));
		renderer.setItemLabelsVisible(true);
		chart.getCategoryPlot().setRenderer(renderer);

		String imgnm = ApplicationBasePath.basePath() + "/WEB-INF/reports/" + "ThisChart.png";

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
