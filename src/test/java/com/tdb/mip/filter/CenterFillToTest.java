package com.tdb.mip.filter;

import com.tdb.mip.reader.SVGImageReader;
import com.tdb.mip.util.PixelRoundingHalfDown;
import com.tdb.mip.writer.PNGImageWriter;
import org.apache.batik.transcoder.TranscoderException;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class CenterFillToTest {

	@Ignore
	@Test
	public void test_need_manual_review() throws TranscoderException, IOException {
		BufferedImage image = new SVGImageReader().read("src/test/resources/craig.svg");

		CenterFillTo filter = new CenterFillTo(100,-1, 1000,-1, new Color(1f,0f,0f,0f), new PixelRoundingHalfDown());
		image = filter.applyTo(image);
		new PNGImageWriter().save(image, "target/centerfill.png");
	}

}
