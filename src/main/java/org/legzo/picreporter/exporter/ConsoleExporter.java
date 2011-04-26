package org.legzo.picreporter.exporter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.legzo.picreporter.model.Aperture;
import org.legzo.picreporter.model.ExposureTime;
import org.legzo.picreporter.model.FocalLength;
import org.legzo.picreporter.model.Image;
import org.legzo.picreporter.model.Parameter;

public class ConsoleExporter {
	private static final int GRAPH_LENGTH = 50;
	private static final NumberFormat nf = new DecimalFormat("0.0");
	private int nbOfImages = 0;

	public void dumpDataForFiles(Set<Image> images) {
		System.out.println("\n\n> Dumping data");

		nbOfImages = 0;

		Map<FocalLength, Integer> focalLengths = new TreeMap<FocalLength, Integer>();
		Map<Aperture, Integer> apertures = new TreeMap<Aperture, Integer>();
		Map<ExposureTime, Integer> exposureTimes = new TreeMap<ExposureTime, Integer>();

		nbOfImages = images.size();

		for (Image image : images) {
			updateCounters(focalLengths, image.getFocalLength());
			updateCounters(apertures, image.getAperture());
			updateCounters(exposureTimes, image.getExposureTime());
		}

		displayValuesForParameter("FOCALS", focalLengths);
		displayValuesForParameter("APERTURES", apertures);
		displayValuesForParameter("EXPOSURE TIMES", exposureTimes);
	}

	private void displayValuesForParameter(String title, Map<? extends Parameter, Integer> values) {
		System.out.println("\n" + title);
		System.out.println("-----------------");
		for (Parameter key : values.keySet()) {
			Integer valueToDisplay = values.get(key);
			float percentage = (float) valueToDisplay / (float) nbOfImages;

			String string = StringUtils.rightPad(StringUtils.rightPad(key.toString(), 7) + " : "
					+ StringUtils.rightPad(valueToDisplay.toString(), 4), 15);

			System.out.println(string + getGraphForPercentage(percentage));
		}
	}

	private String getGraphForPercentage(float percentage) {
		return " [" + StringUtils.rightPad(StringUtils.repeat("#", (int) (GRAPH_LENGTH * percentage)), GRAPH_LENGTH)
				+ "] " + StringUtils.leftPad(nf.format(100 * percentage), 4) + "%";
	}

	private <T extends Parameter> void updateCounters(Map<T, Integer> alreadyLoadedData, T keyToIncrement) {
		if (keyToIncrement != null) {
			Integer counter = alreadyLoadedData.get(keyToIncrement) != null ? alreadyLoadedData.get(keyToIncrement) : 0;
			alreadyLoadedData.put(keyToIncrement, counter + 1);
		}
	}
}
