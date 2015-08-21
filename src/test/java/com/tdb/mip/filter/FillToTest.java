package com.tdb.mip.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderException;
import org.junit.Test;

import com.tdb.mip.reader.DefaultImageReader;
import com.tdb.mip.writer.PNGImageWriter;


public class FillToTest {

	@Test
	public void test_need_manual_review() throws TranscoderException, IOException {
		BufferedImage image = new DefaultImageReader().read("src/test/resources/bear.png");

		// apply filter
		FillTo fillTo = new FillTo(1000,1500, new Color(1f,0f,0f,0f));
		image = fillTo.applyTo(image);
		new PNGImageWriter().save(image, "target/fill_to_1000_1500.png");
	}

}
