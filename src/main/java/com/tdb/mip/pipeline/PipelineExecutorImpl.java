package com.tdb.mip.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PipelineExecutorImpl implements PipelineExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PipelineExecutorImpl.class);

    private final ExecutorService executor;
    private final List<Future<?>> tasks;

    public PipelineExecutorImpl() {
        this(4);
    }

    public PipelineExecutorImpl(int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.tasks = new LinkedList<>();
    }

    @Override
    public void execute(Runnable pipelineRunnable) {
        tasks.add(executor.submit(pipelineRunnable));
    }

    @Override
    public void waitUntilEverythingHasBeenExecuted() {
        for (Future<?> task : tasks) {
            try {
                task.get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("error when running task", e);
            }
        }
    }

}
