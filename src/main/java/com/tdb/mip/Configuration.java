package com.tdb.mip;

import com.tdb.mip.density.AndroidDensity;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.DensityUtils;
import com.tdb.mip.density.IOSDensity;
import com.tdb.mip.exception.MissingConfigPropertyException;
import com.tdb.mip.filter.Filter;
import com.tdb.mip.filter.FilterDescription;
import com.tdb.mip.pipeline.BasePipelineFactory;
import com.tdb.mip.pipeline.Pipeline;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    // windows phone
    private static final int WP_MANDATORY_PROPERTY_COUNT = 2;
    private static final String TARGET_WP_DIR = "target.wp.dir";
    private static final String SOURCE_WP_DIR = "source.wp.dir";
    
    // ios
    private static final int IOS_MANDATORY_PROPERTY_COUNT = 2;
    private static final String TARGET_IOS_DIR = "target.ios.dir";
    private static final String SOURCE_IOS_DIR = "source.ios.dir";
    private static final String TARGET_IOS_DENSITIES = "target.ios.densities";
    
    // android
    private static final int ANDROID_PROPERTY_COUNT = 4;
    private static final String TARGET_ANDROID_DENSITIES_DEFAULT = "target.android.densities.default";
    private static final String TARGET_ANDROID_DIR = "target.android.dir";
    private static final String SOURCE_ANDROID_DIR = "source.android.dir";
    private static final String SOURCE_ANDROID_DENSITY = "source.android.density";

    // generic
    private static final String SOURCE_EXTENSIONS_ALLOWED = "source.extensions.allowed";
    private static final String SOURCE_RECURSIVE_LOAD = "source.recursive-load";
    private static final String GLOBAL_PRE_FILTERS = "filters.global.pre";
    private static final String GLOBAL_POST_FILTERS = "filters.global.post";

    private Density androidSourceDensity;
    private String androidSourceDir;
    private String androidTargetDir;
    private List<Density> androidDefaultTargetDensities;

    private String iosSourceDir;
    private String iosTargetDir;
    private List<Density> iosTargetDensities;

    private String wpSourceDir;
    private String wpTargetDir;
    
	private List<String> sourceAllowedExtensions;
    private String preFiltersDescription;
    private String postFiltersDescription;


    private boolean hasIosConfiguration;
    private boolean hasAndroidConfiguration;
    private boolean hasWindowsPhoneConfiguration;
    
    private boolean recursiveLoad;

    public boolean hasIosConfiguration() {
        return hasIosConfiguration;
    }

    public boolean hasAndroidConfiguration() {
        return hasAndroidConfiguration;
    }
    
    public boolean hasWindowsPhoneConfiguration() {
    	return hasWindowsPhoneConfiguration;
    }

    public List<Filter> getPreFilters(BasePipelineFactory basePipelineFactory, Pipeline pipeline) {
        List<Filter> preFilters = new LinkedList<>();
        if (StringUtils.isNotBlank(preFiltersDescription)) {
            List<FilterDescription> filterDescriptions = FilterDescription.Builder.fromStringDescription(preFiltersDescription);
            basePipelineFactory.buildFiltersFromDescriptions(preFilters, filterDescriptions, pipeline);
        }
        return preFilters;
    }


    public List<Filter> getPostFilters(BasePipelineFactory basePipelineFactory, Pipeline pipeline) {
        List<Filter> postFilters = new LinkedList<>();
        if (StringUtils.isNotBlank(postFiltersDescription)) {
            List<FilterDescription> filterDescriptions = FilterDescription.Builder.fromStringDescription(postFiltersDescription);
            basePipelineFactory.buildFiltersFromDescriptions(postFilters, filterDescriptions, pipeline);
        }
        return postFilters;
    }

    private boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    private boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    private void loadGenericConfig(Properties props) {
        sourceAllowedExtensions = new LinkedList<>();
        String allowedExtensionsList = props.getProperty(SOURCE_EXTENSIONS_ALLOWED);

        if(StringUtils.isNotBlank(allowedExtensionsList)) {
            String[] allowedExtensionNames = allowedExtensionsList.split(",");
            for (String allowedExtension : allowedExtensionNames) {
                sourceAllowedExtensions.add(allowedExtension);
            }
        }else{
            sourceAllowedExtensions.add("svg");
            sourceAllowedExtensions.add("jpg");
            sourceAllowedExtensions.add("png");
        }

        preFiltersDescription = props.getProperty(GLOBAL_PRE_FILTERS);

        postFiltersDescription = props.getProperty(GLOBAL_POST_FILTERS);

        recursiveLoad = Boolean.valueOf(props.getProperty(SOURCE_RECURSIVE_LOAD, "true"));
    }

    public boolean isRecursiveLoad() {
        return recursiveLoad;
    }

    private void loadWindowsPhoneConfig(Properties props) {
    	 List<String> missingProperties = new LinkedList<>();

        wpSourceDir = props.getProperty(SOURCE_WP_DIR);
        if (isEmpty(wpSourceDir)) {
            missingProperties.add(SOURCE_WP_DIR);
        }

        wpTargetDir = props.getProperty(TARGET_WP_DIR);
        if (isEmpty(wpTargetDir)) {
            missingProperties.add(TARGET_WP_DIR);
        }

        boolean allAndroidPropertiesAreMissing = missingProperties.size() == WP_MANDATORY_PROPERTY_COUNT;
        if (!isEmpty(missingProperties) && !allAndroidPropertiesAreMissing) {
            throw new MissingConfigPropertyException("properties " + missingProperties.toString() + " are missing");
        }

        if (missingProperties.size() == 0) {
            hasWindowsPhoneConfiguration = true;
        }
    }
    
    private void loadAndroidConfig(Properties props) {
        List<String> missingProperties = new LinkedList<>();

        String androidSourceDensityPropertyValue = props.getProperty(SOURCE_ANDROID_DENSITY);
        if (isEmpty(androidSourceDensityPropertyValue)) {
            missingProperties.add(SOURCE_ANDROID_DENSITY);
        }else{
            androidSourceDensity = DensityUtils.valueOf(AndroidDensity.ALL, androidSourceDensityPropertyValue);
        }

        androidSourceDir = props.getProperty(SOURCE_ANDROID_DIR);
        if (isEmpty(androidSourceDir)) {
            missingProperties.add(SOURCE_ANDROID_DIR);
        }

        androidTargetDir = props.getProperty(TARGET_ANDROID_DIR);
        if (isEmpty(androidTargetDir)) {
            missingProperties.add(TARGET_ANDROID_DIR);
        }

        androidDefaultTargetDensities = new LinkedList<>();
        String defaultDensitiesList = props.getProperty(TARGET_ANDROID_DENSITIES_DEFAULT);
        if(isEmpty(defaultDensitiesList)){
            missingProperties.add(TARGET_ANDROID_DENSITIES_DEFAULT);
        }else{
            String[] defaultDensityNames = defaultDensitiesList.split(",");
            for (String densityName : defaultDensityNames) {
            	Density density = DensityUtils.valueOf(AndroidDensity.ALL, densityName);
                androidDefaultTargetDensities.add(density);
            }
            // make the list unmodifiable
            androidDefaultTargetDensities = Collections.unmodifiableList(androidDefaultTargetDensities);
        }

        boolean allAndroidPropertiesAreMissing = missingProperties.size() == ANDROID_PROPERTY_COUNT;
        if (!isEmpty(missingProperties) && !allAndroidPropertiesAreMissing) {
            throw new MissingConfigPropertyException("properties " + missingProperties.toString() + " are missing");
        }

        if (missingProperties.size() == 0) {
            hasAndroidConfiguration = true;
        }
    }

    private void loadIosConfig(Properties props) {
        List<String> missingProperties = new LinkedList<>();

        iosSourceDir = props.getProperty(SOURCE_IOS_DIR);
        if (isEmpty(iosSourceDir)) {
            missingProperties.add(SOURCE_IOS_DIR);
        }

        iosTargetDir = props.getProperty(TARGET_IOS_DIR);
        if (isEmpty(iosTargetDir)) {
            missingProperties.add(TARGET_IOS_DIR);
        }
        
        String defaultDensitiesList = props.getProperty(TARGET_IOS_DENSITIES);
        if(isEmpty(defaultDensitiesList)){
            iosTargetDensities = IOSDensity.ALL;
        }else{
        	iosTargetDensities = new LinkedList<>();
            String[] defaultDensityNames = defaultDensitiesList.split(",");
            for (String densityName : defaultDensityNames) {
            	Density density = DensityUtils.valueOf(IOSDensity.ALL, densityName);
            	iosTargetDensities.add(density);
            }
            // make the list unmodifiable
            iosTargetDensities = Collections.unmodifiableList(iosTargetDensities);
        }
        
        //iosTargetDensities = props.getProperty(TARGET_IOS_DENSITIES, IOS)

        boolean allIOSPropertiesAreMissing = missingProperties.size() == IOS_MANDATORY_PROPERTY_COUNT;
        if (!isEmpty(missingProperties) && !allIOSPropertiesAreMissing) {
            throw new MissingConfigPropertyException("properties " + missingProperties.toString() + " are missing");
        }

        if (missingProperties.size() == 0) {
            hasIosConfiguration = true;
        }
    }

    public void loadDefaultConfig() throws IOException {
        String workingDir = System.getProperty("user.dir");
        load(workingDir + "/default.config.properties");
    }

    public void load(String configPath) throws IOException {
        LOGGER.info("loading config: " + configPath);
        Properties props = new Properties();
        props.load(new FileInputStream(configPath));

        loadGenericConfig(props);
        loadAndroidConfig(props);
        loadIosConfig(props);
        loadWindowsPhoneConfig(props);

        if(!hasAndroidConfiguration() && !hasIosConfiguration() && !hasWindowsPhoneConfiguration()){
            throw new MissingConfigPropertyException("neither iOS nor Android nor WindowsPhone properties has been found, at least platform configuration is mandatory");
        }

        LOGGER.info(toString());
    }

    public Density getAndroidSourceDensity() {
        return androidSourceDensity;
    }

    public String getAndroidSourceDir() {
        return androidSourceDir;
    }

    public String getAndroidTargetDir() {
        return androidTargetDir;
    }

    public List<Density> getAndroidDefaultTargetDensities() {
        return androidDefaultTargetDensities;
    }

    public List<String> getSourceAllowedExtensions() {
        return sourceAllowedExtensions;
    }

    public String getWpSourceDir() {
		return wpSourceDir;
	}

	public String getWpTargetDir() {
		return wpTargetDir;
	}

	public String getIosSourceDir() {
        return iosSourceDir;
    }

    public String getIosTargetDir() {
        return iosTargetDir;
    }

    public List<Density> getIosTargetDensities() {
		return iosTargetDensities;
	}

	public void setIosTargetDensities(List<Density> iosTargetDensities) {
		this.iosTargetDensities = iosTargetDensities;
	}

	@Override
	public String toString() {
		return "Configuration [androidSourceDensity=" + androidSourceDensity + ", androidSourceDir=" + androidSourceDir
				+ ", androidTargetDir=" + androidTargetDir + ", androidDefaultTargetDensities=" + androidDefaultTargetDensities
				+ ", iosSourceDir=" + iosSourceDir + ", iosTargetDir=" + iosTargetDir + ", iosTargetDensities=" + iosTargetDensities
				+ ", wpSourceDir=" + wpSourceDir + ", wpTargetDir=" + wpTargetDir + ", sourceAllowedExtensions=" + sourceAllowedExtensions
				+ ", preFiltersDescription=" + preFiltersDescription + ", postFiltersDescription=" + postFiltersDescription
				+ ", hasIosConfiguration=" + hasIosConfiguration + ", hasAndroidConfiguration=" + hasAndroidConfiguration
				+ ", hasWindowsPhoneConfiguration=" + hasWindowsPhoneConfiguration + ", recursiveLoad=" + recursiveLoad + "]";
	}
	


}
