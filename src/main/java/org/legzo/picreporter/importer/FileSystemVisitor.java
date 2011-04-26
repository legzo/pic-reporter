package org.legzo.picreporter.importer;

import org.legzo.picreporter.model.Folder;

public interface FileSystemVisitor {

	void visit(Folder f);
	
}
