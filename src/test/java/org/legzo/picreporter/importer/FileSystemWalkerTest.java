package org.legzo.picreporter.importer;

import junit.framework.Assert;

import org.junit.Test;
import org.legzo.picreporter.exporter.ConsoleExporter;
import org.legzo.picreporter.model.Folder;

public class FileSystemWalkerTest {

	@Test
	public void checkImageLoading() {
		Folder fold = new Folder("src/test/resources/parent");

		FileSystemWalker fsWalker = new FileSystemWalker();

		fsWalker.visit(fold);

		Assert.assertNotNull("Walker did not load any image", fsWalker.getLoadedImages());
		Assert.assertEquals("Bad number of loaded images", 4, fsWalker.getLoadedImages().size());
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		// Folder fold = new Folder("/Users/legzo/Pictures/Nikon D40");
		Folder fold = new Folder("E:\\JEAT8351\\Data\\photos-sfo");
		FileSystemWalker fsWalker = new FileSystemWalker();
		fsWalker.visit(fold);
		long end = System.currentTimeMillis();

		long elapsed = end - start;
		System.out.println("> " + fsWalker.getLoadedImages().size() + " images located in " + elapsed + "ms");

		fsWalker.loadExifData();

		ConsoleExporter exporter = new ConsoleExporter();

		exporter.dumpDataForFiles(fsWalker.getLoadedImages());
	}
}
