package org.legzo.picreporter;

import org.legzo.picreporter.exporter.ConsoleExporter;
import org.legzo.picreporter.importer.FileSystemWalker;
import org.legzo.picreporter.model.Folder;

public class Main {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		if (args.length == 0) {
			System.out.println("Usage: \njava -jar pic-repoter.jar \"path_to_my_pic_dir\"");
		} else {
			FileSystemWalker fsWalker = new FileSystemWalker();

			System.out.println();

			for (String rep : args) {
				Folder fold = new Folder(rep);
				fsWalker.visit(fold);
				System.out.println("> " + fsWalker.getVisitedFolders().size()
						+ " folders recursively visited, starting @ " + rep);
				fsWalker.getVisitedFolders().clear();
			}

			long end = System.currentTimeMillis();

			long elapsed = end - start;
			System.out.println("\n> " + fsWalker.getLoadedImages().size() + " images located in " + elapsed + "ms");

			fsWalker.loadExifData();

			ConsoleExporter exporter = new ConsoleExporter();
			exporter.dumpDataForFiles(fsWalker.getLoadedImages());
		}
	}
}
