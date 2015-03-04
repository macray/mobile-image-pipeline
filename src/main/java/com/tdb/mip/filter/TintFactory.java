package com.tdb.mip.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdb.mip.pipeline.Pipeline;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//https://code.google.com/p/filthy-rich-clients/source/browse/trunk/filthyRichClients-16/src16.StaticEffects.Blur/org/progx/artemis/image/ColorTintFilter.java?r=2
public class TintFactory implements FilterFactory<Tint> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TintFactory.class);

    private static final Pattern PATTERN_TINT = Pattern.compile("tint#{0,1}([0-9a-fA-F]{6})");
    private static final Pattern PATTERN_TINT_MIX_VALUE = Pattern.compile("tint#{0,1}([0-9a-fA-F]{6}),([0-9]+[,\\.]{0,1}[0-9]*)");

    @Override
    public boolean canBuild(FilterDescription filterDescription) {
        String config = filterDescription.getRawDescription();
        return PATTERN_TINT.matcher(config).matches() || PATTERN_TINT_MIX_VALUE.matcher(config).matches();
    }

    private Color extractTint(Matcher matcher) {
        String colorHexa = "#" + matcher.group(1);
        LOGGER.info("tint " + colorHexa);
        return Color.decode(colorHexa);
    }

    @Override
    public Tint build(FilterDescription filterDescription, Pipeline pipeline) {
        Matcher matcher = PATTERN_TINT_MIX_VALUE.matcher(filterDescription.getRawDescription());

        boolean withMixValue = matcher.matches();
        if (!withMixValue) {
            matcher = PATTERN_TINT.matcher(filterDescription.getRawDescription());
            matcher.matches();
        }

        Color tint = extractTint(matcher);
        float mixValue = 1f;
        if (withMixValue) {
            mixValue = Float.parseFloat(matcher.group(2));
        }

        tint = new Color(tint.getRed(), tint.getGreen(), tint.getBlue(), 0);
        return new Tint(tint, mixValue);
    }

}
