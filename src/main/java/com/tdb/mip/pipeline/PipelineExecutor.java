package com.tdb.mip.pipeline;


public interface PipelineExecutor {
    public void execute(Runnable runnable);

    public void waitUntilEverythingHasBeenExecuted();
}
