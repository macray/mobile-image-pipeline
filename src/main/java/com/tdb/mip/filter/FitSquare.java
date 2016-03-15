package com.tdb.mip.filter;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

// TODO: regroup FillSquare to FillTo
public class FitSquare implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(FitSquare.class);

	private int targetW;
	private int targetH;
	private Color color;

	public FitSquare(int targetW, int targetH, Color color) {
		this.targetH = targetH;
		this.targetW = targetW;
		this.color = color;
	}

	@Override
	public BufferedImage applyTo(BufferedImage image) {
		LOGGER.debug("fill to");

		int w = image.getWidth();
		int h = image.getHeight();



        float ratio;
        if(w > h){
            ratio = (float)w / (float)targetW;
        }else{
            ratio = (float)h / (float)targetH;
        }
        int imgTargetW= (int)((float)w / ratio);
        int imgTargetH= (int)((float)h / ratio);


        BufferedImage resize = Scalr.resize(image, targetW, targetH);

        // copy the original image to the center of the new one
		int xShift = (int) ((targetW - imgTargetW) / 2f);
		int yShift = (int) ((targetH - imgTargetH) / 2f);

        BufferedImage targetImage = new BufferedImage(targetW, targetH, image.getType());
        // copy image
		Graphics2D g2 = targetImage.createGraphics();
        g2.setColor(new Color(255,255,255,255));
        g2.fillRect(0,0, targetW, targetH);
        //g2.setBackground(color);
        //g2.clearRect(0,0, targetW, targetH);
        g2.drawImage(resize, null, xShift, yShift);
		g2.dispose();

		return targetImage;
	}

	@Override
	public Filter copy() {
		return new FitSquare(targetW, targetH, color);
	}
}
