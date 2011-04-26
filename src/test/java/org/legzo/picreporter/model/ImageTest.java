package org.legzo.picreporter.model;

import junit.framework.Assert;

import org.junit.Test;

public class ImageTest {

	@Test
	public void checkLoadExif() {
		Image nefImage = new Image("src/test/resources/parent/DSC_1062.NEF");
		nefImage.loadExif();
		Assert.assertEquals("35mm", nefImage.getFocalLength().toString());
	}
}
