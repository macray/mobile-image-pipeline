package com.tdb.mip.pipeline;

import com.tdb.mip.Configuration;
import com.tdb.mip.exception.AmbigiousFilterDescriptionException;
import com.tdb.mip.exception.FilterDescriptionNotUnderstoodException;
import com.tdb.mip.filter.Filter;
import com.tdb.mip.filter.FilterDescription;
import com.tdb.mip.filter.FilterFactory;
import com.tdb.mip.reader.DefaultImageReader;
import com.tdb.mip.reader.ImageReader;
import com.tdb.mip.reader.SVGImageReader;
import com.tdb.mip.util.PixelRoundingHalfDown;
import com.tdb.mip.writer.PNGImageWriter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class BasePipelineFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasePipelineFactory.class);

	private final List<FilterFactory<?>> filterFactories = new LinkedList<>();
	private final List<String> markers = new LinkedList<>();

	public void registerFilterFactory(FilterFactory<?> filterFactory) {
		filterFactories.add(filterFactory);
	}
	
	public void addMarker(String markerName) {
		markers.add(markerName);
	}

	public Pipeline createPipeline(File file, Configuration configuration) {
		Pipeline pipeline = new Pipeline();
		pipeline.setSourceFile(file);
		// TODO: make the rounding configurable
		pipeline.setPixelRounding(new PixelRoundingHalfDown());

		String fileName = file.getName();

		// build Reader
		// according to image source extension
		ImageReader imageReader;
		String extension = FilenameUtils.getExtension(fileName);
		if (extension.equals("svg")) {
			imageReader = new SVGImageReader();
		} else {
			imageReader = new DefaultImageReader();
		}
		pipeline.setImageReader(imageReader);

		// build output filename
		String fileNameWithoutExtension = StringUtils.removeEnd(fileName, "." + extension);
		String[] split = StringUtils.split(fileNameWithoutExtension, '-');
		String outputFileName = split[0] + ".png";
		pipeline.setOutFileName(outputFileName);

		// build filters
		List<Filter> filters = new LinkedList<>();
		filters.addAll(configuration.getPreFilters(this, pipeline));
		List<FilterDescription> filtersDescription = FilterDescription.Builder.fromFilename(new File(fileName));
		buildFiltersFromDescriptions(filters, filtersDescription, pipeline);
		filters.addAll(configuration.getPostFilters(this, pipeline));
		pipeline.setFilters(filters);

		// build the "Saver"
		pipeline.setImageWriter(new PNGImageWriter());

		return pipeline;
	}

	public void buildFiltersFromDescriptions(List<Filter> filters, List<FilterDescription> filterDescriptions, Pipeline pipeline) {
		for (FilterDescription description : filterDescriptions) {
			
			// ignore marker
			boolean isMarker = false;
			for(String markerName : markers) {
				if(markerName.equals(description.getRawDescription())){
					isMarker = true;
                    break;
				}
			}
			if(isMarker) {
				continue;
			}
			
			int numberOfFilterCreated = 0;
			for (FilterFactory<?> factory : filterFactories) {
				if (factory.canBuild(description)) {
					numberOfFilterCreated++;
					Filter filter = factory.build(description, pipeline);
					filters.add(filter);
				}
			}

			if (numberOfFilterCreated > 1) {
				throw new AmbigiousFilterDescriptionException(description);
			}

			if (numberOfFilterCreated == 0) {
				throw new FilterDescriptionNotUnderstoodException(description);
			}
		}
	}

}
