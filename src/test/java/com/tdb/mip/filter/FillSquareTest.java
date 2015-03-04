package com.tdb.mip.filter;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderException;
import org.junit.Test;

import com.tdb.mip.reader.DefaultImageReader;
import com.tdb.mip.writer.PNGImageWriter;


public class FillSquareTest {

	@Test
	public void test_horizontal_filling_need_manual_review() throws TranscoderException, IOException {
		BufferedImage image = new DefaultImageReader().read("src/test/resources/eiffel_tower.jpg");

		// apply filter
		FillSquare fillSquare = new FillSquare();
		image = fillSquare.applyTo(image);
		new PNGImageWriter().save(image, "target/fill_square_h.png");
	}

	@Test
	public void test_vertical_filling_need_manual_review() throws IOException, TranscoderException {
		BufferedImage image = new DefaultImageReader().read("src/test/resources/bear.png");

		// apply filter
		FillSquare fillSquare = new FillSquare();
		image = fillSquare.applyTo(image);
		new PNGImageWriter().save(image, "target/fill_square_v.png");
	}
}
