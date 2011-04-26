package org.legzo.picreporter.model;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.legzo.picreporter.importer.FileSystemVisitor;

public class Folder {

	private File file;

	private static final FileFilter dirFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.isDirectory() && !pathname.getName().equals(".svn");
		}
	};

	private static final FilenameFilter imageFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(".jpg") ||  name.toLowerCase().endsWith(".nef");
		}
	};

	public Folder(String path) {
		this.file = new File(path);
		if (!file.exists()) {
			System.err.println("Could not find dir: " + path);
		}
	}

	public Folder(File file) {
		this.file = file;
	}

	public void accept(FileSystemVisitor visitor) {
		visitor.visit(this);
	}

	public List<Folder> getChildren() {
		List<Folder> result = null;

		File[] subDirs = file.listFiles(dirFilter);

		if (subDirs != null && subDirs.length > 0) {
			result = new ArrayList<Folder>();

			for (File file : subDirs) {
				result.add(new Folder(file));
			}
		}

		return result;
	}

	public List<Image> getImages() {
		List<Image> result = null;

		File[] subDirs = file.listFiles(imageFilter);

		if (subDirs != null && subDirs.length > 0) {
			result = new ArrayList<Image>();

			for (File file : subDirs) {
				result.add(new Image(file));
			}
		}

		return result;
	}
}
