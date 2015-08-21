package com.tdb.mip.filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdb.mip.exception.DownscalingNotSupportedException;

// TODO: regroup FillSquare to FillTo
public class FillTo implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(FillTo.class);

	private int targetW;
	private int targetH;
	private Color color;

	public FillTo(int targetW, int targetH, Color color) {
		this.targetH = targetH;
		this.targetW = targetW;
		this.color = color;
	}

	@Override
	public BufferedImage applyTo(BufferedImage image) {
		LOGGER.debug("fill to");

		int w = image.getWidth();
		int h = image.getHeight();

		if (targetW < w || targetH < h) {
			throw new DownscalingNotSupportedException("targetW=" + targetW + " targetH=" + targetH + " image.H=" + h + " image.W=" + w);
		}

		BufferedImage targetImage = new BufferedImage(targetW, targetH, image.getType());

		// copy the original image to the center of the new one
		int xShift = (int) ((targetW - w) / 2f);
		int yShift = (int) ((targetH - h) / 2f);

		// copy image
		Graphics2D g2 = targetImage.createGraphics();
		g2.drawImage(image, null, xShift, yShift);
		g2.dispose();

		
		// fill top and bottom
		for (int x = 0; x < targetImage.getWidth(); x++) {
			for (int y = yShift; y >= 0; y--) {
				targetImage.setRGB(x, y, color.getRGB());
			}

			for (int y = yShift + image.getHeight(); y < targetImage.getHeight(); y++) {
				targetImage.setRGB(x, y, color.getRGB());
			}
		}

		// fill right and left
		for (int y = 0; y < targetImage.getHeight(); y++) {
			for (int x = xShift; x >= 0; x--) {
				targetImage.setRGB(x, y, color.getRGB());
			}

			for (int x = xShift + image.getWidth(); x < targetImage.getWidth(); x++) {
				targetImage.setRGB(x, y, color.getRGB());
			}
		}

		return targetImage;
	}

	@Override
	public Filter copy() {
		return new FillTo(targetW, targetH, color);
	}
}
