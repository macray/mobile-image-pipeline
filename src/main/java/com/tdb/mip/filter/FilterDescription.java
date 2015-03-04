package com.tdb.mip.filter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds the description of a {@link com.tdb.mip.filter.Filter}.
 *
 * @author vaudauxr
 */
public class FilterDescription {

    private final String rawDescription;

    private FilterDescription(String rawDescription) {
        super();
        this.rawDescription = rawDescription;
    }

    public String getRawDescription() {
        return rawDescription;
    }

    @Override
    public String toString() {
        return "FilterDescription [rawDescription=" + rawDescription + "]";
    }


    public static class Builder {


        /**
         * Expected format "description1-description2"
         *
         * @param descriptions
         * @return
         */
        public static List<FilterDescription> fromStringDescription(String descriptions) {
            List<FilterDescription> filterDescriptions = new LinkedList<>();

            if (StringUtils.isEmpty(descriptions)) {
                return filterDescriptions;
            }

            String[] split = descriptions.split("-");
            for (String description : split) {
                filterDescriptions.add(new FilterDescription(description));
            }

            return filterDescriptions;
        }

        /**
         * Expected format "filename-description1-description2"
         *
         * @param file
         * @return
         */
        public static List<FilterDescription> fromFilename(File file) {
            String fullFilename = file.getName();
            String extension = FilenameUtils.getExtension(fullFilename);
            String fullFilenameWithoutExtension = fullFilename.replace("." + extension, "");
            String descriptionsOnly = StringUtils.substringAfter(fullFilenameWithoutExtension, "-");
            return fromStringDescription(descriptionsOnly);
        }
    }

}
