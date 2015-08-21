package com.tdb.mip.filter;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tdb.mip.pipeline.Pipeline;

public class FillToFactory implements FilterFactory<FillTo> {

	private static final Pattern PATTERN_RESIZE_FILTER_WH_WO_ALPHA = Pattern.compile("fillw([0-9]+)h([0-9]+)#([0-9a-fA-F]{6})");
	private static final Pattern PATTERN_RESIZE_FILTER_HW_WO_ALPHA = Pattern.compile("fillh([0-9]+)w([0-9]+)#([0-9a-fA-F]{6})");

	private static final Pattern PATTERN_RESIZE_FILTER_WH_W_ALPHA = Pattern.compile("fillw([0-9]+)h([0-9]+)#([0-9a-fA-F]{8})");
	private static final Pattern PATTERN_RESIZE_FILTER_HW_W_ALPHA = Pattern.compile("fillh([0-9]+)w([0-9]+)#([0-9a-fA-F]{8})");

	private Color extractColor(String colorTxt) {
		//String colorHexa = "#" + colorTxt;
		//return Color.decode(colorInt);
		boolean hasAlpha = colorTxt.length() == 8;
		int intValue = (int)Long.parseLong(colorTxt, 16);
		return new Color(intValue, hasAlpha);
	}

	@Override
	public boolean canBuild(FilterDescription filterDescription) {

		String config = filterDescription.getRawDescription();
		boolean match = PATTERN_RESIZE_FILTER_WH_WO_ALPHA.matcher(config).matches() //
				|| PATTERN_RESIZE_FILTER_HW_WO_ALPHA.matcher(config).matches() //
				|| PATTERN_RESIZE_FILTER_WH_W_ALPHA.matcher(config).matches() //
				|| PATTERN_RESIZE_FILTER_HW_W_ALPHA.matcher(config).matches(); //
		return match;
	}

	@Override
	public FillTo build(FilterDescription filterDescription, Pipeline pipeline) {

		String rawConfig = filterDescription.getRawDescription();
		int h;
		int w;
		Color color;
		Matcher matcher = PATTERN_RESIZE_FILTER_WH_WO_ALPHA.matcher(rawConfig);
		if (matcher.matches()) {
			w = Integer.parseInt(matcher.group(1));
			h = Integer.parseInt(matcher.group(2));
			color = extractColor(matcher.group(3));
			return new FillTo(w, h, color);
		}

		matcher = PATTERN_RESIZE_FILTER_HW_WO_ALPHA.matcher(rawConfig);
		if (matcher.matches()) {
			h = Integer.parseInt(matcher.group(2));
			w = Integer.parseInt(matcher.group(1));
			color = extractColor(matcher.group(3));
			return new FillTo(w, h, color);
		}

		matcher = PATTERN_RESIZE_FILTER_WH_W_ALPHA.matcher(rawConfig);
		if (matcher.matches()) {
			w = Integer.parseInt(matcher.group(1));
			h = Integer.parseInt(matcher.group(2));
			color = extractColor(matcher.group(3));
			return new FillTo(w, h, color);
		}

		matcher = PATTERN_RESIZE_FILTER_HW_W_ALPHA.matcher(rawConfig);
		if (matcher.matches()) {
			h = Integer.parseInt(matcher.group(2));
			w = Integer.parseInt(matcher.group(1));
			color = extractColor(matcher.group(3));
			return new FillTo(w, h, color);
		}
		
		throw new IllegalStateException("impossible to build FillTo");

	}

}
