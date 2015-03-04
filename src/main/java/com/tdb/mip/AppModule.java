package com.tdb.mip;

import com.google.inject.AbstractModule;
import com.tdb.mip.filter.*;
import com.tdb.mip.pipeline.AndroidPipelineFactory;
import com.tdb.mip.pipeline.IOSPipelineFactory;
import com.tdb.mip.pipeline.PipelineExecutor;
import com.tdb.mip.pipeline.PipelineExecutorImpl;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AppModule extends AbstractModule {
    private final String configPath;

    public AppModule(String configPath) {
        this.configPath = configPath;
    }

    @Override
    protected void configure() {
        List<FilterFactory> filterFactories = new LinkedList<>();
        filterFactories.add(new AutoCropFactory());
        filterFactories.add(new ResizeFactory());
        filterFactories.add(new FillSquareFactory());
        filterFactories.add(new TintFactory());
        filterFactories.add(new VFlipFactory());
        filterFactories.add(new HFlipFactory());
        filterFactories.add(new BlackAndWhiteFactory());

        AndroidPipelineFactory androidPipelineFactory = new AndroidPipelineFactory();
        for (FilterFactory filterFactory : filterFactories) {
            androidPipelineFactory.registerFilterFactory(filterFactory);
        }

        IOSPipelineFactory iosPipelineFactory = new IOSPipelineFactory();
        for (FilterFactory filterFactory : filterFactories) {
            iosPipelineFactory.registerFilterFactory(filterFactory);
        }

        bind(AndroidPipelineFactory.class).toInstance(androidPipelineFactory);
        bind(IOSPipelineFactory.class).toInstance(iosPipelineFactory);

        Configuration configuration = new Configuration();
        try {
            if (StringUtils.isEmpty(configPath)) {
                configuration.loadDefaultConfig();
            } else {
                configuration.load(configPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        bind(Configuration.class).toInstance(configuration);

        bind(PipelineExecutor.class).to(PipelineExecutorImpl.class);
    }
}