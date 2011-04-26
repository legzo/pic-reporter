package org.legzo.picreporter.importer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.legzo.picreporter.model.Folder;
import org.legzo.picreporter.model.Image;

public class FileSystemWalker implements FileSystemVisitor {

	private static final NumberFormat nf = new DecimalFormat("0.0");
	private static final int PROGRESS_BAR_SIZE = 66;
	private Set<Image> loadedImages = new HashSet<Image>();
	private Set<Folder> visitedFolders = new HashSet<Folder>();

	public void visit(Folder f) {
		visitedFolders.add(f);

		List<Image> images = f.getImages();
		List<Folder> children = f.getChildren();

		if (images != null) {
			for (Image img : images) {
				getLoadedImages().add(img);
			}
		}

		if (children != null) {
			for (Folder child : children) {
				child.accept(this);
			}
		}
	}

	public Set<Image> getLoadedImages() {
		return loadedImages;
	}

	public void loadExifData() {
		System.out.println("> loading EXIF data\n");

		long start = System.currentTimeMillis();
		int treatedImages = 0;

		for (Image img : loadedImages) {
			img.loadExif();
			treatedImages++;

			float percentage = (float) treatedImages / (float) loadedImages.size();
			int toDisplay = (int) (percentage * PROGRESS_BAR_SIZE);
			System.out.print("\r[" + StringUtils.rightPad(StringUtils.repeat("=", toDisplay), PROGRESS_BAR_SIZE) + "] "
					+ nf.format(100 * percentage) + "%");
		}

		long elapsed = System.currentTimeMillis() - start;

		System.out.print(" in " + elapsed + "ms");
	}

	public Set<Folder> getVisitedFolders() {
		return visitedFolders;
	}

}