package com.tdb.mip.filter;

import static com.google.common.truth.Truth.*;

import org.junit.Test;

import java.io.File;
import java.util.List;

public class FilterDescriptionTest {

    @Test
    public void build_file_descriptions_can_be_build_from_filename_without_filter_description() throws Exception {
        List<FilterDescription> filterDescriptions = FilterDescription.Builder.fromFilename(new File("image_name.svg"));
        ASSERT.that(filterDescriptions.size()).isEqualTo(0);
    }

    @Test
    public void build_file_descriptions_can_be_build_from_filename() throws Exception {
        List<FilterDescription> filterDescriptions = FilterDescription.Builder.fromFilename(new File("image_name-filterDescription1-filterDescription2.png"));
        ASSERT.that(filterDescriptions.size()).isEqualTo(2);
        ASSERT.that(filterDescriptions.get(0).getRawDescription()).isEqualTo("filterDescription1");
        ASSERT.that(filterDescriptions.get(1).getRawDescription()).isEqualTo("filterDescription2");
    }



    @Test
    public void build_filter_descriptions_from_string(){
        List<FilterDescription> filterDescriptions = FilterDescription.Builder.fromStringDescription("filterDescription1-filterDescription2");
        ASSERT.that(filterDescriptions.size()).isEqualTo(2);
        ASSERT.that(filterDescriptions.get(0).getRawDescription()).isEqualTo("filterDescription1");
        ASSERT.that(filterDescriptions.get(1).getRawDescription()).isEqualTo("filterDescription2");
    }

    @Test
    public void build_filter_descriptions_from_empty_string(){
        List<FilterDescription> filterDescriptions = FilterDescription.Builder.fromStringDescription("");
        ASSERT.that(filterDescriptions.size()).isEqualTo(0);

        filterDescriptions = FilterDescription.Builder.fromStringDescription(null);
        ASSERT.that(filterDescriptions.size()).isEqualTo(0);
    }
}