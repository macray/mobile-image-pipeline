package com.tdb.mip.pipeline;

import com.tdb.mip.exception.AmbigiousFilterDescriptionException;
import com.tdb.mip.exception.FilterDescriptionNotUnderstoodException;
import com.tdb.mip.filter.Filter;
import com.tdb.mip.filter.FilterDescription;
import com.tdb.mip.filter.FilterFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.truth0.Truth.ASSERT;

public class BasePipelineFactoryTest {

    private BasePipelineFactory pipelineFactory;

    @Before
    public void setUp() throws Exception {
        pipelineFactory = new BasePipelineFactory();
    }

    @Test(expected = FilterDescriptionNotUnderstoodException.class)
    public void pipeline_cannot_be_created_if_a_filter_description_is_not_understood() {
        // no FilterFactory has been registered so impossible to understand any FilterDescription
        List<FilterDescription> descriptions = FilterDescription.Builder.fromStringDescription("unknown_config");
        pipelineFactory.buildFiltersFromDescriptions(new LinkedList<Filter>(), descriptions, null);
    }

    @Test(expected = AmbigiousFilterDescriptionException.class)
    public void pipeline_cannot_be_created_if_a_filter_description_is_understood_by_more_than_1_filter() {
        pipelineFactory.registerFilterFactory(new UnderstandAnyFilterDescriptionFactory());
        pipelineFactory.registerFilterFactory(new UnderstandAnyFilterDescriptionFactory());

        List<FilterDescription> descriptions = FilterDescription.Builder.fromStringDescription("config");
        pipelineFactory.buildFiltersFromDescriptions(new LinkedList<Filter>(), descriptions, null);
    }

    @Test
    public void one_filter_per_description() {
        List<Filter> filters = new LinkedList<>();
        pipelineFactory.registerFilterFactory(new UnderstandAnyFilterDescriptionFactory());

        List<FilterDescription> descriptions = FilterDescription.Builder.fromStringDescription("config1-config2-config3");
        pipelineFactory.buildFiltersFromDescriptions(filters, descriptions, null);
        ASSERT.that(filters.size()).isEqualTo(3);
    }

    private static class UnderstandAnyFilterDescriptionFactory implements FilterFactory<Filter> {

        @Override
        public boolean canBuild(FilterDescription filterDescription) {
            // understand any filter description
            return true;
        }

        @Override
        public Filter build(FilterDescription filterDescription, Pipeline pipeline) {
            return null;
        }

    }


}
