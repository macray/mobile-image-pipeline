package com.tdb.mip;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tdb.mip.pipeline.*;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

// TODO: add SourceFileLoader
public class MobileImagePipeline {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileImagePipeline.class);

    @Inject
    PipelineExecutor executor;

    @Inject
    AndroidPipelineFactory androidPipelineFactory;

    @Inject
    IOSPipelineFactory iosPipelineFactory;
    
    @Inject
    WindowsPhonePipelineFactory wpPipelineFactory;

    @Inject
    Configuration configuration;

    public static void main(String[] args) throws TranscoderException, IOException {
        LOGGER.info("Mobile Image Pipeline");

        String[] defaultConfig = {null};
        String[] configFiles = args.length > 0 ? args : defaultConfig;

        for (String configFile : configFiles) {
            LOGGER.info("config file" + configFile);
            Injector injector = Guice.createInjector(new AppModule(configFile));
            MobileImagePipeline mobileImagePipeline = injector.getInstance(MobileImagePipeline.class);
            mobileImagePipeline.run();
        }

        // need to explicitly quit
        LOGGER.info("Exiting...");
        System.exit(0);
    }

    private void loadSourceAssetFiles(List<File> files, File sourceFolder, boolean recursive) {
        for (final File fileEntry : sourceFolder.listFiles()) {
            if (fileEntry.isFile()) {
                String extension = FilenameUtils.getExtension(fileEntry.getName());
                if (configuration.getSourceAllowedExtensions().contains(extension)) {
                    files.add(fileEntry);
                }
            } else if (recursive && fileEntry.isDirectory()) {
                loadSourceAssetFiles(files, fileEntry, recursive);
            }
        }
    }

    public void processAndroidAssets() {
        if (configuration.hasAndroidConfiguration()) {
            File sourceDir = new File(configuration.getAndroidSourceDir());
            List<File> sourceFiles = new LinkedList<>();
            loadSourceAssetFiles(sourceFiles, sourceDir, configuration.isRecursiveLoad());
            for (File source : sourceFiles) {
                Pipeline pipeline = androidPipelineFactory.createPipeline(source, configuration);
                executor.execute(new AndroidPipelineRunnable(pipeline, configuration));
            }
        }
    }

    public void processIosAssets() {
        if (configuration.hasIosConfiguration()) {
            File sourceDir = new File(configuration.getIosSourceDir());
            List<File> sourceFiles = new LinkedList<>();
            loadSourceAssetFiles(sourceFiles, sourceDir, configuration.isRecursiveLoad());
            for (File source : sourceFiles) {
                Pipeline pipeline = iosPipelineFactory.createPipeline(source, configuration);
                executor.execute(new IOSPipelineRunnable(pipeline, configuration));
            }
        }
    }
    
    public void processWindowsPhoneAssets() {
        if (configuration.hasWindowsPhoneConfiguration()) {
            File sourceDir = new File(configuration.getWpSourceDir());
            List<File> sourceFiles = new LinkedList<>();
            loadSourceAssetFiles(sourceFiles, sourceDir, configuration.isRecursiveLoad());
            for (File source : sourceFiles) {
                Pipeline pipeline = wpPipelineFactory.createPipeline(source, configuration);
                executor.execute(new WindowsPhonePipelineRunnable(pipeline, configuration));
            }
        }
    }

    public void run() {
        processAndroidAssets();
        processIosAssets();
        processWindowsPhoneAssets();
        executor.waitUntilEverythingHasBeenExecuted();
    }
}
