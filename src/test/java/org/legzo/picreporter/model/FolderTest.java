package org.legzo.picreporter.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FolderTest {

	@Test
	public void checkGetChildren() {
		Folder fold = new Folder("src/test/resources/parent");
		List<Folder> children = fold.getChildren();

		Assert.assertNotNull("Folder returned no children", children);
		Assert.assertEquals("Folder returned bad number of children", 3, children.size());
	}

	@Test
	public void checkGetImages() {
		Folder fold = new Folder("src/test/resources/parent");
		List<Image> images = fold.getImages();

		Assert.assertNotNull("Folder returned no images", images);
		Assert.assertEquals("Folder returned bad number of images", 2, images.size());
	}
}
