package com.tdb.mip.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdb.mip.pipeline.Pipeline;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResizeFactory implements FilterFactory<Resize> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResizeFactory.class);

    private static final Pattern PATTERN_RESIZE_FILTER_WH = Pattern.compile("w([0-9]+)h([0-9]+)");
    private static final Pattern PATTERN_RESIZE_FILTER_HW = Pattern.compile("h([0-9]+)w([0-9]+)");
    private static final Pattern PATTERN_RESIZE_FILTER_H = Pattern.compile("h([0-9]+)");
    private static final Pattern PATTERN_RESIZE_FILTER_W = Pattern.compile("w([0-9]+)");

    private Resize createResizeFilter(FilterDescription filterDescription, Pipeline pipeline) {
        int w = -1;
        int h = -1;

        String rawConfig = filterDescription.getRawDescription();
        Matcher matcher = PATTERN_RESIZE_FILTER_HW.matcher(rawConfig);
        if (matcher.matches()) {
            h = Integer.parseInt(matcher.group(1));
            w = Integer.parseInt(matcher.group(2));
            return new Resize(w, h, pipeline.getPixelRounding());
        }

        matcher = PATTERN_RESIZE_FILTER_WH.matcher(rawConfig);
        if (matcher.matches()) {
            h = Integer.parseInt(matcher.group(2));
            w = Integer.parseInt(matcher.group(1));
            return new Resize(w, h, pipeline.getPixelRounding());
        }

        matcher = PATTERN_RESIZE_FILTER_H.matcher(rawConfig);
        if (matcher.matches()) {
            h = Integer.parseInt(matcher.group(1));
            return new Resize(w, h, pipeline.getPixelRounding());
        }

        matcher = PATTERN_RESIZE_FILTER_W.matcher(rawConfig);
        if (matcher.matches()) {
            w = Integer.parseInt(matcher.group(1));
            return new Resize(w, h, pipeline.getPixelRounding());
        }

        throw new RuntimeException("WTF? incorrect resize parameter " + rawConfig);
    }

    @Override
    public boolean canBuild(FilterDescription filterDescription) {
        String config = filterDescription.getRawDescription();
        boolean match = PATTERN_RESIZE_FILTER_HW.matcher(config).matches() //
                || PATTERN_RESIZE_FILTER_WH.matcher(config).matches() //
                || PATTERN_RESIZE_FILTER_H.matcher(config).matches() //
                || PATTERN_RESIZE_FILTER_W.matcher(config).matches(); //
        return match;
    }

    @Override
    public Resize build(FilterDescription filterDescription, Pipeline pipeline) {
        return createResizeFilter(filterDescription, pipeline);
    }
}
