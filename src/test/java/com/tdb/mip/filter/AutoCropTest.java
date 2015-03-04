package com.tdb.mip.filter;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderException;
import org.junit.Test;

import com.tdb.mip.reader.SVGImageReader;
import com.tdb.mip.writer.PNGImageWriter;


public class AutoCropTest {

	@Test
	public void apply_filter_need_manual_review() throws TranscoderException, IOException {
		SVGImageReader reader = new SVGImageReader();
		BufferedImage image = reader.read("src/test/resources/craig-autocrop-w400h600.svg");

		AutoCrop autoCrop = new AutoCrop();
		image = autoCrop.applyTo(image);
		new PNGImageWriter().save(image, "target/autoscrop.png");
	}
}
