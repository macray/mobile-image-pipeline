package com.tdb.mip.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderException;
import org.junit.Test;

import com.tdb.mip.reader.DefaultImageReader;
import com.tdb.mip.writer.PNGImageWriter;

public class TintTest {

	@Test
	public void apply_filter_need_manual_review() throws TranscoderException, IOException {
		DefaultImageReader reader = new DefaultImageReader();
		BufferedImage image = reader.read("src/test/resources/eiffel_tower.jpg");

		Tint tint = new Tint(new Color(200, 0, 0, 0), 0.5f);
		image = tint.applyTo(image);
		new PNGImageWriter().save(image, "target/tint.png");
	}

}
